package org.example.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.spring6.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring6.view.ThymeleafViewResolver;

@Configuration
@ComponentScan("org.example")
@EnableWebMvc // включение веб функций, равноценно тегу <mvc:annotation-driven/>
public class SpringConfig implements WebMvcConfigurer {
    // имплементация интерфейс нужна, чтобы настроить под свои нужды Spring MVC.
    // В данном случае вместо стандартного шаблонизатора использовать Thymeleaf
    private ApplicationContext applicationContext;

    @Autowired // внедряем контекст Spring
    public SpringConfig(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }


    @Bean
    public SpringResourceTemplateResolver templateResolver() {
        // создаём свой resolver, который будет формировать представления (view)
        SpringResourceTemplateResolver resolver = new SpringResourceTemplateResolver();
        resolver.setApplicationContext(applicationContext);
        resolver.setPrefix("/WEB-INF/views/"); // путь к шаблонам представлений
        resolver.setSuffix(".html"); // тип файлов представлений
        return resolver;
    }

    @Bean
    public SpringTemplateEngine templateEngine() {
        // создаём "движок" для обработки представлений
        SpringTemplateEngine engine = new SpringTemplateEngine();
        engine.setTemplateResolver(templateResolver());
        engine.setEnableSpringELCompiler(true);
        return engine;
    }

    // давая свою реализацию этого метода, мы задаём свой шаблонизатор вместо стандартного
    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        ThymeleafViewResolver resolver = new ThymeleafViewResolver();
        resolver.setTemplateEngine(templateEngine());
        registry.viewResolver(resolver);
    }
}
