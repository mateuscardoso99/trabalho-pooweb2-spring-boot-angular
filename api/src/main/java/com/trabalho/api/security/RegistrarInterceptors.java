package com.trabalho.api.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.trabalho.api.interceptor.EstabelecimentoInterceptor;

@Configuration
public class RegistrarInterceptors implements WebMvcConfigurer{
    @Autowired
    private EstabelecimentoInterceptor estabelecimentoInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(estabelecimentoInterceptor).addPathPatterns("/estabelecimento/**");
    }
}
