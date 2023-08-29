package com.trabalho.api.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.trabalho.api.dto.ClienteDTO;
import com.trabalho.api.dto.ResponseDTO;
import com.trabalho.api.model.Cliente;
import com.trabalho.api.request.CadastroUsuario;
import com.trabalho.api.service.ClienteService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/cliente")
public class ClienteController {
    private ClienteService clienteService;

    public ClienteController(ClienteService clienteService){
        this.clienteService = clienteService;
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ResponseDTO<ClienteDTO>> findById(@PathVariable Long id) throws Exception{
        Cliente cliente = this.clienteService.findById(id);
        ResponseDTO<ClienteDTO> responseDTO = ResponseDTO.build(ClienteDTO.convert(cliente), true, null, null);
        return new ResponseEntity<ResponseDTO<ClienteDTO>>(responseDTO, new HttpHeaders(), HttpStatus.OK);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<ResponseDTO<ClienteDTO>> update(@RequestBody @Valid CadastroUsuario cadastroCliente, @PathVariable Long id) throws Exception{
        Cliente cliente = this.clienteService.atualizar(cadastroCliente, id);
        ResponseDTO<ClienteDTO> responseDTO = ResponseDTO.build(ClienteDTO.convert(cliente), true, null, null);
        return new ResponseEntity<ResponseDTO<ClienteDTO>>(responseDTO, new HttpHeaders(), HttpStatus.OK);
    }

    @PutMapping(value = "/desativar/{id}")
    public ResponseEntity<?> desativar(@PathVariable Long id) throws Exception{
        this.clienteService.handleAtivacao(id, false);
        return new ResponseEntity<>(new ResponseDTO<>(null, null, "cliente desativado com sucesso", null), new HttpHeaders(), HttpStatus.OK);
    }

    @PutMapping(value = "/ativar/{id}")
    public ResponseEntity<?> ativar(@PathVariable Long id) throws Exception{
        this.clienteService.handleAtivacao(id, true);
        return new ResponseEntity<>(new ResponseDTO<>(null, null, "cliente ativado com sucesso", null), new HttpHeaders(), HttpStatus.OK);
    }
}
