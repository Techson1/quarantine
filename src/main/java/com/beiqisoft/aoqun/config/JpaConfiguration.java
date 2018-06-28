package com.beiqisoft.aoqun.config;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.convert.converter.Converter;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
@Order(Ordered.HIGHEST_PRECEDENCE)
@Configuration
@EnableTransactionManagement(proxyTargetClass = true)
@EnableJpaRepositories(basePackages = {"com.**.repository","com.**.repository.view"})
@EntityScan(basePackages = {"com.**.entity","com.**.entity.view"})
public class JpaConfiguration {
	 @Bean
	    PersistenceExceptionTranslationPostProcessor persistenceExceptionTranslationPostProcessor(){
	        return new PersistenceExceptionTranslationPostProcessor();
	    }
	 @Bean
	    public Converter<String, Date> addNewConvert() {
	        return new Converter<String, Date>() {
	            @Override
	            public Date convert(String source) {
	                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	                Date date = null;
	                try {
	                    date = sdf.parse((String) source);
	                } catch (ParseException e) {
	                }
	                return date;
	            }
	        };
	    }
	 
}
