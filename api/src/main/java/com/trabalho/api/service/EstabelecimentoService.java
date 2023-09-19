package com.trabalho.api.service;

import java.util.Arrays;
import java.util.Collection;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.trabalho.api.exception.DataNotFoundException;
import com.trabalho.api.model.Empresa;
import com.trabalho.api.model.Endereco;
import com.trabalho.api.model.Estabelecimento;
import com.trabalho.api.model.Permissoes;
import com.trabalho.api.model.UsuarioAdminEstabelecimento;
import com.trabalho.api.repository.EmpresaRepository;
import com.trabalho.api.repository.EstabelecimentoRepository;
import com.trabalho.api.repository.UsuarioAdminEstabelecimentoRepository;
import com.trabalho.api.request.CadastroEstabelecimento;
import com.trabalho.api.request.CadastroUsuarioAdmin;
import com.trabalho.api.security.JwtUtils;

import jakarta.servlet.http.HttpServletRequest;

/**
 * os métodos que pegam o id_empresa do token, são chamados pelo EmpresaController
 * intuito é impedir que usuario de uma empresa veja dados de outra
 */
@Service
public class EstabelecimentoService {
    private final EstabelecimentoRepository estabelecimentoRepository;
    private final EmpresaRepository empresaRepository;
    private final JwtUtils jwtUtils;
    private final UsuarioAdminEstabelecimentoRepository adminEstabelecimentoRepository;
    private final PasswordEncoder passwordEncoder;

