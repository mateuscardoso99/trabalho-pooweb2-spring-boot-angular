package com.trabalho.api.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.trabalho.api.dto.UsuarioAdminEmpresaDTO;
import com.trabalho.api.dto.UsuarioAdminEstabelecimentoDTO;
import com.trabalho.api.dto.ClienteDTO;
import com.trabalho.api.dto.TokenDTO;
import com.trabalho.api.dto.UsuarioDTO;
import com.trabalho.api.model.UsuarioAdminEmpresa;
import com.trabalho.api.model.UsuarioAdminEstabelecimento;
import com.trabalho.api.model.Cliente;
import com.trabalho.api.model.Permissoes;
import com.trabalho.api.request.LoginRequest;
import com.trabalho.api.security.JwtUtils;
import com.trabalho.api.security.UserDetailsImpl;
import com.trabalho.api.service.UsuarioAdminEmpresaService;
import com.trabalho.api.service.UsuarioAdminEstabelecimentoService;
import com.trabalho.api.service.ClienteService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/login")
public class LoginController {
    private AuthenticationManager authenticationManager;
    private ClienteService clienteService;
    private UsuarioAdminEmpresaService adminEmpresaService;
    private UsuarioAdminEstabelecimentoService adminEstabelecimentoService;
    private JwtUtils jwtUtils;

    public LoginController(AuthenticationManager authenticationManager, JwtUtils jwtUtils, ClienteService clienteService, UsuarioAdminEmpresaService adminEmpresaService, UsuarioAdminEstabelecimentoService adminEstabelecimentoService){
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
        this.clienteService = clienteService;
        this.adminEmpresaService = adminEmpresaService;
        this.adminEstabelecimentoService = adminEstabelecimentoService;
    }

    @PostMapping
    public ResponseEntity<TokenDTO<UsuarioDTO>> login(@RequestBody @Valid LoginRequest dados) throws Exception{
        try{
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(dados.getEmail(), dados.getSenha()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

            TokenDTO<UsuarioDTO> tokenDTO = new TokenDTO<>();
            Map<String,Object> claimsJwt = new HashMap<>();
            claimsJwt.put("user_id", userDetails.getId());

            claimsJwt.put("permissoes", userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList().stream().toArray(String[]::new));
            claimsJwt.put("username", userDetails.getUsername());

            if(userDetails.getAuthorities().contains(new SimpleGrantedAuthority(Permissoes.CLIENTE.name()))){
                Cliente cliente = clienteService.findById(userDetails.getId());
                tokenDTO.setUsuario(ClienteDTO.convert(cliente));
            }
            else if(userDetails.getAuthorities().contains(new SimpleGrantedAuthority(Permissoes.ADMIN_ESTABELECIMENTO.name()))){
                UsuarioAdminEstabelecimento adminEstab = adminEstabelecimentoService.findById(userDetails.getId());
                claimsJwt.put("estabelecimento_id", adminEstab.getEstabelecimento().getId());
                tokenDTO.setUsuario(UsuarioAdminEstabelecimentoDTO.convert(adminEstab));
            }
            else if(userDetails.getAuthorities().contains(new SimpleGrantedAuthority(Permissoes.ADMIN_EMPRESA.name()))){
                UsuarioAdminEmpresa adminEmpresa = adminEmpresaService.findById(userDetails.getId());
                claimsJwt.put("empresa_id", adminEmpresa.getEmpresa().getId());
                tokenDTO.setUsuario(UsuarioAdminEmpresaDTO.convert(adminEmpresa));
            }
            else{
                throw new Exception("Usuário não possui permissões");
            }

            String token = jwtUtils.generateTokenFromUser(userDetails,claimsJwt);
            tokenDTO.setToken(token);

            return new ResponseEntity<>(tokenDTO,new HttpHeaders(),HttpStatus.OK);
        }catch(DisabledException e){
            throw new Exception("Usuário desabilitado",e);
        }catch(BadCredentialsException e){
            throw new Exception("Email ou senha incorretos", e);
        }
    }
}
