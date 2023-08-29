package com.trabalho.api.service;

import org.springframework.stereotype.Service;

import com.trabalho.api.exception.DataNotFoundException;
import com.trabalho.api.model.AdminEmpresa;
import com.trabalho.api.repository.AdminEmpresaRepository;

@Service
public class AdminEmpresaService {
    private final AdminEmpresaRepository adminEmpresaRepository;

    public AdminEmpresaService(AdminEmpresaRepository adminEmpresaRepository){
        this.adminEmpresaRepository = adminEmpresaRepository;
    }

    public AdminEmpresa findById(Long id) throws Exception{
        return this.adminEmpresaRepository.findById(id).orElseThrow(() -> new DataNotFoundException("Admin empresa não encontrado"));
    }
}
