package com.cg.springsecurity.zero_to_master.config;

import com.cg.springsecurity.zero_to_master.exceptionhandling.CustomAccessDeniedHandler;
import com.cg.springsecurity.zero_to_master.exceptionhandling.CustomAuthenticationFailureHandler;
import com.cg.springsecurity.zero_to_master.exceptionhandling.CustomAuthenticationSuccessHandler;
import com.cg.springsecurity.zero_to_master.exceptionhandling.CustomBasicAuthenticationEntryPoint;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.password.CompromisedPasswordChecker;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.password.HaveIBeenPwnedRestApiPasswordChecker;

@Configuration
@Profile("!prod")
@RequiredArgsConstructor
public class ProjectSecurityConfig {

    private final CustomAuthenticationSuccessHandler authenticationSuccessHandler;
    private final CustomAuthenticationFailureHandler authenticationFailureHandler;

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception
    {
        //http.authorizeHttpRequests((requests) -> requests.anyRequest().authenticated());
        http.sessionManagement(smc->smc.invalidSessionUrl("/invalidSession").maximumSessions(3).maxSessionsPreventsLogin(true))
            .requiresChannel(rcc->rcc.anyRequest().requiresInsecure())
            .csrf(crsfConfig-> crsfConfig.disable()).authorizeHttpRequests((requests) -> requests
            .requestMatchers("/dashboard","/myAccount","/myBalance","/myCards","/loans","/user").authenticated()
            .requestMatchers("/","/home", "/holidays/**", "/contact", "/saveMsg",
                    "/courses", "/about", "/assets/**", "/login/**","/notices","/contact","error","/register","/invalidSession").permitAll());

            http.formLogin(flc -> flc.loginPage("/login").usernameParameter("userid").passwordParameter("secretPwd")
            .defaultSuccessUrl("/dashboard").failureUrl("/login?error=true")
            .successHandler(authenticationSuccessHandler).failureHandler(authenticationFailureHandler))
            .logout(loc -> loc.logoutSuccessUrl("/login?logout=true").invalidateHttpSession(true).clearAuthentication(true)
                        .deleteCookies("JSESSIONID"));

        http.httpBasic(hbc->hbc.authenticationEntryPoint(new CustomBasicAuthenticationEntryPoint()));
        http.exceptionHandling(ehc->ehc.accessDeniedHandler(new CustomAccessDeniedHandler()));
/*
        http.exceptionHandling(ehc->ehc.authenticationEntryPoint(new CustomBasicAuthenticationEntryPoint()));
*/
        return http.build();
    }

    /*@Bean
    public UserDetailsService userDetailsService(DataSource dataSource)
    {
       return new JdbcUserDetailsManager(dataSource);
    }*/

    @Bean
    public PasswordEncoder passwordEncoder()
    {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public CompromisedPasswordChecker compromisedPasswordChecker()
    {
        return new HaveIBeenPwnedRestApiPasswordChecker();
    }
}