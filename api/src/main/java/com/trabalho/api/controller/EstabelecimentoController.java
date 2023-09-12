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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.trabalho.api.dto.UsuarioAdminEmpresaDTO;
import com.trabalho.api.dto.UsuarioAdminEstabelecimentoDTO;
import com.trabalho.api.dto.EstabelecimentoDTO;
import com.trabalho.api.dto.PedidoDTO;
import com.trabalho.api.dto.ResponseDTO;
import com.trabalho.api.model.UsuarioAdminEstabelecimento;
import com.trabalho.api.model.Pedido.StatusPedido;
import com.trabalho.api.model.Estabelecimento;
import com.trabalho.api.model.Pedido;
import com.trabalho.api.request.CadastroEstabelecimento;
import com.trabalho.api.request.CadastroUsuarioAdmin;
import com.trabalho.api.service.EstabelecimentoService;
import com.trabalho.api.service.PedidoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/estabelecimento")
public class EstabelecimentoController {
    private final EstabelecimentoService estabelecimentoService;
    private final PedidoService pedidoService;

    public EstabelecimentoController(EstabelecimentoService estabelecimentoService, PedidoService pedidoService){
        this.estabelecimentoService = estabelecimentoService;
        this.pedidoService = pedidoService;
    }

    @GetMapping(value = "/{idEstabelecimento}")
    @ResponseBody
    public ResponseEntity<ResponseDTO<EstabelecimentoDTO>> findById(@PathVariable Long idEstabelecimento) throws Exception{
        Estabelecimento estabelecimento = estabelecimentoService.findById(idEstabelecimento);
        ResponseDTO<EstabelecimentoDTO> responseDTO = ResponseDTO.build(EstabelecimentoDTO.convert(estabelecimento), true, null, null);
        return new ResponseEntity<ResponseDTO<EstabelecimentoDTO>>(responseDTO, new HttpHeaders(), HttpStatus.OK);
    }

    @PutMapping(value = "/{idEstabelecimento}")
    @ResponseBody
    public ResponseEntity<ResponseDTO<EstabelecimentoDTO>> update(@RequestBody @Valid CadastroEstabelecimento dados, @PathVariable Long idEstabelecimento) throws Exception{
        Estabelecimento estabelecimento = estabelecimentoService.atualizar(dados, idEstabelecimento);
        ResponseDTO<EstabelecimentoDTO> responseDTO = ResponseDTO.build(EstabelecimentoDTO.convert(estabelecimento), true, "estabelecimento salvo com sucesso", null);
        return new ResponseEntity<ResponseDTO<EstabelecimentoDTO>>(responseDTO, new HttpHeaders(), HttpStatus.OK);
    }

    //usuários
    @GetMapping(value = "/{idEstabelecimento}/usuarios")
    public ResponseEntity<ResponseDTO<Collection<UsuarioAdminEstabelecimentoDTO>>> findAllUsuariosEstabelecimento(@PathVariable Long idEstabelecimento){
        Collection<UsuarioAdminEstabelecimento> usuarios = this.estabelecimentoService.findAllUsuariosByEstabelecimento(idEstabelecimento);
        ResponseDTO<Collection<UsuarioAdminEstabelecimentoDTO>> responseDTO = ResponseDTO.build(UsuarioAdminEstabelecimentoDTO.convert(usuarios), true, null, null);
        return new ResponseEntity<ResponseDTO<Collection<UsuarioAdminEstabelecimentoDTO>>>(responseDTO, new HttpHeaders(), HttpStatus.OK);
    }

    @GetMapping(value = "/{idEstabelecimento}/buscar-usuario/{idUsuario}")
    public ResponseEntity<ResponseDTO<UsuarioAdminEstabelecimentoDTO>> findUsuarioEstab(@PathVariable Long idEstabelecimento, @PathVariable Long idUsuario) throws Exception{
        UsuarioAdminEstabelecimento usuario = this.estabelecimentoService.findUsuarioEstabelecimentoByIdEstabelecimentoAndIdUsuario(idEstabelecimento,idUsuario);
        ResponseDTO<UsuarioAdminEstabelecimentoDTO> responseDTO = ResponseDTO.build(UsuarioAdminEstabelecimentoDTO.convert(usuario), true, null, null);
        return new ResponseEntity<ResponseDTO<UsuarioAdminEstabelecimentoDTO>>(responseDTO, new HttpHeaders(), HttpStatus.OK);
    }

