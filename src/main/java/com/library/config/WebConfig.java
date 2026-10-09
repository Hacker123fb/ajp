package com.library.config;

import com.library.interceptor.RoleInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

/**
 * WebConfig - Spring MVC configuration.
 *
 * Responsibilities:
 * - Configures the JSP view resolver (prefix/suffix)
 * - Registers the RoleInterceptor for all URLs except /auth/**
 * - Serves static resources (CSS) from /resources/
 * - Registers direct view controllers for "/" and "/error"
 */
@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private RoleInterceptor roleInterceptor;

    /**
     * JSP View Resolver.
     * Maps view names like "login" to /WEB-INF/views/login.jsp
     */
    @Bean
    public ViewResolver viewResolver() {
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setViewClass(JstlView.class);
        resolver.setPrefix("/WEB-INF/views/");
        resolver.setSuffix(".jsp");
        return resolver;
    }

    /**
     * Register direct view controllers.
     * - "/" redirects to "/auth/login"
     * - "/error" maps to /WEB-INF/views/error.jsp
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("redirect:/auth/login");
        registry.addViewController("/error").setViewName("error");
    }

    /**
     * Register RoleInterceptor.
     * Excludes /auth/** so that login/logout are publicly accessible.
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(roleInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/auth/**", "/resources/**", "/error");
    }

    /**
     * Serve static resources (CSS files) from /resources/
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**")
                .addResourceLocations("/resources/");
    }

    /**
     * Allow the default servlet to handle requests that Spring MVC cannot match.
     */
    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }
}
