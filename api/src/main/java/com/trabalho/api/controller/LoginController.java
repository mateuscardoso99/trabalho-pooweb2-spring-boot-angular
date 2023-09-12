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
import com.trabalho.api.exception.BadRequestException;
import com.trabalho.api.model.UsuarioAdminEmpresa;
import com.trabalho.api.model.UsuarioAdminEstabelecimento;
import com.trabalho.api.model.Cliente;
import com.trabalho.api.model.Permissoes;
import com.trabalho.api.request.LoginRequest;
import com.trabalho.api.security.JwtUtils;
import com.trabalho.api.security.UserDetailsImpl;
import com.trabalho.api.service.ClienteService;
import com.trabalho.api.service.EmpresaService;
import com.trabalho.api.service.EstabelecimentoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/login")
public class LoginController {
    private AuthenticationManager authenticationManager;
    private ClienteService clienteService;
    private EmpresaService empresaService;
    private EstabelecimentoService estabelecimentoService;
    private JwtUtils jwtUtils;

    public LoginController(AuthenticationManager authenticationManager, JwtUtils jwtUtils, ClienteService clienteService, EmpresaService empresaService, EstabelecimentoService estabelecimentoService){
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
        this.clienteService = clienteService;
        this.empresaService = empresaService;
        this.estabelecimentoService = estabelecimentoService;
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
                UsuarioAdminEstabelecimento adminEstab = estabelecimentoService.findUsuarioEstabelecimentoByIdUsuario(userDetails.getId());
                claimsJwt.put("estabelecimento_id", adminEstab.getEstabelecimento().getId());
                tokenDTO.setUsuario(UsuarioAdminEstabelecimentoDTO.convert(adminEstab));
            }
            else if(userDetails.getAuthorities().contains(new SimpleGrantedAuthority(Permissoes.ADMIN_EMPRESA.name()))){
                UsuarioAdminEmpresa adminEmpresa = empresaService.findUsuarioEmpresaByIdUsuario(userDetails.getId());
                claimsJwt.put("empresa_id", adminEmpresa.getEmpresa().getId());
                tokenDTO.setUsuario(UsuarioAdminEmpresaDTO.convert(adminEmpresa));
            }
            else if(userDetails.getAuthorities().contains(new SimpleGrantedAuthority(Permissoes.ADMIN_SISTEMA.name()))){
                UsuarioDTO usuarioDTO = new UsuarioDTO();
                usuarioDTO.setId(userDetails.getId());
                usuarioDTO.setEmail(userDetails.getEmail());
                usuarioDTO.setNome(userDetails.getUsername());
                usuarioDTO.setPermissoes(userDetails.getAuthorities().stream().map(p -> Permissoes.valueOf(p.getAuthority())).toList());
                tokenDTO.setUsuario(usuarioDTO);
            }
            else{
                throw new BadRequestException("Usuário não possui permissões");
            }

            String token = jwtUtils.generateTokenFromUser(userDetails,claimsJwt);
            tokenDTO.setToken(token);

            return new ResponseEntity<>(tokenDTO,new HttpHeaders(),HttpStatus.OK);
        }catch(DisabledException e){
            throw new BadRequestException("Usuário desabilitado");
        }catch(BadCredentialsException e){
            throw new BadRequestException("Email ou senha incorretos");
        }
    }
}
