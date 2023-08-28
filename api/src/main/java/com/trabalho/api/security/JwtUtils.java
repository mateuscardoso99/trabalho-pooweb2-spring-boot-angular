package com.trabalho.api.security;

import java.util.Date;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;

/**
 * partes JWT
 * Header: objeto JSON que define informações sobre o tipo do token (typ), nesse caso JWT, e o algorítmo de criptografia usado em sua assinatura (alg), normalmente HMAC SHA256 ou RSA
 * Payload: objeto JSON com as Claims (informações) da entidade tratada, normalmente o usuário autenticado

 * Claims: dados do usuário ou qualquer coisa que seja importante para o cliente
 * A especificação JWT define sete declarações reservadas que não são obrigatórias, mas são recomendadas para permitir a 
 * interoperabilidade com aplicativos de terceiros Estes são:
 * iss(emissor): Emissor do JWT
 * sub(assunto): Assunto do JWT (o usuário)
 * aud( público ): Destinatário ao qual o JWT se destina
 * exp(tempo de expiração): Tempo após o qual o JWT expira
 * nbf(não antes do tempo): Tempo antes do qual o JWT não deve ser aceito para processamento
 * iat(emitido na hora): Hora em que o JWT foi emitido; pode ser usado para determinar a idade do JWT
 * jti(ID JWT): identificador único; pode ser usado para evitar que o JWT seja reproduzido (permite que um token seja usado apenas uma vez)
 *
 * Você pode definir suas próprias declarações personalizadas que você controla e adicioná-las a um token. aqui estão alguns exemplos:
 * Adicione o endereço de e-mail de um usuário a um token de acesso e use-o para identificar o usuário exclusivamente.



 * Signature: assinatura é a concatenação dos hashes gerados a partir do Header e Payload usando base64UrlEncode, com uma chave secreta ou certificado RSA.
    Essa assinatura é utilizada para garantir a integridade do token, no caso, se ele foi modificado e se realmente foi gerado por você.
    Isso previne ataques do tipo man-in-the-middle, onde o invasor poderia interceptar a requisição e modificar seu conteúdo, 
    desta forma personificando o usuário com informações falsas. Caso o payload seja alterado, o hash final não será válido pois não 
    foi assinado com sua chave secreta.
    Apenas quem está de posse da chave pode criar, alterar e validar o token.
    Em posse do token, a API não precisa ir até o banco de dados consultar as informações do usuário, 
    pois contido no próprio token JWT já temos suas credenciais de acesso
 */

@Component
public class JwtUtils {
    private static final String SECRET = "PD46W0FdSDNaQ3tAeENQPUhyTWNQMG94USNwWTtoU1hIMFd43JkWktyWWcpdEpSOChkRTVZdzNNNwLVVoYqNZbj0uaHVAen1QKjByS0k7";
    private static final Long EXPIRE_DURATION = 24 * 60 * 60 * 1000l; // 24 hour
    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);
    
    public String generateTokenFromUser(UserDetailsImpl userDetails) {   
        String[] permissoes = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList().stream().toArray(String[]::new);

        String token = Jwts.builder()
            .setSubject(userDetails.getEmail())
            .setIssuer(userDetails.getEmail())
            .setExpiration(new Date(System.currentTimeMillis() + EXPIRE_DURATION))
            .setIssuedAt(new Date())
            .addClaims(Map.of("permissoes", permissoes,"nome",userDetails.getUsername(),"user_id",userDetails.getId()))//alem dos claims iss, sub, exp... pode definir claims personalizados
            .signWith(Keys.hmacShaKeyFor(Decoders.BASE64.decode(SECRET)),SignatureAlgorithm.HS512)
            .compact();
        return token;
    }

    public String getUserNameFromJwtToken(String token) {
        return Jwts.parserBuilder().setSigningKey(Keys.hmacShaKeyFor(Decoders.BASE64.decode(SECRET))).build().parseClaimsJws(token).getBody().getSubject();
    }

    //valida o token, fazendo um parse
    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parserBuilder().setSigningKey(Keys.hmacShaKeyFor(Decoders.BASE64.decode(SECRET))).build().parse(authToken);
            return true;
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty: {}", e.getMessage());
        }

        return false;
    }

    public String getTokenFromRequest(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        String token = header.split(" ")[1].trim();
        return token;
    }
}
