package com.trabalho.api.interceptor;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.HandlerMapping;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.trabalho.api.security.JwtUtils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * diferença filtro e interceptor
 * Os filtros interceptam solicitações antes que elas cheguem ao DispatcherServlet, não atuam no contexto do spring
 * O HandlerIntercepors, por outro lado, intercepta solicitações entre o DispatcherServlet e os controllers Isso é feito dentro da estrutura Spring MVC, fornecendo acesso aos objetos Handler e ModelAndView
 */

@Component
public class EstabelecimentoInterceptor implements HandlerInterceptor{
    @Autowired
    private JwtUtils jwtUtils; //só é possível fazer autowired em componentes spring

    //verifica se o idEstabelecimento passado na request é o mesmo que está no token
    //impede de um usuário de um estab veja dados de outro estab
    //é aplicado pra todas as requisições no EstabelecimentoController
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)throws Exception {
        Map<String,String> pathVariables = new ObjectMapper().convertValue(request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE), new TypeReference<Map<String, String>>(){});
        //Map<String, String> pathVariables = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        if(pathVariables.containsKey("idEstabelecimento")){
            String token = jwtUtils.getTokenFromRequest(request);
            String idEstabelecimentoToken = jwtUtils.getClaimsFromJwtToken(token).get("estabelecimento_id").toString();
            if(!idEstabelecimentoToken.equals(pathVariables.get("idEstabelecimento"))){
                throw new AccessDeniedException("parametro diferente do que está no jwt");
            }
        }

        return HandlerInterceptor.super.preHandle(request, response, handler);
    }
    
}
