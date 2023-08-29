package com.trabalho.api.service;

import org.springframework.stereotype.Service;

import com.trabalho.api.exception.DataNotFoundException;
import com.trabalho.api.model.AdminEstabelecimento;
import com.trabalho.api.repository.AdminEstabelecimentoRepository;

@Service
public class AdminEstabelecimentoService {
    private final AdminEstabelecimentoRepository adminEstabelecimentoRepository;

    public AdminEstabelecimentoService(AdminEstabelecimentoRepository adminEstabelecimentoRepository){
        this.adminEstabelecimentoRepository = adminEstabelecimentoRepository;
    }

    public AdminEstabelecimento findById(Long id) throws Exception{
        return this.adminEstabelecimentoRepository.findById(id).orElseThrow(() -> new DataNotFoundException("Admin Estabelecimento não encontrado"));
    }
}
