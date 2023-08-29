package com.trabalho.api.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.trabalho.api.dto.AdminEmpresaDTO;
import com.trabalho.api.dto.EmpresaDTO;
import com.trabalho.api.dto.ResponseDTO;
import com.trabalho.api.model.AdminEmpresa;
import com.trabalho.api.model.Empresa;
import com.trabalho.api.request.CadastroEmpresa;
import com.trabalho.api.request.CadastroUsuario;
import com.trabalho.api.service.AdminEmpresaService;
import com.trabalho.api.service.EmpresaService;

import jakarta.validation.Valid;

import java.util.Collection;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping(value = "/empresa")
public class EmpresaController {
    private final EmpresaService empresaService;
    private final AdminEmpresaService adminEmpresaService;

    public EmpresaController(EmpresaService empresaService, AdminEmpresaService adminEmpresaService){
        this.empresaService = empresaService;
        this.adminEmpresaService = adminEmpresaService;
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

    @PutMapping(value = "/desativar/{id}")
    @ResponseBody
    public ResponseEntity<?> desativar(@PathVariable Long id) throws Exception{
        this.empresaService.handleAtivacao(id, false);
        ResponseDTO<?> responseDTO = ResponseDTO.build(null, true, "desativado com sucesso", null);
        return new ResponseEntity<>(responseDTO, new HttpHeaders(), HttpStatus.OK);
    }

    @PutMapping(value = "/ativar/{id}")
    @ResponseBody
    public ResponseEntity<?> ativar(@PathVariable Long id) throws Exception{
        this.empresaService.handleAtivacao(id, true);
        ResponseDTO<?> responseDTO = ResponseDTO.build(null, true, "ativado com sucesso", null);
        return new ResponseEntity<>(responseDTO, new HttpHeaders(), HttpStatus.OK);
    }

    //usuários
    @GetMapping(value = "/{idEmpresa}/usuarios")
    public ResponseEntity<ResponseDTO<Collection<AdminEmpresaDTO>>> findAllUsuariosEmpresa(@PathVariable Long idEmpresa){
        Collection<AdminEmpresa> usuarios = this.adminEmpresaService.findAllByEmpresa(idEmpresa);
        ResponseDTO<Collection<AdminEmpresaDTO>> responseDTO = ResponseDTO.build(AdminEmpresaDTO.convert(usuarios), true, null, null);
        return new ResponseEntity<ResponseDTO<Collection<AdminEmpresaDTO>>>(responseDTO, new HttpHeaders(), HttpStatus.OK);
    }

    @GetMapping(value = "/{idEmpresa}/buscar-usuario/{idUsuario}")
    public ResponseEntity<ResponseDTO<AdminEmpresaDTO>> findUsuarioEmpresa(@PathVariable Long idEmpresa, @PathVariable Long idUsuario) throws Exception{
        AdminEmpresa usuario = this.adminEmpresaService.findUsuarioEmpresa(idEmpresa,idUsuario);
        ResponseDTO<AdminEmpresaDTO> responseDTO = ResponseDTO.build(AdminEmpresaDTO.convert(usuario), true, null, null);
        return new ResponseEntity<ResponseDTO<AdminEmpresaDTO>>(responseDTO, new HttpHeaders(), HttpStatus.OK);
    }

    @PostMapping(value = "/{idEmpresa}/usuario")
    public ResponseEntity<ResponseDTO<AdminEmpresaDTO>> criarUsuarioEmpresa(@Valid @RequestBody CadastroUsuario dados, @PathVariable Long idEmpresa) throws Exception{
        AdminEmpresa usuario = this.adminEmpresaService.salvar(dados, idEmpresa);
        ResponseDTO<AdminEmpresaDTO> responseDTO = ResponseDTO.build(AdminEmpresaDTO.convert(usuario), true, "usuario cadastrado com sucesso", null);
        return new ResponseEntity<ResponseDTO<AdminEmpresaDTO>>(responseDTO, new HttpHeaders(), HttpStatus.OK);
    }

    @PutMapping(value = "/{idEmpresa}/update-usuario/{idUsuario}")
    public ResponseEntity<ResponseDTO<AdminEmpresaDTO>> updateUsuarioEmpresa(@Valid @RequestBody CadastroUsuario dados, @PathVariable Long idEmpresa, @PathVariable Long idUsuario) throws Exception{
        AdminEmpresa usuario = this.adminEmpresaService.update(dados,idUsuario,idEmpresa);
        return new ResponseEntity<ResponseDTO<AdminEmpresaDTO>>(new ResponseDTO<AdminEmpresaDTO>(AdminEmpresaDTO.convert(usuario), true, "usuario salvo com sucesso", null), new HttpHeaders(), HttpStatus.OK);
    }

    @PutMapping(value = "/{idEmpresa}/desativar-usuario/{idUsuario}")
    public ResponseEntity<ResponseDTO<?>> desativarUsuarioEmpresa(@PathVariable Long idEmpresa, @PathVariable Long idUsuario) throws Exception{
        this.adminEmpresaService.handleAtivacao(idUsuario,idEmpresa,false);
        return new ResponseEntity<>(new ResponseDTO<>(null, true, "usuario desativado com sucesso", null), new HttpHeaders(), HttpStatus.OK);
    }

    @PutMapping(value = "/{idEmpresa}/ativar-usuario/{idUsuario}")
    public ResponseEntity<ResponseDTO<AdminEmpresaDTO>> ativarUsuarioEmpresa(@PathVariable Long idEmpresa, @PathVariable Long idUsuario) throws Exception{
        this.adminEmpresaService.handleAtivacao(idUsuario,idEmpresa,true);
        return new ResponseEntity<>(new ResponseDTO<>(null, true, "usuario ativado com sucesso", null), new HttpHeaders(), HttpStatus.OK);
    }
}
