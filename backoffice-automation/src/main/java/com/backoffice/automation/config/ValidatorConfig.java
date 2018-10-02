package com.backoffice.automation.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.validation.Validation;
import javax.validation.Validator;

/**
 * Created by NEX9ZKA on 9/16/2018.
 */
@Configuration
public class ValidatorConfig {
    @Bean
    public Validator validator() {
        return Validation.buildDefaultValidatorFactory().getValidator();
    }

   /* @Bean
    public Validator marshItfValidator() {
        return Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Bean
    public Validator shipCoverValidator() {
        return Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Bean
    public Validator shippingApiValidator() {
        return Validation.buildDefaultValidatorFactory().getValidator();
    }*/
} 