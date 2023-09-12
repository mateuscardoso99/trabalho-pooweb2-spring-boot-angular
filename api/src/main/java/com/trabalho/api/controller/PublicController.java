package com.trabalho.api.controller;

import java.util.Collection;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.trabalho.api.dto.EstabelecimentoDTO;
import com.trabalho.api.dto.ResponseDTO;
import com.trabalho.api.model.Estabelecimento;
import com.trabalho.api.service.EstabelecimentoService;

@RestController
@RequestMapping(value = "/public")
public class PublicController {
    private final EstabelecimentoService estabelecimentoService;

    public PublicController(EstabelecimentoService estabelecimentoService){
        this.estabelecimentoService = estabelecimentoService;
    }

    @GetMapping("/estabelecimentos")
    public ResponseEntity<ResponseDTO<Collection<EstabelecimentoDTO>>> show(){
        Collection<Estabelecimento> estabelecimentos = estabelecimentoService.findAll();
        ResponseDTO<Collection<EstabelecimentoDTO>> responseDTO = ResponseDTO.build(EstabelecimentoDTO.convert(estabelecimentos), true, null, null);
        return new ResponseEntity<ResponseDTO<Collection<EstabelecimentoDTO>>>(responseDTO, new HttpHeaders(), HttpStatus.OK);
    }
}