    @PostMapping(value = "/{idEstabelecimento}/usuario")
    public ResponseEntity<ResponseDTO<UsuarioAdminEstabelecimentoDTO>> criarUsuarioEmpresa(@Valid @RequestBody CadastroUsuarioAdmin dados, @PathVariable Long idEstabelecimento) throws Exception{
        UsuarioAdminEstabelecimento usuario = this.estabelecimentoService.salvarUsuarioEstabelecimento(dados, idEstabelecimento);
        ResponseDTO<UsuarioAdminEstabelecimentoDTO> responseDTO = ResponseDTO.build(UsuarioAdminEstabelecimentoDTO.convert(usuario), true, "usuario cadastrado com sucesso", null);
        return new ResponseEntity<ResponseDTO<UsuarioAdminEstabelecimentoDTO>>(responseDTO, new HttpHeaders(), HttpStatus.OK);
    }

    @PutMapping(value = "/{idEstabelecimento}/update-usuario/{idUsuario}")
    public ResponseEntity<ResponseDTO<UsuarioAdminEstabelecimentoDTO>> updateUsuarioEstabelecimento(@Valid @RequestBody CadastroUsuarioAdmin dados, @PathVariable Long idEstabelecimento, @PathVariable Long idUsuario) throws Exception{
        UsuarioAdminEstabelecimento usuario = this.estabelecimentoService.updateUsuarioEstabelecimento(dados,idEstabelecimento, idUsuario);
        return new ResponseEntity<ResponseDTO<UsuarioAdminEstabelecimentoDTO>>(new ResponseDTO<UsuarioAdminEstabelecimentoDTO>(UsuarioAdminEstabelecimentoDTO.convert(usuario), true, "usuario salvo com sucesso", null), new HttpHeaders(), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{idEstabelecimento}/desativar-usuario/{idUsuario}")
    public ResponseEntity<ResponseDTO<?>> desativarUsuarioEmpresa(@PathVariable Long idEstabelecimento, @PathVariable Long idUsuario) throws Exception{
        this.estabelecimentoService.handleAtivacaoUsuarioEstabelecimento(idUsuario,idEstabelecimento,false);
        return new ResponseEntity<>(new ResponseDTO<>(null, true, "usuario desativado com sucesso", null), new HttpHeaders(), HttpStatus.OK);
    }

    @PutMapping(value = "/{idEstabelecimento}/ativar-usuario/{idUsuario}")
    public ResponseEntity<ResponseDTO<UsuarioAdminEmpresaDTO>> ativarUsuarioEmpresa(@PathVariable Long idEstabelecimento, @PathVariable Long idUsuario) throws Exception{
        this.estabelecimentoService.handleAtivacaoUsuarioEstabelecimento(idUsuario,idEstabelecimento,true);
        return new ResponseEntity<>(new ResponseDTO<>(null, true, "usuario ativado com sucesso", null), new HttpHeaders(), HttpStatus.OK);
    }

    //pedidos
    @GetMapping(value = "/{idEstabelecimento}/pedidos")
    public ResponseEntity<ResponseDTO<Collection<PedidoDTO>>> findAllByEstabelecimento(@PathVariable Long idEstabelecimento){
        Collection<Pedido> pedidos = this.pedidoService.findAllByEstabelecimento(idEstabelecimento);
        ResponseDTO<Collection<PedidoDTO>> responseDTO = ResponseDTO.build(PedidoDTO.convert(pedidos), null, null, null);
        return new ResponseEntity<ResponseDTO<Collection<PedidoDTO>>>(responseDTO, new HttpHeaders(), HttpStatus.OK);
    }

    @GetMapping(value = "/{idEstabelecimento}/pedidos/{idPedido}")
    public ResponseEntity<ResponseDTO<PedidoDTO>> findByIdAndEstabelecimentoId(@PathVariable Long idEstabelecimento, @PathVariable(name = "idPedido") Long id) throws Exception{
        Pedido pedido = this.pedidoService.findByIdAndEstabelecimentoId(idEstabelecimento, id);
        ResponseDTO<PedidoDTO> response = ResponseDTO.build(PedidoDTO.convert(pedido), null, null, null);
        return new ResponseEntity<ResponseDTO<PedidoDTO>>(response, new HttpHeaders(), HttpStatus.OK);
    }

    @PutMapping(value = "/{idEstabelecimento}/pedidos/{idPedido}")
    public ResponseEntity<ResponseDTO<PedidoDTO>> mudarStatusPedido(@PathVariable Long idEstabelecimento, @PathVariable(name = "idPedido") Long idPedido, @RequestParam(name = "status") StatusPedido statusPedido) throws Exception{
        Pedido pedido = this.pedidoService.mudarStatus(idEstabelecimento, idPedido, statusPedido);
        ResponseDTO<PedidoDTO> response = ResponseDTO.build(PedidoDTO.convert(pedido), true, null, null);
        return new ResponseEntity<ResponseDTO<PedidoDTO>>(response, new HttpHeaders(), HttpStatus.OK);
    }
}
