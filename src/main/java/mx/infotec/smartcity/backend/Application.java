package mx.infotec.smartcity.backend;

import javax.servlet.Filter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import mx.infotec.smartcity.backend.filter.LoggedUserFilter;

/**
 *
 * @author infotec
 */
@SpringBootApplication
public class Application extends SpringBootServletInitializer {

  @Bean
  public WebMvcConfigurer corsConfigurer() {
    return new WebMvcConfigurerAdapter() {
      @Override
      public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedOrigins("*").allowedMethods("*").maxAge(3600);
      }
    };
  }

  @Override
  protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
    return application.sources(Application.class);
  }

  @Bean
  public FilterRegistrationBean loggedUserFilterRegistration() {

    FilterRegistrationBean registration = new FilterRegistrationBean();
    registration.setFilter(loggedUserFilter());
    registration.addUrlPatterns("/customers/**");
    // registration.addInitParameter("paramName", "paramValue");
    registration.setName("loggedUserFilter");

    registration.setOrder(1);

    return registration;
  }

  @Bean(name = "loggedUserFilter")
  public Filter loggedUserFilter() {
    return new LoggedUserFilter();
  }

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }
}
