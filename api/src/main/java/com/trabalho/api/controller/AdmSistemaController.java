package com.trabalho.api.controller;

import java.util.Collection;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.trabalho.api.dto.ClienteDTO;
import com.trabalho.api.dto.EmpresaDTO;
import com.trabalho.api.dto.EstabelecimentoDTO;
import com.trabalho.api.dto.PedidoDTO;
import com.trabalho.api.dto.ResponseDTO;
import com.trabalho.api.model.Cliente;
import com.trabalho.api.model.Empresa;
import com.trabalho.api.model.Estabelecimento;
import com.trabalho.api.model.Pedido;
import com.trabalho.api.request.CadastroEmpresa;
import com.trabalho.api.request.CadastroEstabelecimento;
import com.trabalho.api.service.ClienteService;
import com.trabalho.api.service.EmpresaService;
import com.trabalho.api.service.EstabelecimentoService;
import com.trabalho.api.service.PedidoService;

import jakarta.validation.Valid;

@Controller
public class AdmSistemaController {
    private final EmpresaService empresaService;
    private final EstabelecimentoService estabelecimentoService;
    private final ClienteService clienteService;
    private final PedidoService pedidoService;

    public AdmSistemaController(EmpresaService empresaService, EstabelecimentoService estabelecimentoService, ClienteService clienteService, PedidoService pedidoService){
        this.empresaService = empresaService;
        this.estabelecimentoService = estabelecimentoService;
        this.clienteService = clienteService;
        this.pedidoService = pedidoService;
    }

    //cliente
    @GetMapping("/adm/cliente")
    @ResponseBody
    public ResponseEntity<ResponseDTO<Collection<ClienteDTO>>> findAllClientes(){
        Collection<Cliente> clientes = clienteService.findAll();
        ResponseDTO<Collection<ClienteDTO>> responseDTO = ResponseDTO.build(ClienteDTO.convert(clientes), true, null, null);
        return new ResponseEntity<ResponseDTO<Collection<ClienteDTO>>>(responseDTO, new HttpHeaders(), HttpStatus.OK);
    }

    @GetMapping("/adm/cliente/{id}")
    @ResponseBody
    public ResponseEntity<ResponseDTO<ClienteDTO>> findCliente(@PathVariable Long id) throws Exception{
        Cliente cliente = clienteService.findById(id);
        ResponseDTO<ClienteDTO> responseDTO = ResponseDTO.build(ClienteDTO.convert(cliente), true, null, null);
        return new ResponseEntity<ResponseDTO<ClienteDTO>>(responseDTO, new HttpHeaders(), HttpStatus.OK);
    }

    //empresa
    @GetMapping("/adm/empresa")
    @ResponseBody
    public ResponseEntity<ResponseDTO<Collection<EmpresaDTO>>> findAllEmpresas(){
        Collection<Empresa> empresas = empresaService.findAll();
        ResponseDTO<Collection<EmpresaDTO>> responseDTO = ResponseDTO.build(EmpresaDTO.convert(empresas), true, null, null);
        return new ResponseEntity<ResponseDTO<Collection<EmpresaDTO>>>(responseDTO, new HttpHeaders(), HttpStatus.OK);
    }

    @GetMapping("/adm/empresa/{id}")
    @ResponseBody
    public ResponseEntity<ResponseDTO<EmpresaDTO>> findEmpresa(@PathVariable Long id) throws Exception{
        Empresa empresa = empresaService.findById(id);
        ResponseDTO<EmpresaDTO> responseDTO = ResponseDTO.build(EmpresaDTO.convert(empresa), true, null, null);
        return new ResponseEntity<ResponseDTO<EmpresaDTO>>(responseDTO, new HttpHeaders(), HttpStatus.OK);
    }

