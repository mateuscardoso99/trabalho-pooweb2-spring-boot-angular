package com.trabalho.api.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.trabalho.api.dto.AdminEmpresaDTO;
import com.trabalho.api.dto.AdminEstabelecimentoDTO;
import com.trabalho.api.dto.ClienteDTO;
import com.trabalho.api.dto.TokenDTO;
import com.trabalho.api.dto.UsuarioDTO;
import com.trabalho.api.model.Permissoes;
import com.trabalho.api.request.LoginRequest;
import com.trabalho.api.security.JwtUtils;
import com.trabalho.api.security.UserDetailsImpl;
import com.trabalho.api.service.AdminEmpresaService;
import com.trabalho.api.service.AdminEstabelecimentoService;
import com.trabalho.api.service.ClienteService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/login")
public class LoginController {
    private AuthenticationManager authenticationManager;
    private ClienteService clienteService;
    private AdminEmpresaService adminEmpresaService;
    private AdminEstabelecimentoService adminEstabelecimentoService;
    private JwtUtils jwtUtils;

    public LoginController(AuthenticationManager authenticationManager, JwtUtils jwtUtils, ClienteService clienteService, AdminEmpresaService adminEmpresaService, AdminEstabelecimentoService adminEstabelecimentoService){
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
        this.clienteService = clienteService;
        this.adminEmpresaService = adminEmpresaService;
        this.adminEstabelecimentoService = adminEstabelecimentoService;
    }

    @PostMapping
    public ResponseEntity<TokenDTO<? extends UsuarioDTO>> login(@RequestBody @Valid LoginRequest dados) throws Exception{
        try{
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(dados.getEmail(), dados.getSenha()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            String token = jwtUtils.generateTokenFromUser(userDetails);

            if(userDetails.getAuthorities().contains(new SimpleGrantedAuthority(Permissoes.CLIENTE.name()))){
                ClienteDTO clienteDTO = clienteService.findById(userDetails.getId());
                TokenDTO<ClienteDTO> tokenDTO = new TokenDTO<>();
                tokenDTO.setUsuario(clienteDTO);
                tokenDTO.setToken(token);
                return new ResponseEntity<>(tokenDTO,new HttpHeaders(),HttpStatus.OK);
            }
            else if(userDetails.getAuthorities().contains(new SimpleGrantedAuthority(Permissoes.ADMIN_ESTABELECIMENTO.name()))){
                AdminEstabelecimentoDTO adminEstabDTO = adminEstabelecimentoService.findById(userDetails.getId());
                TokenDTO<AdminEstabelecimentoDTO> tokenDTO = new TokenDTO<>();
                tokenDTO.setUsuario(adminEstabDTO);
                tokenDTO.setToken(token);
                return new ResponseEntity<>(tokenDTO,new HttpHeaders(),HttpStatus.OK);
            }
            else if(userDetails.getAuthorities().contains(new SimpleGrantedAuthority(Permissoes.ADMIN_EMPRESA.name()))){
                AdminEmpresaDTO adminEmpresaDTO = adminEmpresaService.findById(userDetails.getId());
                TokenDTO<AdminEmpresaDTO> tokenDTO = new TokenDTO<>();
                tokenDTO.setUsuario(adminEmpresaDTO);
                tokenDTO.setToken(token);
                return new ResponseEntity<>(tokenDTO,new HttpHeaders(),HttpStatus.OK);
            }
            else{
                throw new Exception("Usuário não possui permissões");
            }
        }catch(DisabledException e){
            throw new Exception("Usuário desabilitado",e);
        }catch(BadCredentialsException e){
            throw new Exception("Email ou senha incorretos", e);
        }
    }
}
