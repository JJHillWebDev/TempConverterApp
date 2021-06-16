package com.jj.tempconverter.configuration.spring;

import com.jj.tempconverter.controllers.TempConverterController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
    Configuration class for Srping.

    @author Jeremy Hill
    @version 16.0.1
 */
@Configuration
public class SpringConfiguration 
{
    @Bean
    public TempConverterController tempConverterController() 
    {
        return new TempConverterController();
    }
}
