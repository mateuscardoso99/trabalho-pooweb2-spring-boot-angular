package com.trabalho.api.service;

import org.springframework.stereotype.Service;

import com.trabalho.api.dto.AdminEmpresaDTO;
import com.trabalho.api.exception.DataNotFoundException;
import com.trabalho.api.model.AdminEmpresa;
import com.trabalho.api.repository.AdminEmpresaRepository;

@Service
public class AdminEmpresaService {
    private final AdminEmpresaRepository adminEmpresaRepository;

    public AdminEmpresaService(AdminEmpresaRepository adminEmpresaRepository){
        this.adminEmpresaRepository = adminEmpresaRepository;
    }

    public AdminEmpresaDTO findById(Long id) throws Exception{
        AdminEmpresa adminEmpresa = this.adminEmpresaRepository.findById(id).orElseThrow(() -> new DataNotFoundException("Admin empresa não encontrado"));
        return AdminEmpresaDTO.convert(adminEmpresa);
    }
}
