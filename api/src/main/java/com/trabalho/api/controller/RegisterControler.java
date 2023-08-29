package com.trabalho.api.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.trabalho.api.dto.ClienteDTO;
import com.trabalho.api.dto.ResponseDTO;
import com.trabalho.api.model.Cliente;
import com.trabalho.api.request.CadastroCliente;
import com.trabalho.api.service.ClienteService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/register")
public class RegisterControler {
    private final ClienteService clienteService;

    public RegisterControler(ClienteService clienteService){
        this.clienteService = clienteService;
    }

    @PostMapping
    public ResponseEntity<ResponseDTO<ClienteDTO>> cadastrar(@RequestBody @Valid CadastroCliente dados){
        Cliente cliente = this.clienteService.save(dados);
        ResponseDTO<ClienteDTO> responseDTO = ResponseDTO.build(ClienteDTO.convert(cliente), true, "cliente cadastrado com sucesso", null);
        return new ResponseEntity<ResponseDTO<ClienteDTO>>(responseDTO, new HttpHeaders(), HttpStatus.CREATED);
    }
}