    public EstabelecimentoService(
        EstabelecimentoRepository estabelecimentoRepository, 
        EmpresaRepository empresaRepository, 
        JwtUtils jwtUtils,
        UsuarioAdminEstabelecimentoRepository adminEstabelecimentoRepository, 
        PasswordEncoder passwordEncoder
    ){
        this.estabelecimentoRepository = estabelecimentoRepository;
        this.empresaRepository = empresaRepository;
        this.jwtUtils = jwtUtils;
        this.adminEstabelecimentoRepository = adminEstabelecimentoRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Collection<Estabelecimento> findAll(){
        Collection<Estabelecimento> estabs = estabelecimentoRepository.findAll();
        return estabs;
    }

    public Estabelecimento findById(Long id) throws Exception{
        return estabelecimentoRepository.findById(id).orElseThrow(() -> new DataNotFoundException("estab não encontrado"));
    }

    public Estabelecimento findByIdAndEmpresaId(HttpServletRequest request, Long idEstab) throws Exception{
        String token = jwtUtils.getTokenFromRequest(request);
        String idEmpresa = jwtUtils.getClaimsFromJwtToken(token).get("empresa_id").toString();
        Estabelecimento estabelecimento = this.estabelecimentoRepository.findByIdAndEmpresaId(idEstab, Long.parseLong(idEmpresa)).orElseThrow(()->new DataNotFoundException("estab não encontrado"));
        return estabelecimento;
    }

    public Collection<Estabelecimento> findAllByEmpresa(HttpServletRequest request){
        String token = jwtUtils.getTokenFromRequest(request);
        String idEmpresa = jwtUtils.getClaimsFromJwtToken(token).get("empresa_id").toString();
        return estabelecimentoRepository.findAllByEmpresa(Long.parseLong(idEmpresa));
    }

    @Transactional
    public Estabelecimento salvar(CadastroEstabelecimento dados) throws Exception{
        Empresa empresa = this.empresaRepository.findById(dados.idEmpresa()).orElseThrow(() -> new DataNotFoundException("empresa não encontrada"));

        Endereco e = new Endereco();
        e.setBairro(dados.endereco().bairro());
        e.setCidade(dados.endereco().cidade());
        e.setComplemento(dados.endereco().complemento());
        e.setLatitude(dados.endereco().latitude());
        e.setLongitude(dados.endereco().longitude());
        e.setNumero(dados.endereco().numero());
        e.setRua(dados.endereco().rua());
        e.setUf(dados.endereco().uf());

        Estabelecimento estabelecimento = Estabelecimento.builder()
                            .nome(dados.nome())
                            .razaoSocial(dados.razaoSocial())
                            .horarioFuncionamento(dados.horarioFuncionamento())
                            .endereco(e)
                            .empresa(empresa)
                            .build();
        
        return this.estabelecimentoRepository.save(estabelecimento);
    }

    @Transactional
    public Estabelecimento atualizar(CadastroEstabelecimento dados, Long id) throws Exception{
        Empresa empresa = this.empresaRepository.findById(dados.idEmpresa()).orElseThrow(() -> new DataNotFoundException("Empresa não encontrada"));
        Estabelecimento estabelecimento = this.estabelecimentoRepository.findById(id).orElseThrow(() -> new DataNotFoundException("estabelecimento não encontrado"));
        Endereco e = empresa.getEndereco();
        e.setBairro(dados.endereco().bairro());
        e.setCidade(dados.endereco().cidade());
        e.setComplemento(dados.endereco().complemento());
        e.setLatitude(dados.endereco().latitude());
        e.setLongitude(dados.endereco().longitude());
        e.setNumero(dados.endereco().numero());
        e.setRua(dados.endereco().rua());
        e.setUf(dados.endereco().uf());

        estabelecimento.setNome(dados.nome());
        estabelecimento.setRazaoSocial(dados.razaoSocial());
        estabelecimento.setHorarioFuncionamento(dados.horarioFuncionamento());
        estabelecimento.setEndereco(e);
        estabelecimento.setEmpresa(empresa);

        return estabelecimento;
    }

    //metodo chamado pelo empresaController e admController, se for chamado pelo empresaController precisa validar se o estab pertence a empresa
    @Transactional
    public void handleAtivacao(Long idEstab, boolean ativar, HttpServletRequest request) throws Exception{
        Estabelecimento estabelecimento;
        if(request == null){
            estabelecimento = this.findById(idEstab);
        }
        else{
            estabelecimento = this.findByIdAndEmpresaId(request, idEstab);
        }
        estabelecimento.setAtivo(ativar ? true : false);
        estabelecimentoRepository.save(estabelecimento);
    }
    


    //USUARIOS
    public UsuarioAdminEstabelecimento findUsuarioEstabelecimentoByIdUsuario(Long id) throws Exception{
        return this.adminEstabelecimentoRepository.findById(id).orElseThrow(() -> new DataNotFoundException("Admin Estabelecimento não encontrado"));
    }

    public UsuarioAdminEstabelecimento findUsuarioEstabelecimentoByIdEstabelecimentoAndIdUsuario(Long idEstabelecimento, Long idUsuario) throws Exception{
        return this.adminEstabelecimentoRepository.findUsuarioEstabelecimento(idEstabelecimento,idUsuario).orElseThrow(() -> new DataNotFoundException("Admin Estabelecimento não encontrado"));
    }

    public Collection<UsuarioAdminEstabelecimento> findAllUsuariosByEstabelecimento(Long id){
        return this.adminEstabelecimentoRepository.findAllByEstabelecimento(id);
    }

    @Transactional
    public UsuarioAdminEstabelecimento salvarUsuarioEstabelecimento(CadastroUsuarioAdmin dados, Long idEstab) throws Exception{
        Estabelecimento e = this.findById(idEstab);
        UsuarioAdminEstabelecimento user = new UsuarioAdminEstabelecimento();
        user.setEmail(dados.email());
        user.setEstabelecimento(e);
        user.setNome(dados.nome());
        user.setPermissoes(Arrays.asList(Permissoes.ADMIN_ESTABELECIMENTO));
        user.setSenha(passwordEncoder.encode(dados.senha()));
        return this.adminEstabelecimentoRepository.save(user);
    }

    @Transactional
    public UsuarioAdminEstabelecimento updateUsuarioEstabelecimento(CadastroUsuarioAdmin dados, Long idEstabelecimento, Long idUsuario) throws Exception{
        Estabelecimento estabelecimento = this.findById(idEstabelecimento);
        UsuarioAdminEstabelecimento user = this.findUsuarioEstabelecimentoByIdEstabelecimentoAndIdUsuario(estabelecimento.getId(),idUsuario);
        user.setEmail(dados.email());
        user.setNome(dados.nome());
        user.setSenha(passwordEncoder.encode(dados.senha()));
        return user;
    }

    @Transactional
    public void handleAtivacaoUsuarioEstabelecimento(Long idUsuario, Long idEstabelecimento, boolean ativar) throws Exception{
        UsuarioAdminEstabelecimento adminEstabelecimento = this.findUsuarioEstabelecimentoByIdEstabelecimentoAndIdUsuario(idEstabelecimento,idUsuario);
        adminEstabelecimento.setAtivo(ativar ? true : false);
        adminEstabelecimentoRepository.save(adminEstabelecimento);
    }
}
