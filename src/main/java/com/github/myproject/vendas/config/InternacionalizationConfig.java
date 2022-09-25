package com.github.myproject.vendas.config;

import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@Configuration
public class InternacionalizationConfig {
	
	@Bean
	public MessageSource messageSource() {
		ReloadableResourceBundleMessageSource message = new ReloadableResourceBundleMessageSource();
		message.setBasename("classpath:messages");
		message.setDefaultEncoding("ISO-8859-1");
		message.setDefaultLocale(Locale.getDefault());
		
		return message;
	}
	
	@Bean
	public LocalValidatorFactoryBean validator() {
		
		LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
		bean.setValidationMessageSource(messageSource());
		
		return bean;
	}

}
