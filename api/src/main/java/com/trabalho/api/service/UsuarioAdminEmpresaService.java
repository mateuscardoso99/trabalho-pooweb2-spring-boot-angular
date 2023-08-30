package com.trabalho.api.service;

import java.util.Arrays;
import java.util.Collection;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.trabalho.api.exception.DataNotFoundException;
import com.trabalho.api.model.UsuarioAdminEmpresa;
import com.trabalho.api.model.Empresa;
import com.trabalho.api.model.Permissoes;
import com.trabalho.api.repository.UsuarioAdminEmpresaRepository;
import com.trabalho.api.request.CadastroUsuario;
import com.trabalho.api.security.JwtUtils;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class UsuarioAdminEmpresaService {
    private final UsuarioAdminEmpresaRepository adminEmpresaRepository;
    private final EmpresaService empresaService;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    public UsuarioAdminEmpresaService(UsuarioAdminEmpresaRepository adminEmpresaRepository, EmpresaService empresaService, PasswordEncoder passwordEncoder, JwtUtils jwtUtils){
        this.adminEmpresaRepository = adminEmpresaRepository;
        this.empresaService = empresaService;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
    }

    public UsuarioAdminEmpresa findById(Long id) throws Exception{
        return this.adminEmpresaRepository.findById(id).orElseThrow(() -> new DataNotFoundException("Admin empresa não encontrado"));
    }

    public UsuarioAdminEmpresa findUsuarioEmpresa(HttpServletRequest request, Long idUsuario) throws Exception{
        String token = jwtUtils.getTokenFromRequest(request);
        String idEmpresa = jwtUtils.getClaimsFromJwtToken(token).get("empresa_id").toString();
        return this.adminEmpresaRepository.findUsuarioEmpresa(Long.parseLong(idEmpresa),idUsuario).orElseThrow(() -> new DataNotFoundException("Admin empresa não encontrado"));
    }

    public UsuarioAdminEmpresa findUsuarioEmpresa(Long idEmpresa, Long idUsuario) throws Exception{
        return this.adminEmpresaRepository.findUsuarioEmpresa(idEmpresa,idUsuario).orElseThrow(() -> new DataNotFoundException("Admin empresa não encontrado"));
    }

    public Collection<UsuarioAdminEmpresa> findAllByEmpresa(HttpServletRequest request){
        String token = jwtUtils.getTokenFromRequest(request);
        String idEmpresa = jwtUtils.getClaimsFromJwtToken(token).get("empresa_id").toString();
        return this.adminEmpresaRepository.findAllByEmpresa(Long.parseLong(idEmpresa));
    }

    @Transactional
    public UsuarioAdminEmpresa salvar(HttpServletRequest request, CadastroUsuario dados) throws Exception{
        String token = jwtUtils.getTokenFromRequest(request);
        String idEmpresa = jwtUtils.getClaimsFromJwtToken(token).get("empresa_id").toString();

        Empresa empresa = this.empresaService.findById(Long.parseLong(idEmpresa));
        UsuarioAdminEmpresa user = new UsuarioAdminEmpresa();
        user.setEmail(dados.getEmail());
        user.setEmpresa(empresa);
        user.setNome(dados.getNome());
        user.setPermissoes(Arrays.asList(Permissoes.ADMIN_EMPRESA));
        user.setSenha(passwordEncoder.encode(dados.getSenha()));
        return this.adminEmpresaRepository.save(user);
    }

    @Transactional
    public UsuarioAdminEmpresa update(HttpServletRequest request, CadastroUsuario dados, Long idUsuario) throws Exception{
        String token = jwtUtils.getTokenFromRequest(request);
        String idEmpresa = jwtUtils.getClaimsFromJwtToken(token).get("empresa_id").toString();

        Empresa empresa = this.empresaService.findById(Long.parseLong(idEmpresa));
        UsuarioAdminEmpresa user = this.findUsuarioEmpresa(empresa.getId(),idUsuario);
        user.setEmail(dados.getEmail());
        user.setNome(dados.getNome());
        user.setSenha(passwordEncoder.encode(dados.getSenha()));
        return this.adminEmpresaRepository.save(user);
    }

    @Transactional
    public void handleAtivacao(HttpServletRequest request, Long idUsuario, boolean ativar) throws Exception{
        String token = jwtUtils.getTokenFromRequest(request);
        String idEmpresa = jwtUtils.getClaimsFromJwtToken(token).get("empresa_id").toString();

        UsuarioAdminEmpresa adminEmpresa = this.findUsuarioEmpresa(Long.parseLong(idEmpresa),idUsuario);
        adminEmpresa.setAtivo(ativar ? true : false);
        adminEmpresaRepository.save(adminEmpresa);
    }
}
