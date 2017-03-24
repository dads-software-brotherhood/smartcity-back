package mx.infotec.smartcity.backend.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.stereotype.Component;


@Component

public class SecurityConfig extends WebSecurityConfigurerAdapter {

  @Autowired
  AuthenticationTokenFilter   authenticationTokenFilter;

  @Autowired
  TokenAuthenticationProvider tokenAuthenticationProvider;

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.addFilterBefore(authenticationTokenFilter, BasicAuthenticationFilter.class)
        .antMatcher("/prueba/**").authenticationProvider(tokenAuthenticationProvider)
        .authorizeRequests().anyRequest().authenticated();
    http.csrf().disable();

  }

  @Override
  public void configure(WebSecurity web) throws Exception {
    web.ignoring().antMatchers("/security/**");
  }

}
