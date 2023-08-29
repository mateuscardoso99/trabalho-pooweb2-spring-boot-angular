package com.trabalho.api.controller;

import java.util.Collection;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.trabalho.api.dto.PedidoDTO;
import com.trabalho.api.dto.ResponseDTO;
import com.trabalho.api.model.Pedido;
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

    @GetMapping
    public ResponseEntity<ResponseDTO<Collection<PedidoDTO>>> findAllByCliente(){
        Collection<Pedido> pedidos = this.pedidoService.findAllByCliente();
        ResponseDTO<Collection<PedidoDTO>> responseDTO = ResponseDTO.build(PedidoDTO.convert(pedidos), null, null, null);
        return new ResponseEntity<ResponseDTO<Collection<PedidoDTO>>>(responseDTO, new HttpHeaders(), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ResponseDTO<PedidoDTO>> findById(Long id) throws Exception{
        Pedido pedido = this.pedidoService.findByIdCliente(id);
        ResponseDTO<PedidoDTO> response = ResponseDTO.build(PedidoDTO.convert(pedido), null, null, null);
        return new ResponseEntity<ResponseDTO<PedidoDTO>>(response, new HttpHeaders(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ResponseDTO<PedidoDTO>> salvar(@Valid @RequestBody CadastroPedido dados) throws Exception{
        Pedido pedido = this.pedidoService.salvar(dados);
        ResponseDTO<PedidoDTO> responseDTO = ResponseDTO.build(PedidoDTO.convert(pedido), true, "pedido salvo com sucesso", null);
        return new ResponseEntity<ResponseDTO<PedidoDTO>>(responseDTO, new HttpHeaders(), HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<ResponseDTO<?>> salvar(@PathVariable Long id) throws Exception{
        this.pedidoService.apagar(id);
        return new ResponseEntity<ResponseDTO<?>>(new ResponseDTO<>(null, true, "pedido removido com sucesso", null), new HttpHeaders(), HttpStatus.CREATED);
    }
}
