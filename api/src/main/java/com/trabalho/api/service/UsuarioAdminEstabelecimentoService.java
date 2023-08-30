package com.trabalho.api.service;

import java.util.Arrays;
import java.util.Collection;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.trabalho.api.exception.DataNotFoundException;
import com.trabalho.api.model.UsuarioAdminEstabelecimento;
import com.trabalho.api.model.Estabelecimento;
import com.trabalho.api.model.Permissoes;
import com.trabalho.api.repository.UsuarioAdminEstabelecimentoRepository;
import com.trabalho.api.request.CadastroUsuario;

@Service
public class UsuarioAdminEstabelecimentoService {
    private final UsuarioAdminEstabelecimentoRepository adminEstabelecimentoRepository;
    private final EstabelecimentoService estabelecimentoService;
    private final PasswordEncoder passwordEncoder;

    public UsuarioAdminEstabelecimentoService(UsuarioAdminEstabelecimentoRepository adminEstabelecimentoRepository, EstabelecimentoService estabelecimentoService, PasswordEncoder passwordEncoder){
        this.adminEstabelecimentoRepository = adminEstabelecimentoRepository;
        this.passwordEncoder = passwordEncoder;
        this.estabelecimentoService = estabelecimentoService;
    }

    public UsuarioAdminEstabelecimento findById(Long id) throws Exception{
        return this.adminEstabelecimentoRepository.findById(id).orElseThrow(() -> new DataNotFoundException("Admin Estabelecimento não encontrado"));
    }

    public UsuarioAdminEstabelecimento findUsuarioEstabelecimento(Long idEstabelecimento, Long idUsuario) throws Exception{
        return this.adminEstabelecimentoRepository.findUsuarioEstabelecimento(idEstabelecimento,idUsuario).orElseThrow(() -> new DataNotFoundException("Admin Estabelecimento não encontrado"));
    }

    public Collection<UsuarioAdminEstabelecimento> findAllByEstabelecimento(Long id){
        return this.adminEstabelecimentoRepository.findAllByEstabelecimento(id);
    }

    @Transactional
    public UsuarioAdminEstabelecimento salvar(CadastroUsuario dados, Long idEstab) throws Exception{
        Estabelecimento e = this.estabelecimentoService.findById(idEstab);
        UsuarioAdminEstabelecimento user = new UsuarioAdminEstabelecimento();
        user.setEmail(dados.getEmail());
        user.setEstabelecimento(e);
        user.setNome(dados.getNome());
        user.setPermissoes(Arrays.asList(Permissoes.ADMIN_ESTABELECIMENTO));
        user.setSenha(passwordEncoder.encode(dados.getSenha()));
        return this.adminEstabelecimentoRepository.save(user);
    }

    @Transactional
    public UsuarioAdminEstabelecimento update(CadastroUsuario dados, Long idEstabelecimento, Long idUsuario) throws Exception{
        Estabelecimento estabelecimento = this.estabelecimentoService.findById(idEstabelecimento);
        UsuarioAdminEstabelecimento user = this.findUsuarioEstabelecimento(estabelecimento.getId(),idUsuario);
        user.setEmail(dados.getEmail());
        user.setNome(dados.getNome());
        user.setSenha(passwordEncoder.encode(dados.getSenha()));
        return this.adminEstabelecimentoRepository.save(user);
    }

    @Transactional
    public void handleAtivacao(Long idUsuario, Long idEstabelecimento, boolean ativar) throws Exception{
        UsuarioAdminEstabelecimento adminEstabelecimento = this.findUsuarioEstabelecimento(idEstabelecimento,idUsuario);
        adminEstabelecimento.setAtivo(ativar ? true : false);
        adminEstabelecimentoRepository.save(adminEstabelecimento);
    }
}
