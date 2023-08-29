package com.trabalho.api.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.trabalho.api.dto.PedidoDTO;
import com.trabalho.api.dto.ResponseDTO;
import com.trabalho.api.request.CadastroPedido;
import com.trabalho.api.service.PedidoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/cliente/pedido")
public class PedidoController {
    private final PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService){
        this.pedidoService = pedidoService;
    }

    @PostMapping
    public ResponseEntity<ResponseDTO<PedidoDTO>> salvar(@Valid @RequestBody CadastroPedido dados) throws Exception{
        PedidoDTO pedidoDTO = this.pedidoService.salvar(dados);
        ResponseDTO<PedidoDTO> responseDTO = ResponseDTO.build(pedidoDTO, true, "pedido salvo com sucesso", null);
        return new ResponseEntity<ResponseDTO<PedidoDTO>>(responseDTO, new HttpHeaders(), HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> salvar(@PathVariable Long id) throws Exception{
        this.pedidoService.apagar(id);
        return new ResponseEntity<String>("Pedido removido com sucesso", new HttpHeaders(), HttpStatus.CREATED);
    }
}
