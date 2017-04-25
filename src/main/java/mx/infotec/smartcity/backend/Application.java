package mx.infotec.smartcity.backend;

import javax.servlet.Filter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import mx.infotec.smartcity.backend.filter.AdminFilter;
import mx.infotec.smartcity.backend.filter.LoggedUserFilter;
import mx.infotec.smartcity.backend.filter.SelfDataEditFilter;

/**
 *
 * @author infotec
 */
@SpringBootApplication
@EnableScheduling
public class Application extends SpringBootServletInitializer {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurerAdapter() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedOrigins("*").allowedMethods("*")
                        .allowedHeaders("*").allowCredentials(true).maxAge(3600);
            }
        };
    }

    @Bean
    public FilterRegistrationBean corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config);
        FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(source));
        bean.setOrder(0);
        return bean;
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Application.class);
    }

    @Bean
    public FilterRegistrationBean loggedUserFilterRegistration() {

        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(loggedUserFilter());
        registration.addUrlPatterns("/user-profile/*", "/public-transports/*", "/register/update-password", "/admin/*",
                 "/groupss/*");
        // registration.addInitParameter("paramName", "paramValue");
        registration.setName("loggedUserFilter");

        registration.setOrder(1);

        return registration;
    }

    @Bean(name = "loggedUserFilter")
    public Filter loggedUserFilter() {
        return new LoggedUserFilter();
    }

    @Bean
    public FilterRegistrationBean selfDataEditFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(selfDataEditFilter());
        registration.addUrlPatterns("/user-profile/*");
        registration.addServletNames("selfDataEditFilter");
        registration.setOrder(2);

        return registration;

    }

    @Bean(name = "selfDataEditFilter")
    public Filter selfDataEditFilter() {
        return new SelfDataEditFilter();
    }
    

    @Bean
    public FilterRegistrationBean adminFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(adminFilter());
        registration.addServletNames("adminFilter");
        registration.addUrlPatterns("/admin/*","/user-profile/*", "/public-transports/*", "/groups/*", "/rules/*");
//        registration.addUrlPatterns("/rules/*");
        registration.setOrder(3);
        return registration;
    }

    @Bean
    public Filter adminFilter() {
        return new AdminFilter();
    }
    
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
