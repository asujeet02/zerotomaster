package com.cg.springsecurity.zero_to_master.config;

import com.cg.springsecurity.zero_to_master.exceptionhandling.CustomAccessDeniedHandler;
import com.cg.springsecurity.zero_to_master.exceptionhandling.CustomAuthenticationFailureHandler;
import com.cg.springsecurity.zero_to_master.exceptionhandling.CustomAuthenticationSuccessHandler;
import com.cg.springsecurity.zero_to_master.filter.CsrfCookieFilter;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;

@Configuration
@Profile("!prod")
@RequiredArgsConstructor
public class ProjectSecurityConfig {

    @Value(("${spring.security.oauth2.resourceserver.opaque.introspection-uri}"))
    String introspectionUri;

    @Value(("${spring.security.oauth2.resourceserver.introspection-client-id}"))
    String clientId;

    @Value(("${spring.security.oauth2.resourceserver.introspection-client-secret}"))
    String clientSecret;

    private final CustomAuthenticationSuccessHandler authenticationSuccessHandler;
    private final CustomAuthenticationFailureHandler authenticationFailureHandler;

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception
    {
        //http.authorizeHttpRequests((requests) -> requests.anyRequest().authenticated());
        JwtAuthenticationConverter jwtAuthenticationConverter=new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(new KeyCloakRoleConverter());
        CsrfTokenRequestAttributeHandler csrfTokenRequestAttributeHandler = new CsrfTokenRequestAttributeHandler();
        http
        //securityContext(contextConfig -> contextConfig.requireExplicitSave(false))
                .sessionManagement(sessionConfig -> sessionConfig.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .cors(corsConfig -> corsConfig.configurationSource(new CorsConfigurationSource() {
                    @Override
                    public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                        CorsConfiguration config = new CorsConfiguration();
                        config.setAllowedOrigins(Collections.singletonList("http://localhost:4200"));
                        config.setAllowedMethods(Collections.singletonList("*"));
                        config.setAllowCredentials(true);
                        config.setAllowedHeaders(Collections.singletonList("*"));
                        config.setExposedHeaders(Arrays.asList("Authorization"));
                        config.setMaxAge(3600L);
                        return config;
                    }
                }))
/*
        .sessionManagement(smc->smc.invalidSessionUrl("/invalidSession").maximumSessions(3).maxSessionsPreventsLogin(true))
*/
        .csrf(csrfConfig -> csrfConfig.csrfTokenRequestHandler(csrfTokenRequestAttributeHandler)
        .ignoringRequestMatchers("/myContact","/register")
        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))
        .addFilterAfter(new CsrfCookieFilter(), BasicAuthenticationFilter.class)
//        .addFilterBefore(new RequestValidationBeforeFilter(), BasicAuthenticationFilter.class)  ,"/apiLogin"
//        .addFilterAfter(new AuthoritiesLoggingAfterFilter(), BasicAuthenticationFilter.class)
//        .addFilterAt(new AuthoritiesLoggingAtFilter(), BasicAuthenticationFilter.class)
//        .addFilterAfter(new JWTTokenGeneratorFilter(),BasicAuthenticationFilter.class)
//        .addFilterBefore(new JWTTokenValidatorFilter(), BasicAuthenticationFilter.class)
        .requiresChannel(rcc->rcc.anyRequest().requiresInsecure())
        .authorizeHttpRequests((requests) -> requests
        /*.requestMatchers("/myAccount").hasAuthority("VIEWACCOUNT")
        .requestMatchers("/myBalance").hasAnyAuthority("VIEWBALANCE","VIEWACCOUNT")
        .requestMatchers("/myLoans").hasAuthority("VIEWLOANS")
        .requestMatchers("/myCards").hasAuthority("VIEWCARDS")*/
        .requestMatchers("/myAccount").hasRole("USER")
        .requestMatchers("/myBalance").hasAnyRole("USER","ADMIN")
        .requestMatchers("/myLoans").authenticated()
        .requestMatchers("/myCards").hasRole("USER")
        .requestMatchers("/dashboard","/user").authenticated()
        .requestMatchers("/","/home", "/holidays/**", "/myContact", "/saveMsg",
                "/courses", "/about", "/assets/**", "/login/**","/notices","/contact","error","/register").permitAll());

//        http.formLogin(flc -> flc.loginPage("/login").usernameParameter("userid").passwordParameter("secretPwd")
//        .defaultSuccessUrl("/dashboard").failureUrl("/login?error=true")     "/invalidSession","/apiLogin"
//        .successHandler(authenticationSuccessHandler).failureHandler(authenticationFailureHandler))
//        .logout(loc -> loc.logoutSuccessUrl("/login?logout=true").invalidateHttpSession(true).clearAuthentication(true)
//                    .deleteCookies("JSESSIONID"));
//
//        http.httpBasic(hbc->hbc.authenticationEntryPoint(new CustomBasicAuthenticationEntryPoint()));
        //http.oauth2ResourceServer(rsc->rsc.jwt(jwtConfigurer -> jwtConfigurer.jwtAuthenticationConverter(jwtAuthenticationConverter)));
        http.oauth2ResourceServer(rsc->rsc.opaqueToken(otc->otc.authenticationConverter(new KeyCloakOpaqueRoleConverter()).introspectionUri(this.introspectionUri).introspectionClientCredentials(this.clientId,this.clientSecret)));
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

//    @Bean
//    public PasswordEncoder passwordEncoder()
//    {
//        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
//    }
//
//    @Bean
//    public CompromisedPasswordChecker compromisedPasswordChecker()
//    {
//        return new HaveIBeenPwnedRestApiPasswordChecker();
//    }

//    @Bean
//    public AuthenticationManager authenticationManager(UserDetailsService userDetailsService,PasswordEncoder passwordEncoder)
//    {
//        EazyBankUsernamePwdAuthenticationProvider authenticationProvider=new EazyBankUsernamePwdAuthenticationProvider(userDetailsService,passwordEncoder);
//        ProviderManager providerManager=new ProviderManager(authenticationProvider);
//        providerManager.setEraseCredentialsAfterAuthentication(false);
//        return providerManager;
//    }
}