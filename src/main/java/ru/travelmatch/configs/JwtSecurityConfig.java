package ru.travelmatch.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import ru.travelmatch.jwt.JwtConfigurer;
import ru.travelmatch.jwt.JwtTokenProvider;

@Configuration
@EnableWebSecurity
public class JwtSecurityConfig {

    @Configuration
    public static class JwtTokenWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {
        private JwtTokenProvider tokenProvider;

        @Autowired
        public void setTokenProvider(JwtTokenProvider tokenProvider) {
            this.tokenProvider = tokenProvider;
        }

        @Bean
        @Override
        public AuthenticationManager authenticationManagerBean() throws Exception {
            return super.authenticationManagerBean();
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.antMatcher("/api")
                    .httpBasic().disable()
                    .csrf().disable()
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    .and()
                    .authorizeRequests()
                    .antMatchers("/profile/**").authenticated()
                    .antMatchers("/admin/**").hasRole("ADMIN")
                    .anyRequest().permitAll()
                    .and()
                    .cors()
                    .and()
                    .apply(new JwtConfigurer(tokenProvider));
        }
    }

}
