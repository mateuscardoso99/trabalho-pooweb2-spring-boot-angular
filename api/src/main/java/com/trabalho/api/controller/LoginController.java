package com.trabalho.api.controller;

import java.util.ArrayList;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
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

import com.trabalho.api.dto.AdminEmpresaDTO;
import com.trabalho.api.dto.AdminEstabDTO;
import com.trabalho.api.dto.ClienteDTO;
import com.trabalho.api.dto.EnderecoDTO;
import com.trabalho.api.dto.TokenDTO;
import com.trabalho.api.dto.UsuarioDTO;
import com.trabalho.api.model.AdminEmpresa;
import com.trabalho.api.model.AdminEstab;
import com.trabalho.api.model.Permissoes;
import com.trabalho.api.model.Usuario;
import com.trabalho.api.repository.UsuarioRepository;
import com.trabalho.api.request.LoginRequest;
import com.trabalho.api.security.JwtUtils;
import com.trabalho.api.security.UserDetailsImpl;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/login")
public class LoginController {
    private AuthenticationManager authenticationManager;
    private UsuarioRepository usuarioRepository;
    private JwtUtils jwtUtils;

    public LoginController(AuthenticationManager authenticationManager, JwtUtils jwtUtils, UsuarioRepository usuarioRepository){
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
        this.usuarioRepository = usuarioRepository;
    }

    @PostMapping
    public ResponseEntity<TokenDTO<?>> login(@RequestBody @Valid LoginRequest dados) throws Exception{
        try{
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(dados.getEmail(), dados.getSenha()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            String token = jwtUtils.generateTokenFromUser(userDetails);
            Usuario usuario = usuarioRepository.findByEmail(userDetails.getEmail()).get();

            UsuarioDTO usuarioDTO = new UsuarioDTO();
            usuarioDTO.setId(usuario.getId());
            usuarioDTO.setNome(usuario.getNome());
            usuarioDTO.setEmail(usuario.getEmail());
            usuarioDTO.setPermissoes(usuario.getPermissoes());
            usuarioDTO.setEndereco(EnderecoDTO.convert(usuario.getEndereco()));

            if(usuario.getPermissoes().contains(Permissoes.CLIENTE)){
                ClienteDTO clienteDTO = new ClienteDTO(usuarioDTO, new ArrayList<>());
                TokenDTO<ClienteDTO> tokenDTO = new TokenDTO<>();
                tokenDTO.setUsuario(clienteDTO);
                tokenDTO.setToken(token);
                return new ResponseEntity<>(tokenDTO,new HttpHeaders(),HttpStatus.OK);
            }
            else if(usuario.getPermissoes().contains(Permissoes.ADMIN_ESTABELECIMENTO)){
                AdminEstabDTO adminEstabDTO = new AdminEstabDTO(usuarioDTO, null);
                TokenDTO<AdminEstabDTO> tokenDTO = new TokenDTO<>();
                tokenDTO.setUsuario(adminEstabDTO);
                tokenDTO.setToken(token);
                return new ResponseEntity<>(tokenDTO,new HttpHeaders(),HttpStatus.OK);
            }
            else if(usuario.getPermissoes().contains(Permissoes.ADMIN_EMPRESA)){
                AdminEmpresaDTO adminEmpresaDTO = new AdminEmpresaDTO(usuarioDTO, null);
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
