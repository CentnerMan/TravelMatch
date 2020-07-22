package ru.travelmatch.configs;

/**
 * GeekBrains Java, TravelMatch.
 *
 * @author Anatoly Lebedev
 * @version 1.0.0 11.06.2020
 * @link https://github.com/Centnerman
 */

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

@EnableWebSecurity
public class JwtSecurityConfig {

    @Configuration
    @Order(1)
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
            http.antMatcher("/api/v1")
                    .httpBasic().disable()
                    .csrf().disable()
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    .and()
                    .authorizeRequests()
                    .antMatchers("/profile/**").authenticated()
                    .antMatchers("/admin/**").hasRole("ADMIN")
                    .antMatchers("/profile/**").hasAnyRole("ADMIN", "USER")
                    .anyRequest().permitAll()
                    .and()
                    .cors()
                    .and()
                    .apply(new JwtConfigurer(tokenProvider));
        }
    }

    @Configuration
    @Order(2)
    public static class BasicWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {
        private DaoAuthenticationProvider provider;

        @Autowired
        public void setProvider(DaoAuthenticationProvider provider) {
            this.provider = provider;
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.csrf().disable()
                    .authorizeRequests()
                    .antMatchers("/admin/**").hasRole("ADMIN")
                    .antMatchers("/profile/**").hasAnyRole("ADMIN", "USER")
                    .antMatchers("/admin/users/**").hasRole("ADMIN")
                    .antMatchers("/profile/**").authenticated()
                    .anyRequest().permitAll()
                    .and()
                    .formLogin()
                    .loginPage("/login")
                    .loginProcessingUrl("/authenticateTheUser")
                    .permitAll()
                    .and()
                    .logout()
                    .deleteCookies("JSESSIONID")
                    .logoutUrl("/logout")
                    .logoutSuccessUrl("/")
                    .permitAll().and().exceptionHandling().accessDeniedPage("/403");
        }

        @Override
        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
            auth.authenticationProvider(provider);
        }
    }
}

