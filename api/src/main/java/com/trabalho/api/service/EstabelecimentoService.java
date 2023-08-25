package com.trabalho.api.service;

import java.util.Collection;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.trabalho.api.dto.EstabelecimentoDTO;
import com.trabalho.api.exception.DataNotFoundException;
import com.trabalho.api.model.Empresa;
import com.trabalho.api.model.Endereco;
import com.trabalho.api.model.Estabelecimento;
import com.trabalho.api.repository.EmpresaRepository;
import com.trabalho.api.repository.EstabelecimentoRepository;
import com.trabalho.api.request.CadastroEstabelecimento;

@Service
public class EstabelecimentoService {
    private final EstabelecimentoRepository estabelecimentoRepository;
    private final EmpresaRepository empresaRepository;

    public EstabelecimentoService(EstabelecimentoRepository estabelecimentoRepository, EmpresaRepository empresaRepository){
        this.estabelecimentoRepository = estabelecimentoRepository;
        this.empresaRepository = empresaRepository;
    }

    public Collection<EstabelecimentoDTO> findAll(){
        Collection<Estabelecimento> estabs = estabelecimentoRepository.findAll();
        return EstabelecimentoDTO.convert(estabs);
    }

    public EstabelecimentoDTO findById(Long id) throws Exception{
        Estabelecimento est = estabelecimentoRepository.findById(id).orElseThrow(() -> new DataNotFoundException("estab não encontrado"));
        return EstabelecimentoDTO.convert(est);
    }

    @Transactional
    public EstabelecimentoDTO salvar(CadastroEstabelecimento dados) throws Exception{
        Empresa empresa = this.empresaRepository.findById(dados.getIdEmpresa()).orElseThrow(() -> new DataNotFoundException("empresa não encontrada"));
        Endereco endereco = dados.getEndereco().toEndereco();

        Estabelecimento estabelecimento = Estabelecimento.builder()
                            .nome(dados.getNome())
                            .razaoSocial(dados.getRazaoSocial())
                            .horarioFuncionamento(dados.getHorarioFuncionamento())
                            .endereco(endereco)
                            .empresa(empresa)
                            .build();
        
        estabelecimento = this.estabelecimentoRepository.save(estabelecimento);
        return EstabelecimentoDTO.convert(estabelecimento);
    }

    @Transactional
    public EstabelecimentoDTO atualizar(CadastroEstabelecimento dados, Long id) throws Exception{
        Empresa empresa = this.empresaRepository.findById(dados.getIdEmpresa()).orElseThrow(() -> new DataNotFoundException("Empresa não encontrada"));
        Estabelecimento estabelecimento = this.estabelecimentoRepository.findById(id).orElseThrow(() -> new DataNotFoundException("estabelecimento não encontrado"));
        Endereco e = empresa.getEndereco();
        e.setBairro(dados.getEndereco().getBairro());
        e.setCidade(dados.getEndereco().getCidade());
        e.setComplemento(dados.getEndereco().getComplemento());
        e.setLatitude(dados.getEndereco().getLatitude());
        e.setLongitude(dados.getEndereco().getLongitude());
        e.setNumero(dados.getEndereco().getNumero());
        e.setRua(dados.getEndereco().getRua());
        e.setUf(dados.getEndereco().getUf());

        estabelecimento.setNome(dados.getNome());
        estabelecimento.setRazaoSocial(dados.getRazaoSocial());
        estabelecimento.setHorarioFuncionamento(dados.getHorarioFuncionamento());
        estabelecimento.setEndereco(e);
        estabelecimento.setEmpresa(empresa);

        estabelecimento = this.estabelecimentoRepository.save(estabelecimento);
        return EstabelecimentoDTO.convert(estabelecimento);
    }

    @Transactional
    public void apagar(Long id) throws Exception{
        Estabelecimento estabelecimento = this.estabelecimentoRepository.findById(id).orElseThrow(() -> new DataNotFoundException("estabelecimento não encontrado"));
        estabelecimentoRepository.deleteById(estabelecimento.getId());
    }
}
