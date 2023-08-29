package com.trabalho.api.service;

import java.util.Collection;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.trabalho.api.exception.DataNotFoundException;
import com.trabalho.api.model.Empresa;
import com.trabalho.api.model.Endereco;
import com.trabalho.api.repository.EmpresaRepository;
import com.trabalho.api.request.CadastroEmpresa;

@Service
public class EmpresaService {
    private final EmpresaRepository empresaRepository;

    public EmpresaService(EmpresaRepository empresaRepository){
        this.empresaRepository = empresaRepository;
    }

    public Collection<Empresa> findAll(){
        return empresaRepository.findAll();
    }

    public Empresa findById(Long id) throws Exception{
        return empresaRepository.findById(id).orElseThrow(() -> new DataNotFoundException("Empresa não encontrada"));
    }

    @Transactional
    public Empresa salvar(CadastroEmpresa dados){
        Endereco endereco = dados.getEndereco().toEndereco();
        Empresa empresa = Empresa.builder()
                            .nome(dados.getNome())
                            .razaoSocial(dados.getRazaoSocial())
                            .endereco(endereco)
                            .build();
        return this.empresaRepository.save(empresa);
    }

    @Transactional
    public Empresa atualizar(CadastroEmpresa dados, Long id) throws Exception{
        Empresa empresa = empresaRepository.findById(id).orElseThrow(() -> new DataNotFoundException("Empresa não encontrada"));

        Endereco e = empresa.getEndereco();
        e.setBairro(dados.getEndereco().getBairro());
        e.setCidade(dados.getEndereco().getCidade());
        e.setComplemento(dados.getEndereco().getComplemento());
        e.setLatitude(dados.getEndereco().getLatitude());
        e.setLongitude(dados.getEndereco().getLongitude());
        e.setNumero(dados.getEndereco().getNumero());
        e.setRua(dados.getEndereco().getRua());
        e.setUf(dados.getEndereco().getUf());

        empresa.setNome(dados.getNome());
        empresa.setRazaoSocial(dados.getRazaoSocial());
        empresa.setEndereco(e);

        return this.empresaRepository.save(empresa);
    }

    @Transactional
    public void handleAtivacao(Long id, boolean ativar) throws Exception{
        Empresa empresa = this.findById(id);
        empresa.setAtivo(ativar ? true : false);
        empresaRepository.save(empresa);
    }
}
