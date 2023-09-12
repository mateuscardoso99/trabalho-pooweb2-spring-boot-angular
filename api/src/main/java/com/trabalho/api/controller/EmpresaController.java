package com.trabalho.api.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.trabalho.api.dto.UsuarioAdminEmpresaDTO;
import com.trabalho.api.dto.EmpresaDTO;
import com.trabalho.api.dto.EstabelecimentoDTO;
import com.trabalho.api.dto.ResponseDTO;
import com.trabalho.api.model.UsuarioAdminEmpresa;
import com.trabalho.api.model.Empresa;
import com.trabalho.api.model.Estabelecimento;
import com.trabalho.api.request.CadastroEmpresa;
import com.trabalho.api.request.CadastroUsuarioAdmin;
import com.trabalho.api.service.EmpresaService;
import com.trabalho.api.service.EstabelecimentoService;

import jakarta.servlet.http.HttpServletRequest;
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
    private final EstabelecimentoService estabelecimentoService;

    public EmpresaController(EmpresaService empresaService, EstabelecimentoService estabelecimentoService){
        this.empresaService = empresaService;
        this.estabelecimentoService = estabelecimentoService;
    }

    @GetMapping
    @ResponseBody
    public ResponseEntity<ResponseDTO<EmpresaDTO>> findById(HttpServletRequest request) throws Exception{
        Empresa empresa = empresaService.findById(request);
        ResponseDTO<EmpresaDTO> responseDTO = ResponseDTO.build(EmpresaDTO.convert(empresa), true, null, null);
        return new ResponseEntity<ResponseDTO<EmpresaDTO>>(responseDTO, new HttpHeaders(), HttpStatus.OK);
    }

    @PutMapping("atualizar")
    @ResponseBody
    public ResponseEntity<ResponseDTO<EmpresaDTO>> update(@RequestBody @Valid CadastroEmpresa dados, HttpServletRequest request) throws Exception{
        Empresa empresa = empresaService.atualizar(dados, request);
        ResponseDTO<EmpresaDTO> responseDTO = ResponseDTO.build(EmpresaDTO.convert(empresa), true, "empresa salva com sucesso", null);
        return new ResponseEntity<ResponseDTO<EmpresaDTO>>(responseDTO, new HttpHeaders(), HttpStatus.OK);
    }

    //usuários
    @GetMapping(value = "/usuarios")
    public ResponseEntity<ResponseDTO<Collection<UsuarioAdminEmpresaDTO>>> findAllUsuariosEmpresa(HttpServletRequest request){
        Collection<UsuarioAdminEmpresa> usuarios = this.empresaService.findAllUsuariosByEmpresa(request);
        ResponseDTO<Collection<UsuarioAdminEmpresaDTO>> responseDTO = ResponseDTO.build(UsuarioAdminEmpresaDTO.convert(usuarios), true, null, null);
        return new ResponseEntity<ResponseDTO<Collection<UsuarioAdminEmpresaDTO>>>(responseDTO, new HttpHeaders(), HttpStatus.OK);
    }

    @GetMapping(value = "/buscar-usuario/{idUsuario}")
    public ResponseEntity<ResponseDTO<UsuarioAdminEmpresaDTO>> findUsuarioEmpresa(HttpServletRequest request, @PathVariable Long idUsuario) throws Exception{
        UsuarioAdminEmpresa usuario = this.empresaService.findUsuarioEmpresaByIdUsuario(request,idUsuario);
        ResponseDTO<UsuarioAdminEmpresaDTO> responseDTO = ResponseDTO.build(UsuarioAdminEmpresaDTO.convert(usuario), true, null, null);
        return new ResponseEntity<ResponseDTO<UsuarioAdminEmpresaDTO>>(responseDTO, new HttpHeaders(), HttpStatus.OK);
    }

    @PostMapping(value = "/usuario")
    public ResponseEntity<ResponseDTO<UsuarioAdminEmpresaDTO>> criarUsuarioEmpresa(HttpServletRequest request, @Valid @RequestBody CadastroUsuarioAdmin dados) throws Exception{
        UsuarioAdminEmpresa usuario = this.empresaService.salvarUsuarioEmpresa(request,dados);
        ResponseDTO<UsuarioAdminEmpresaDTO> responseDTO = ResponseDTO.build(UsuarioAdminEmpresaDTO.convert(usuario), true, "usuario cadastrado com sucesso", null);
        return new ResponseEntity<ResponseDTO<UsuarioAdminEmpresaDTO>>(responseDTO, new HttpHeaders(), HttpStatus.OK);
    }

    @PutMapping(value = "/update-usuario/{idUsuario}")
    public ResponseEntity<ResponseDTO<UsuarioAdminEmpresaDTO>> updateUsuarioEmpresa(HttpServletRequest request, @Valid @RequestBody CadastroUsuarioAdmin dados, @PathVariable Long idUsuario) throws Exception{
        UsuarioAdminEmpresa usuario = this.empresaService.updateUsuarioEmpresa(request,dados,idUsuario);
        return new ResponseEntity<ResponseDTO<UsuarioAdminEmpresaDTO>>(new ResponseDTO<UsuarioAdminEmpresaDTO>(UsuarioAdminEmpresaDTO.convert(usuario), true, "usuario salvo com sucesso", null), new HttpHeaders(), HttpStatus.OK);
    }

    @DeleteMapping(value = "/desativar-usuario/{idUsuario}")
    public ResponseEntity<ResponseDTO<?>> desativarUsuarioEmpresa(HttpServletRequest request, @PathVariable Long idUsuario) throws Exception{
        this.empresaService.handleAtivacaoUsuarioEmpresa(request,idUsuario,false);
        return new ResponseEntity<>(new ResponseDTO<>(null, true, "usuario desativado com sucesso", null), new HttpHeaders(), HttpStatus.OK);
    }

    @PutMapping(value = "/ativar-usuario/{idUsuario}")
    public ResponseEntity<ResponseDTO<UsuarioAdminEmpresaDTO>> ativarUsuarioEmpresa(HttpServletRequest request, @PathVariable Long idUsuario) throws Exception{
        this.empresaService.handleAtivacaoUsuarioEmpresa(request, idUsuario,true);
        return new ResponseEntity<>(new ResponseDTO<>(null, true, "usuario ativado com sucesso", null), new HttpHeaders(), HttpStatus.OK);
    }

    //estabelecimentos
    @GetMapping(value = "/estabelecimentos")
    public ResponseEntity<ResponseDTO<Collection<EstabelecimentoDTO>>> findAllEstabelecimentosByEmpresa(HttpServletRequest request){
        Collection<Estabelecimento> estabelecimentos = estabelecimentoService.findAllByEmpresa(request);
        ResponseDTO<Collection<EstabelecimentoDTO>> responseDTO = ResponseDTO.build(EstabelecimentoDTO.convert(estabelecimentos), true, null, null);
        return new ResponseEntity<ResponseDTO<Collection<EstabelecimentoDTO>>>(responseDTO, new HttpHeaders(), HttpStatus.OK);
    }

    @GetMapping(value = "/estabelecimentos/{idEstab}")
    public ResponseEntity<ResponseDTO<EstabelecimentoDTO>> findEstabelecimentosByIdAndEmpresaId(HttpServletRequest request, @PathVariable Long idEstab) throws Exception{
        Estabelecimento estabelecimento = estabelecimentoService.findByIdAndEmpresaId(request, idEstab);
        ResponseDTO<EstabelecimentoDTO> responseDTO = ResponseDTO.build(EstabelecimentoDTO.convert(estabelecimento), true, null, null);
        return new ResponseEntity<ResponseDTO<EstabelecimentoDTO>>(responseDTO, new HttpHeaders(), HttpStatus.OK);
    }

    @DeleteMapping(value = "/estabelecimento/desativar/{idEstabelecimento}")
    @ResponseBody
    public ResponseEntity<?> desativarEstab(@PathVariable Long idEstabelecimento, HttpServletRequest request) throws Exception{
        this.estabelecimentoService.handleAtivacao(idEstabelecimento, false, request);
        return new ResponseEntity<>(new ResponseDTO<>(null, true, "desativado com sucesso", null), new HttpHeaders(), HttpStatus.CREATED);
    }

    @PutMapping(value = "/estabelecimento/ativar/{idEstabelecimento}")
    @ResponseBody
    public ResponseEntity<?> ativarEstab(@PathVariable(name = "idEstabelecimento") Long id, HttpServletRequest request) throws Exception{
        this.estabelecimentoService.handleAtivacao(id, true, request);
        return new ResponseEntity<>(new ResponseDTO<>(null, true, "ativado com sucesso", null), new HttpHeaders(), HttpStatus.CREATED);
    }
}