    @PostMapping("/adm/empresa/create")
    @ResponseBody
    public ResponseEntity<ResponseDTO<EmpresaDTO>> cadastrarEmpresa(@RequestBody @Valid CadastroEmpresa dados) {
        Empresa empresa = empresaService.salvar(dados);
        ResponseDTO<EmpresaDTO> responseDTO = ResponseDTO.build(EmpresaDTO.convert(empresa), true, "empresa salva com sucesso", null);
        return new ResponseEntity<ResponseDTO<EmpresaDTO>>(responseDTO, new HttpHeaders(), HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/adm/empresa/desativar/{id}")
    @ResponseBody
    public ResponseEntity<?> desativarEmpresa(@PathVariable Long id) throws Exception{
        this.empresaService.handleAtivacao(id, false);
        ResponseDTO<?> responseDTO = ResponseDTO.build(null, true, "empresa desativado com sucesso", null);
        return new ResponseEntity<>(responseDTO, new HttpHeaders(), HttpStatus.OK);
    }

    @PutMapping(value = "/adm/empresa/ativar/{id}")
    @ResponseBody
    public ResponseEntity<?> ativarEmpresa(@PathVariable Long id) throws Exception{
        this.empresaService.handleAtivacao(id, true);
        ResponseDTO<?> responseDTO = ResponseDTO.build(null, true, "empresa ativado com sucesso", null);
        return new ResponseEntity<>(responseDTO, new HttpHeaders(), HttpStatus.OK);
    }


    //estabs
    @GetMapping("/adm/estabelecimento")
    @ResponseBody
    public ResponseEntity<ResponseDTO<Collection<EstabelecimentoDTO>>> findAllEstabs(){
        Collection<Estabelecimento> estabelecimentos = estabelecimentoService.findAll();
        ResponseDTO<Collection<EstabelecimentoDTO>> responseDTO = ResponseDTO.build(EstabelecimentoDTO.convert(estabelecimentos), true, null, null);
        return new ResponseEntity<ResponseDTO<Collection<EstabelecimentoDTO>>>(responseDTO, new HttpHeaders(), HttpStatus.OK);
    }

    @GetMapping("/adm/estabelecimento/{id}")
    @ResponseBody
    public ResponseEntity<ResponseDTO<EstabelecimentoDTO>> findEstab(@PathVariable Long id) throws Exception{
        Estabelecimento estabelecimentos = estabelecimentoService.findById(id);
        ResponseDTO<EstabelecimentoDTO> responseDTO = ResponseDTO.build(EstabelecimentoDTO.convert(estabelecimentos), true, null, null);
        return new ResponseEntity<ResponseDTO<EstabelecimentoDTO>>(responseDTO, new HttpHeaders(), HttpStatus.OK);
    }

    @DeleteMapping(value = "/adm/estabelecimento/desativar/{idEstabelecimento}")
    @ResponseBody
    public ResponseEntity<?> desativarEstab(@PathVariable Long idEstabelecimento) throws Exception{
        this.estabelecimentoService.handleAtivacao(idEstabelecimento, false, null);
        return new ResponseEntity<>(new ResponseDTO<>(null, true, "desativado com sucesso", null), new HttpHeaders(), HttpStatus.CREATED);
    }

    @PutMapping(value = "/adm/estabelecimento/ativar/{idEstabelecimento}")
    @ResponseBody
    public ResponseEntity<?> ativarEstab(@PathVariable(name = "idEstabelecimento") Long id) throws Exception{
        this.estabelecimentoService.handleAtivacao(id, true, null);
        return new ResponseEntity<>(new ResponseDTO<>(null, true, "ativado com sucesso", null), new HttpHeaders(), HttpStatus.CREATED);
    }

    @GetMapping("/adm-sistema-e-adm-empresa/estabelecimento/{idEstabelecimento}/pedidos")
    @ResponseBody
    public ResponseEntity<ResponseDTO<Collection<PedidoDTO>>> findByIdAndEstabelecimentoId(@PathVariable Long idEstabelecimento) throws Exception{
        Collection<Pedido> pedido = this.pedidoService.findAllByEstabelecimento(idEstabelecimento);
        ResponseDTO<Collection<PedidoDTO>> response = ResponseDTO.build(PedidoDTO.convert(pedido), null, null, null);
        return new ResponseEntity<ResponseDTO<Collection<PedidoDTO>>>(response, new HttpHeaders(), HttpStatus.OK);
    }

    @PostMapping(value = "/adm-sistema-e-adm-empresa/estabelecimento/create")
    @ResponseBody
    public ResponseEntity<ResponseDTO<EstabelecimentoDTO>> cadastrarEstab(@RequestBody @Valid CadastroEstabelecimento dados) throws Exception{
        Estabelecimento estabelecimento = estabelecimentoService.salvar(dados);
        ResponseDTO<EstabelecimentoDTO> responseDTO = ResponseDTO.build(EstabelecimentoDTO.convert(estabelecimento), true, "estabelecimento salvo com sucesso", null);
        return new ResponseEntity<ResponseDTO<EstabelecimentoDTO>>(responseDTO, new HttpHeaders(), HttpStatus.OK);
    }
}
