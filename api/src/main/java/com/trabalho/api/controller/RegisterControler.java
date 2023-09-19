package com.trabalho.api.controller;

import java.util.Optional;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.trabalho.api.dto.ClienteDTO;
import com.trabalho.api.dto.ResponseDTO;
import com.trabalho.api.exception.BadRequestException;
import com.trabalho.api.model.Cliente;
import com.trabalho.api.model.Usuario;
import com.trabalho.api.repository.UsuarioRepository;
import com.trabalho.api.request.CadastroCliente;
import com.trabalho.api.service.ClienteService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/register")
public class RegisterControler {
    private final ClienteService clienteService;
    private final UsuarioRepository usuarioRepository;

    public RegisterControler(ClienteService clienteService, UsuarioRepository usuarioRepository){
        this.clienteService = clienteService;
        this.usuarioRepository = usuarioRepository;
    }

    @PostMapping
    public ResponseEntity<ResponseDTO<ClienteDTO>> cadastrar(@Valid @RequestBody CadastroCliente dados) throws Exception{
        Optional<Usuario> usuarioExiste = this.usuarioRepository.findByEmail(dados.email());

        if(dados.endereco() == null){
            throw new BadRequestException("informe o endereço");
        }
        if(usuarioExiste.isPresent()){
            throw new BadRequestException("dados inválidos");
        }

        Cliente cliente = this.clienteService.save(dados);
        ResponseDTO<ClienteDTO> responseDTO = ResponseDTO.build(ClienteDTO.convert(cliente), true, "cliente cadastrado com sucesso", null);
        return new ResponseEntity<ResponseDTO<ClienteDTO>>(responseDTO, new HttpHeaders(), HttpStatus.CREATED);
    }
}
