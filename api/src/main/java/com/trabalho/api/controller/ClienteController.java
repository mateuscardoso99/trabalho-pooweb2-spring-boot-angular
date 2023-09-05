package com.trabalho.api.controller;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.trabalho.api.dto.ClienteDTO;
import com.trabalho.api.dto.PedidoDTO;
import com.trabalho.api.dto.ResponseDTO;
import com.trabalho.api.model.Cliente;
import com.trabalho.api.model.Pedido;
import com.trabalho.api.request.CadastroCliente;
import com.trabalho.api.request.CadastroPedido;
import com.trabalho.api.service.ClienteService;
import com.trabalho.api.service.PedidoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/cliente")
public class ClienteController {
    private final ClienteService clienteService;
    private final PedidoService pedidoService;

    //parametros são injetados automaticamente pelo spring pois ClienteController, ClienteService e PedidoService são componentes spring
    //se o construtor fosse assim: public ClienteController(ClienteService clienteService, PedidoService pedidoService, String g){} não funcionaria pois variavel g não é componente spring

    public ClienteController(ClienteService clienteService, PedidoService pedidoService){
        this.clienteService = clienteService;
        this.pedidoService = pedidoService;
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ResponseDTO<ClienteDTO>> findById(@PathVariable Long id) throws Exception{
        Cliente cliente = this.clienteService.findById(id);
        ResponseDTO<ClienteDTO> responseDTO = ResponseDTO.build(ClienteDTO.convert(cliente), true, null, null);
        return new ResponseEntity<ResponseDTO<ClienteDTO>>(responseDTO, new HttpHeaders(), HttpStatus.OK);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<ResponseDTO<ClienteDTO>> update(@RequestBody @Valid CadastroCliente cadastroCliente, @PathVariable Long id) throws Exception{
        Cliente cliente = this.clienteService.atualizar(cadastroCliente, id);
        ResponseDTO<ClienteDTO> responseDTO = ResponseDTO.build(ClienteDTO.convert(cliente), true, null, null);
        return new ResponseEntity<ResponseDTO<ClienteDTO>>(responseDTO, new HttpHeaders(), HttpStatus.OK);
    }

    @PutMapping(value = "/desativar/{id}")
    public ResponseEntity<?> desativarCliente(@PathVariable Long id) throws Exception{
        this.clienteService.handleAtivacao(id, false);
        return new ResponseEntity<>(new ResponseDTO<>(null, null, "cliente desativado com sucesso", null), new HttpHeaders(), HttpStatus.OK);
    }

    @PutMapping(value = "/ativar/{id}")
    public ResponseEntity<?> ativarCliente(@PathVariable Long id) throws Exception{
        this.clienteService.handleAtivacao(id, true);
        return new ResponseEntity<>(new ResponseDTO<>(null, null, "cliente ativado com sucesso", null), new HttpHeaders(), HttpStatus.OK);
    }

    //pedidos
    @GetMapping(value = "/pedidos")
    public ResponseEntity<ResponseDTO<Collection<PedidoDTO>>> findAllPedidosByCliente(){
        Collection<Pedido> pedidos = this.pedidoService.findAllByCliente();
        ResponseDTO<Collection<PedidoDTO>> responseDTO = ResponseDTO.build(PedidoDTO.convert(pedidos), null, null, null);
        return new ResponseEntity<ResponseDTO<Collection<PedidoDTO>>>(responseDTO, new HttpHeaders(), HttpStatus.OK);
    }

    @GetMapping(value = "/pedidos/{id}")
    public ResponseEntity<ResponseDTO<PedidoDTO>> findPedidoByIdAndClienteId(@PathVariable Long id) throws Exception{
        Pedido pedido = this.pedidoService.findByIdAndClienteId(id);
        ResponseDTO<PedidoDTO> response = ResponseDTO.build(PedidoDTO.convert(pedido), null, null, null);
        return new ResponseEntity<ResponseDTO<PedidoDTO>>(response, new HttpHeaders(), HttpStatus.OK);
    }

    @PostMapping(value = "/pedidos")
    public ResponseEntity<ResponseDTO<PedidoDTO>> salvarPedido(@Valid @RequestBody CadastroPedido dados) throws Exception{
        Pedido pedido = this.pedidoService.salvar(dados);
        ResponseDTO<PedidoDTO> responseDTO = ResponseDTO.build(PedidoDTO.convert(pedido), true, "pedido salvo com sucesso", null);
        return new ResponseEntity<ResponseDTO<PedidoDTO>>(responseDTO, new HttpHeaders(), HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/pedidos/desativar/{id}")
    public ResponseEntity<ResponseDTO<?>> desativarPedido(@PathVariable Long id) throws Exception{
        this.pedidoService.handleAtivacao(id, false);
        return new ResponseEntity<ResponseDTO<?>>(new ResponseDTO<>(null, true, "pedido desativado com sucesso", null), new HttpHeaders(), HttpStatus.OK);
    }

    @PutMapping(value = "/pedidos/ativar/{id}")
    public ResponseEntity<ResponseDTO<?>> ativarPedido(@PathVariable Long id) throws Exception{
        this.pedidoService.handleAtivacao(id, true);
        return new ResponseEntity<ResponseDTO<?>>(new ResponseDTO<>(null, true, "pedido ativado com sucesso", null), new HttpHeaders(), HttpStatus.OK);
    }
}
