package com.trabalho.api.service;

import java.util.Arrays;
import java.util.Collection;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.trabalho.api.exception.DataNotFoundException;
import com.trabalho.api.model.Empresa;
import com.trabalho.api.model.Endereco;
import com.trabalho.api.model.Permissoes;
import com.trabalho.api.model.UsuarioAdminEmpresa;
import com.trabalho.api.repository.EmpresaRepository;
import com.trabalho.api.repository.UsuarioAdminEmpresaRepository;
import com.trabalho.api.request.CadastroEmpresa;
import com.trabalho.api.request.CadastroUsuarioAdmin;
import com.trabalho.api.security.JwtUtils;

import jakarta.servlet.http.HttpServletRequest;

/**
 * os métodos que pegam o id_empresa do token, são chamados pelo EmpresaController
 * intuito é impedir que usuario de uma empresa veja dados de outra
 */

@Service
public class EmpresaService {
    private final EmpresaRepository empresaRepository;
    private final UsuarioAdminEmpresaRepository adminEmpresaRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    public EmpresaService(EmpresaRepository empresaRepository,UsuarioAdminEmpresaRepository adminEmpresaRepository, PasswordEncoder passwordEncoder, JwtUtils jwtUtils){
        this.empresaRepository = empresaRepository;
        this.adminEmpresaRepository = adminEmpresaRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
    }

    public Collection<Empresa> findAll(){
        return empresaRepository.findAll();
    }

    //usado no portal admin
    public Empresa findById(Long id) throws Exception{
        return empresaRepository.findById(id).orElseThrow(() -> new DataNotFoundException("Empresa não encontrada"));
    }

    //usado no portal empresa, busca a empresa do usuario logado pelo id da empresa no jwt
    public Empresa findById(HttpServletRequest request) throws Exception{
        String token = jwtUtils.getTokenFromRequest(request);
        String idEmpresa = jwtUtils.getClaimsFromJwtToken(token).get("empresa_id").toString();
        return empresaRepository.findById(Long.parseLong(idEmpresa)).orElseThrow(() -> new DataNotFoundException("Empresa não encontrada"));
    }

    @Transactional
    public Empresa salvar(CadastroEmpresa dados){
        Endereco e = new Endereco();
        e.setBairro(dados.endereco().bairro());
        e.setCidade(dados.endereco().cidade());
        e.setComplemento(dados.endereco().complemento());
        e.setLatitude(dados.endereco().latitude());
        e.setLongitude(dados.endereco().longitude());
        e.setNumero(dados.endereco().numero());
        e.setRua(dados.endereco().rua());
        e.setUf(dados.endereco().uf());

        Empresa empresa = Empresa.builder()
                            .nome(dados.nome())
                            .razaoSocial(dados.razaoSocial())
                            .endereco(e)
                            .build();
        return this.empresaRepository.save(empresa);
    }

    @Transactional
    public Empresa atualizar(CadastroEmpresa dados, HttpServletRequest request) throws Exception{
        String token = jwtUtils.getTokenFromRequest(request);
        String idEmpresa = jwtUtils.getClaimsFromJwtToken(token).get("empresa_id").toString();
        Empresa empresa = empresaRepository.findById(Long.parseLong(idEmpresa)).orElseThrow(() -> new DataNotFoundException("Empresa não encontrada"));

        Endereco e = empresa.getEndereco();
        e.setBairro(dados.endereco().bairro());
        e.setCidade(dados.endereco().cidade());
        e.setComplemento(dados.endereco().complemento());
        e.setLatitude(dados.endereco().latitude());
        e.setLongitude(dados.endereco().longitude());
        e.setNumero(dados.endereco().numero());
        e.setRua(dados.endereco().rua());
        e.setUf(dados.endereco().uf());

        empresa.setNome(dados.nome());
        empresa.setRazaoSocial(dados.razaoSocial());
        empresa.setEndereco(e);

        return empresa;
    }

