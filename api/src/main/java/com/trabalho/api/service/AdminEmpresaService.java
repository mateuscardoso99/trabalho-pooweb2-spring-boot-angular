package com.trabalho.api.service;

import java.util.Arrays;
import java.util.Collection;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.trabalho.api.exception.DataNotFoundException;
import com.trabalho.api.model.AdminEmpresa;
import com.trabalho.api.model.Empresa;
import com.trabalho.api.model.Permissoes;
import com.trabalho.api.repository.AdminEmpresaRepository;
import com.trabalho.api.request.CadastroUsuario;

@Service
public class AdminEmpresaService {
    private final AdminEmpresaRepository adminEmpresaRepository;
    private final EmpresaService empresaService;
    private final PasswordEncoder passwordEncoder;

    public AdminEmpresaService(AdminEmpresaRepository adminEmpresaRepository, EmpresaService empresaService, PasswordEncoder passwordEncoder){
        this.adminEmpresaRepository = adminEmpresaRepository;
        this.empresaService = empresaService;
        this.passwordEncoder = passwordEncoder;
    }

    public AdminEmpresa findById(Long id) throws Exception{
        return this.adminEmpresaRepository.findById(id).orElseThrow(() -> new DataNotFoundException("Admin empresa não encontrado"));
    }

    public AdminEmpresa findUsuarioEmpresa(Long idEmpresa, Long idUsuario) throws Exception{
        return this.adminEmpresaRepository.findUsuarioEmpresa(idEmpresa,idUsuario).orElseThrow(() -> new DataNotFoundException("Admin empresa não encontrado"));
    }

    public Collection<AdminEmpresa> findAllByEmpresa(Long idEmpresa){
        return this.adminEmpresaRepository.findAllByEmpresa(idEmpresa);
    }

    @Transactional
    public AdminEmpresa salvar(CadastroUsuario dados, Long idEmpresa) throws Exception{
        Empresa empresa = this.empresaService.findById(idEmpresa);
        AdminEmpresa user = new AdminEmpresa();
        user.setEmail(dados.getEmail());
        user.setEmpresa(empresa);
        user.setNome(dados.getNome());
        user.setPermissoes(Arrays.asList(Permissoes.ADMIN_EMPRESA));
        user.setSenha(passwordEncoder.encode(dados.getSenha()));
        return this.adminEmpresaRepository.save(user);
    }

    @Transactional
    public AdminEmpresa update(CadastroUsuario dados, Long idEmpresa, Long idUsuario) throws Exception{
        Empresa empresa = this.empresaService.findById(idEmpresa);
        AdminEmpresa user = this.findUsuarioEmpresa(empresa.getId(),idUsuario);
        user.setEmail(dados.getEmail());
        user.setNome(dados.getNome());
        user.setSenha(passwordEncoder.encode(dados.getSenha()));
        return this.adminEmpresaRepository.save(user);
    }

    @Transactional
    public void handleAtivacao(Long idUsuario, Long idEmpresa, boolean ativar) throws Exception{
        AdminEmpresa adminEmpresa = this.findUsuarioEmpresa(idEmpresa,idUsuario);
        adminEmpresa.setAtivo(ativar ? true : false);
        adminEmpresaRepository.save(adminEmpresa);
    }
}
