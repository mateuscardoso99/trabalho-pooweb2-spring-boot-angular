package com.trabalho.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.trabalho.api.model.Usuario;
import com.trabalho.api.request.LoginRequest;
import com.trabalho.api.security.JwtUtils;
import com.trabalho.api.security.UserDetailsImpl;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/login")
public class LoginController {
    private AuthenticationManager authenticationManager;
    private JwtUtils jwtUtils;

    public LoginController(AuthenticationManager authenticationManager, JwtUtils jwtUtils){
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
    }

    @PostMapping
    public ResponseEntity<?> login(@RequestBody @Valid LoginRequest dados) throws Exception{
        try{
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(dados.getEmail(), dados.getSenha()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            String token = jwtUtils.generateTokenFromUser(userDetails);
            //Usuario usuario = 
            return ResponseEntity.ok(token);
        }catch(DisabledException e){
            throw new Exception("Usuário desabilitado",e);
        }catch(BadCredentialsException e){
            throw new Exception("Email ou senha incorretos", e);
        }
    }
}
