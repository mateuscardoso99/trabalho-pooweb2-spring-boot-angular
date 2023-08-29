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
import com.trabalho.api.model.Estabelecimento;
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
        Collection<Estabelecimento> estabelecimentos = estabelecimentoService.findAll();
        ResponseDTO<Collection<EstabelecimentoDTO>> responseDTO = ResponseDTO.build(EstabelecimentoDTO.convert(estabelecimentos), true, null, null);
        return new ResponseEntity<ResponseDTO<Collection<EstabelecimentoDTO>>>(responseDTO, new HttpHeaders(), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    @ResponseBody
    public ResponseEntity<ResponseDTO<EstabelecimentoDTO>> findById(@PathVariable Long id) throws Exception{
        Estabelecimento estabelecimento = estabelecimentoService.findById(id);
        ResponseDTO<EstabelecimentoDTO> responseDTO = ResponseDTO.build(EstabelecimentoDTO.convert(estabelecimento), true, null, null);
        return new ResponseEntity<ResponseDTO<EstabelecimentoDTO>>(responseDTO, new HttpHeaders(), HttpStatus.OK);
    }

    @PostMapping
    @ResponseBody
    public ResponseEntity<ResponseDTO<EstabelecimentoDTO>> cadastrar(@RequestBody @Valid CadastroEstabelecimento dados) throws Exception{
        Estabelecimento estabelecimento = estabelecimentoService.salvar(dados);
        ResponseDTO<EstabelecimentoDTO> responseDTO = ResponseDTO.build(EstabelecimentoDTO.convert(estabelecimento), true, "estabelecimento salvo com sucesso", null);
        return new ResponseEntity<ResponseDTO<EstabelecimentoDTO>>(responseDTO, new HttpHeaders(), HttpStatus.OK);
    }

    @PutMapping(value = "/{id}")
    @ResponseBody
    public ResponseEntity<ResponseDTO<EstabelecimentoDTO>> update(@RequestBody @Valid CadastroEstabelecimento dados, @PathVariable Long id) throws Exception{
        Estabelecimento estabelecimento = estabelecimentoService.atualizar(dados, id);
        ResponseDTO<EstabelecimentoDTO> responseDTO = ResponseDTO.build(EstabelecimentoDTO.convert(estabelecimento), true, "estabelecimento salvo com sucesso", null);
        return new ResponseEntity<ResponseDTO<EstabelecimentoDTO>>(responseDTO, new HttpHeaders(), HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseBody
    public ResponseEntity<?> apagar(@PathVariable Long id) throws Exception{
        this.estabelecimentoService.apagar(id);
        return new ResponseEntity<>(new ResponseDTO<>(null, true, "estabelecimento removido com sucesso", null), new HttpHeaders(), HttpStatus.CREATED);

    }
}
