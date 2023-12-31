package nl.miwgroningen.c11.cerberus.docentenportaalproject.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Handles photo uploads and login page.
 *
 * @author Marianne Kooistra, Mohammed Almameri, Joost Schreuder
 */

@Configuration
public class MvcConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // for security reason the name of serving link (photos) different than real folder (uploads)
        registry.addResourceHandler("/photos/**")
                .addResourceLocations("file:"+System.getProperty("user.dir") + "/uploads/");

    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {

        registry.addViewController("/login").setViewName("user/login");

    }
}