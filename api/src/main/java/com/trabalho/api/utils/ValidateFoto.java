package com.trabalho.api.utils;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

//@Target só pode ser usada para anotar outras anotações, nesse caso está anotando a anotação @ValidateFoto
//ElementType como TYPE, a anotação personalizada só pode anotar uma classe, enumeração ou interface
//ElementType.METHOD, ElementType.PACKAGE permite que @ValidateFoto seja usada para anotar um método ou pacote
//ElementType.FIELD permite anotar um atributo, nesse caso @ValideFoto vai anotar o atributo foto da class CadastroEstabelecimento
//@Retention também é uma meta-anotação
//RetentionPolicy.RUNTIME: As anotações anotadas usando a política de retenção RUNTIME são retidas durante o tempo de execução e podem ser acessadas em nosso programa durante o tempo de execução.
//RetentionPolicy.SOURCE: as anotações anotadas usando a política de retenção SOURCE são descartadas em tempo de execução.
//RetentionPolicy.CLASS: As anotações anotadas usando a política de retenção CLASS são registradas no arquivo .class, mas são descartadas durante o tempo de execução. CLASS é a política de retenção padrão em Java.
@Target({ElementType.PARAMETER,ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {ImageFileValidator.class})
public @interface ValidateFoto {
    String message() default "foto inválida";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
