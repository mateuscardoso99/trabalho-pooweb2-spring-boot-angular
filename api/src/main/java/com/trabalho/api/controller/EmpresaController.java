package com.trabalho.api.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.trabalho.api.dto.EmpresaDTO;
import com.trabalho.api.dto.ResponseDTO;
import com.trabalho.api.model.Empresa;
import com.trabalho.api.request.CadastroEmpresa;
import com.trabalho.api.service.EmpresaService;

import jakarta.validation.Valid;

import java.util.Collection;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping(value = "/empresa")
public class EmpresaController {
    private final EmpresaService empresaService;

    public EmpresaController(EmpresaService empresaService){
        this.empresaService = empresaService;
    }

    @GetMapping
    @ResponseBody
    public ResponseEntity<ResponseDTO<Collection<EmpresaDTO>>> findAll(){
        Collection<Empresa> empresas = empresaService.findAll();
        ResponseDTO<Collection<EmpresaDTO>> responseDTO = ResponseDTO.build(EmpresaDTO.convert(empresas), true, null, null);
        return new ResponseEntity<ResponseDTO<Collection<EmpresaDTO>>>(responseDTO, new HttpHeaders(), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    @ResponseBody
    public ResponseEntity<ResponseDTO<EmpresaDTO>> findById(@PathVariable Long id) throws Exception{
        Empresa empresa = empresaService.findById(id);
        ResponseDTO<EmpresaDTO> responseDTO = ResponseDTO.build(EmpresaDTO.convert(empresa), true, null, null);
        return new ResponseEntity<ResponseDTO<EmpresaDTO>>(responseDTO, new HttpHeaders(), HttpStatus.OK);
    }

    @PostMapping
    @ResponseBody
    public ResponseEntity<ResponseDTO<EmpresaDTO>> cadastrar(@RequestBody @Valid CadastroEmpresa dados) {
        Empresa empresa = empresaService.salvar(dados);
        ResponseDTO<EmpresaDTO> responseDTO = ResponseDTO.build(EmpresaDTO.convert(empresa), true, "empresa salva com sucesso", null);
        return new ResponseEntity<ResponseDTO<EmpresaDTO>>(responseDTO, new HttpHeaders(), HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}")
    @ResponseBody
    public ResponseEntity<ResponseDTO<EmpresaDTO>> update(@RequestBody @Valid CadastroEmpresa dados, @PathVariable Long id) throws Exception{
        Empresa empresa = empresaService.atualizar(dados, id);
        ResponseDTO<EmpresaDTO> responseDTO = ResponseDTO.build(EmpresaDTO.convert(empresa), true, "empresa salva com sucesso", null);
        return new ResponseEntity<ResponseDTO<EmpresaDTO>>(responseDTO, new HttpHeaders(), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseBody
    public ResponseEntity<?> apagar(@PathVariable Long id) throws Exception{
        this.empresaService.apagar(id);
        ResponseDTO<?> responseDTO = ResponseDTO.build(null, true, "apagado com sucesso", null);
        return new ResponseEntity<>(responseDTO, new HttpHeaders(), HttpStatus.OK);
    }
}
