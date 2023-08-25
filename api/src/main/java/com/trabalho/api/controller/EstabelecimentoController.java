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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.trabalho.api.dto.EstabelecimentoDTO;
import com.trabalho.api.dto.ResponseDTO;
import com.trabalho.api.request.CadastroEstabelecimento;
import com.trabalho.api.service.EstabelecimentoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/estabelecimento")
public class EstabelecimentoController {
    private final EstabelecimentoService estabelecimentoService;

    public EstabelecimentoController(EstabelecimentoService estabelecimentoService){
        this.estabelecimentoService = estabelecimentoService;
    }

    @GetMapping
    @ResponseBody
    public ResponseEntity<ResponseDTO<Collection<EstabelecimentoDTO>>> findAll(){
        Collection<EstabelecimentoDTO> estabelecimentos = estabelecimentoService.findAll();
        ResponseDTO<Collection<EstabelecimentoDTO>> responseDTO = ResponseDTO.build(estabelecimentos, true, null, null);
        return new ResponseEntity<ResponseDTO<Collection<EstabelecimentoDTO>>>(responseDTO, new HttpHeaders(), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    @ResponseBody
    public ResponseEntity<ResponseDTO<EstabelecimentoDTO>> findById(@PathVariable Long id) throws Exception{
        EstabelecimentoDTO estabelecimento = estabelecimentoService.findById(id);
        ResponseDTO<EstabelecimentoDTO> responseDTO = ResponseDTO.build(estabelecimento, true, null, null);
        return new ResponseEntity<ResponseDTO<EstabelecimentoDTO>>(responseDTO, new HttpHeaders(), HttpStatus.OK);
    }

    @PostMapping
    @ResponseBody
    public ResponseEntity<ResponseDTO<EstabelecimentoDTO>> cadastrar(@RequestBody @Valid CadastroEstabelecimento dados) throws Exception{
        EstabelecimentoDTO estabelecimento = estabelecimentoService.salvar(dados);
        ResponseDTO<EstabelecimentoDTO> responseDTO = ResponseDTO.build(estabelecimento, true, "estabelecimento salvo com sucesso", null);
        return new ResponseEntity<ResponseDTO<EstabelecimentoDTO>>(responseDTO, new HttpHeaders(), HttpStatus.OK);
    }

    @PutMapping(value = "/{id}")
    @ResponseBody
    public ResponseEntity<ResponseDTO<EstabelecimentoDTO>> update(@RequestBody @Valid CadastroEstabelecimento dados, @PathVariable Long id) throws Exception{
        EstabelecimentoDTO estabelecimento = estabelecimentoService.atualizar(dados, id);
        ResponseDTO<EstabelecimentoDTO> responseDTO = ResponseDTO.build(estabelecimento, true, "estabelecimento salvo com sucesso", null);
        return new ResponseEntity<ResponseDTO<EstabelecimentoDTO>>(responseDTO, new HttpHeaders(), HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseBody
    public ResponseEntity<?> apagar(@PathVariable Long id) throws Exception{
        this.estabelecimentoService.apagar(id);
        ResponseDTO<?> responseDTO = ResponseDTO.build(null, true, "apagado com sucesso", null);
        return new ResponseEntity<>(responseDTO, new HttpHeaders(), HttpStatus.OK);
    }
}
