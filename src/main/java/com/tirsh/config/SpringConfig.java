package com.tirsh.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

@Configuration
@ComponentScan("com.tirsh")
@EnableWebMvc
public class SpringConfig implements WebMvcConfigurer {
    final ApplicationContext applicationContext;

    public SpringConfig(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Bean
    public SpringResourceTemplateResolver templateResolver(){
        SpringResourceTemplateResolver resourceTemplateResolver = new SpringResourceTemplateResolver();
        resourceTemplateResolver.setApplicationContext(applicationContext);
        resourceTemplateResolver.setPrefix("WEB-INF/view");
        resourceTemplateResolver.setSuffix(".html");
        return resourceTemplateResolver;
    }

    @Bean
    public SpringTemplateEngine templateEngine(){
        SpringTemplateEngine springTemplateEngine = new SpringTemplateEngine();
        springTemplateEngine.setTemplateResolver(templateResolver());
        springTemplateEngine.setEnableSpringELCompiler(true);
        return springTemplateEngine;
    }
    public void configureViewResolvers(ViewResolverRegistry registry) {
        ThymeleafViewResolver thymeleafViewResolver = new ThymeleafViewResolver();
        thymeleafViewResolver.setTemplateEngine(templateEngine());
        registry.viewResolver(thymeleafViewResolver);
    }
}
