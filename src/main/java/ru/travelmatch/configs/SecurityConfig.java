package ru.travelmatch.configs;

import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import ru.travelmatch.services.UserService;

//@Configuration
//@EnableWebSecurity
//@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private UserService userService;

//    @Autowired
//    public void setUserService(UserService userService) {
//        this.userService = userService;
//    }
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.authorizeRequests()
//                .antMatchers("/admin/**").hasRole("ADMIN")
//                .antMatchers("/admin/records/**").hasAnyRole("ADMIN", "WRITER")
//                .antMatchers("/admin/users/**").hasRole("ADMIN")
//                .antMatchers("/profile/**").authenticated()
//                .anyRequest().permitAll()
//                .and()
//                .formLogin()
//                .loginPage("/login")
//                .loginProcessingUrl("/authenticateTheUser")
//                .permitAll()
//                .and()
//                .logout()
//                .deleteCookies("JSESSIONID")
//                .logoutUrl("/logout")
//                .logoutSuccessUrl("/")
//                .permitAll().and().exceptionHandling().accessDeniedPage("/403");
//    }
//
//    @Bean
//    public BCryptPasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//    @Bean
//    public DaoAuthenticationProvider authenticationProvider() {
//        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
//        auth.setUserDetailsService(userService);
//        auth.setPasswordEncoder(passwordEncoder());
//        return auth;
//    }

//  @Bean
//  public DaoAuthenticationProvider authenticationProvider() {
//    DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
//    auth.setUserDetailsService(userService);
//    auth.setPasswordEncoder(passwordEncoder());
//    return auth;
//  }
}