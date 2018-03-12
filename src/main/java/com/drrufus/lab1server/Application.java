package com.drrufus.lab1server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
/*import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;*/
import org.springframework.context.annotation.ComponentScan;

/**
 * !IMPORTANT!
 * All commented code here is used for deploying on an external
 * container server (e.g. Apache Tomcat)
 * Make sure that corresponding code is also uncommented in pom.xml
 * @author drrufus
 */
@ComponentScan
@EnableAutoConfiguration
public class Application /*extends SpringBootServletInitializer*/ {

    /*@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Application.class);
    }*/
    
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