    @Transactional
    public void handleAtivacao(Long id, boolean ativar) throws Exception{
        Empresa empresa = this.findById(id);
        empresa.setAtivo(ativar ? true : false);
    }


    //USUÁRIOS
    public UsuarioAdminEmpresa findUsuarioEmpresaByIdUsuario(Long id) throws Exception{
        return this.adminEmpresaRepository.findById(id).orElseThrow(() -> new DataNotFoundException("Admin empresa não encontrado"));
    }

    public UsuarioAdminEmpresa findUsuarioEmpresaByIdUsuario(HttpServletRequest request, Long idUsuario) throws Exception{
        String token = jwtUtils.getTokenFromRequest(request);
        String idEmpresa = jwtUtils.getClaimsFromJwtToken(token).get("empresa_id").toString();
        return this.adminEmpresaRepository.findUsuarioEmpresa(Long.parseLong(idEmpresa),idUsuario).orElseThrow(() -> new DataNotFoundException("Admin empresa não encontrado"));
    }

    public UsuarioAdminEmpresa findUsuarioEmpresaByIdUsuarioAndIdEmpresa(Long idEmpresa, Long idUsuario) throws Exception{
        return this.adminEmpresaRepository.findUsuarioEmpresa(idEmpresa,idUsuario).orElseThrow(() -> new DataNotFoundException("Admin empresa não encontrado"));
    }

    public Collection<UsuarioAdminEmpresa> findAllUsuariosByEmpresa(HttpServletRequest request){
        String token = jwtUtils.getTokenFromRequest(request);
        String idEmpresa = jwtUtils.getClaimsFromJwtToken(token).get("empresa_id").toString();
        return this.adminEmpresaRepository.findAllByEmpresa(Long.parseLong(idEmpresa));
    }

    @Transactional
    public UsuarioAdminEmpresa salvarUsuarioEmpresa(HttpServletRequest request, CadastroUsuarioAdmin dados) throws Exception{
        String token = jwtUtils.getTokenFromRequest(request);
        String idEmpresa = jwtUtils.getClaimsFromJwtToken(token).get("empresa_id").toString();

        Empresa empresa = this.findById(Long.parseLong(idEmpresa));
        UsuarioAdminEmpresa user = new UsuarioAdminEmpresa();
        user.setEmail(dados.email());
        user.setEmpresa(empresa);
        user.setNome(dados.nome());
        user.setPermissoes(Arrays.asList(Permissoes.ADMIN_EMPRESA));
        user.setSenha(passwordEncoder.encode(dados.senha()));
        return this.adminEmpresaRepository.save(user);
    }

    @Transactional
    public UsuarioAdminEmpresa updateUsuarioEmpresa(HttpServletRequest request, CadastroUsuarioAdmin dados, Long idUsuario) throws Exception{
        String token = jwtUtils.getTokenFromRequest(request);
        String idEmpresa = jwtUtils.getClaimsFromJwtToken(token).get("empresa_id").toString();

        Empresa empresa = this.findById(Long.parseLong(idEmpresa));
        UsuarioAdminEmpresa user = this.findUsuarioEmpresaByIdUsuarioAndIdEmpresa(empresa.getId(),idUsuario);
        user.setEmail(dados.email());
        user.setNome(dados.nome());
        user.setSenha(passwordEncoder.encode(dados.senha()));
        return user;
    }

    @Transactional
    public void handleAtivacaoUsuarioEmpresa(HttpServletRequest request, Long idUsuario, boolean ativar) throws Exception{
        String token = jwtUtils.getTokenFromRequest(request);
        String idEmpresa = jwtUtils.getClaimsFromJwtToken(token).get("empresa_id").toString();

        UsuarioAdminEmpresa adminEmpresa = this.findUsuarioEmpresaByIdUsuarioAndIdEmpresa(Long.parseLong(idEmpresa),idUsuario);
        adminEmpresa.setAtivo(ativar ? true : false);
        adminEmpresaRepository.save(adminEmpresa);
    }
}
