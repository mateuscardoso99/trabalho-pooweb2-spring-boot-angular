package com.trabalho.api.security;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import com.trabalho.api.exception.DataNotFoundException;
import com.trabalho.api.model.Usuario;
import com.trabalho.api.repository.UsuarioRepository;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class AuthTokenFilter extends OncePerRequestFilter{
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private JwtUtils jwtUtils;
    private static final Logger logger = LoggerFactory.getLogger(AuthTokenFilter.class);

    //cada request passa pelo filtro e verifica se o token está na request, se tiver valida com os métodos da biblioteca e busca o email do usuario de dentro
    //do token pra validar, depois busca no bd o usuario e faz uma tentativa de login, caso a tentativa der uma exceção entao houve algum erro
    //e o spring lança uma exceção
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String jwt = jwtUtils.getTokenFromRequest(request);
            if (jwt != null && jwtUtils.validateJwtToken(jwt)) {
                String user_id = jwtUtils.getClaimsFromJwtToken(jwt).getSubject();

                Usuario usuario = usuarioRepository.findById(Long.parseLong(user_id)).orElseThrow(()->new DataNotFoundException("usuario não encontrado no token"));
                UserDetails userDetails = UserDetailsImpl.build(usuario);
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails,userDetails.getPassword(),userDetails.getAuthorities());
                
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception e) {
            logger.error("Cannot set user authentication: {}", e);
        }

        filterChain.doFilter(request, response);
    }    
}
