package com.codewithfibbee.paykit.configurations.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;

@Configuration
@EnableWebSecurity
public class MultipleEntryPointSecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /*
     * For admin
     */
    @Configuration
    @Order(2)
    public static class AdminWebSecurityConfiguration {

        String path = "/api/v1/admin";

        @Autowired
        private JwtFilter jwtFilter;
        
//        @Autowired
//        private PasswordEncoder passwordEncoder;
//
//        @Autowired
//        AdminUserDetailsService userDetailsService;


        private final String[] AUTH_WHITELIST = {
                path+"/login",
                path+"/refresh-token"
        };


        @Bean
        public SecurityFilterChain adminAPIFilterChain(HttpSecurity http) throws Exception {
            http.cors();
            http.csrf().disable()
                    .antMatcher(path+"/**")
                    .authorizeRequests(authorize ->
                            authorize
                            .antMatchers(AUTH_WHITELIST)
                            .permitAll()
                            .anyRequest()
                            .authenticated())
                    .exceptionHandling(Customizer.withDefaults())
                    .sessionManagement( httpSecuritySessionManagementConfigurer -> httpSecuritySessionManagementConfigurer
                            .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                    .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

            return http.build();
        }

        @Bean("adminAuthenticationManager")
        public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
            return authenticationConfiguration.getAuthenticationManager();
        }

//        @Override
//        @Autowired
//        public void configure(AuthenticationManagerBuilder auth) throws Exception {
//            auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
//        }

    }

    /*
     * For all routes
     */
    @Configuration
    @Order(3)
    public static class OtherWebSecurityConfigurer {

        String path = "/api/v1";

        @Autowired
        private JwtFilter jwtFilter;

//        @Autowired
//        private PasswordEncoder passwordEncoder;
//
//        @Autowired
//        AdminUserDetailsService userDetailsService;//To be changed to CientUSerDetailsService

        private final String[] AUTH_WHITELIST = {
                path+"/forms/**",
                path+"/forgot-password/**",
                path+"/reset-password/**",
                path+"/reference/**",
                path+"/health/**",
        };


        @Bean
        public SecurityFilterChain otherAPIFilterChain(HttpSecurity http) throws Exception {
            http.cors();
            http.csrf(AbstractHttpConfigurer::disable)
                    .antMatcher(path+"/**")
                    .authorizeRequests( request -> request
                            .antMatchers(AUTH_WHITELIST)
                            .permitAll()
                            .anyRequest()
                            .authenticated())
                    .exceptionHandling(Customizer.withDefaults())
                    .sessionManagement(session -> session
                            .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                    .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
            return http.build();
        }

        @Bean("defaultAuthenticationManager")
        public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
            return authenticationConfiguration.getAuthenticationManager();
        }

//        @Override
//        @Autowired
//        public void configure(AuthenticationManagerBuilder auth) throws Exception {
//             auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
//        }
    }



    @Configuration
    @Order(5)
    public static class ApiDocWebSecurityConfigurer {
        @Autowired
        private PasswordEncoder passwordEncoder;


        @Bean
        public InMemoryUserDetailsManager userDetailsService() {
            UserDetails user = User.builder()
                    .username("user")
                    .password(passwordEncoder.encode("password#"))
                    .roles()
                    .build();
            return new InMemoryUserDetailsManager(user);
        }

        private static final String[] AUTH_LIST = {
                "/v2/api-docs",
                "/configuration/ui",
                "/swagger-resources",
                "/configuration/security",
                "/swagger-ui.html",
                "/webjars/**"
        };

        @Bean
        public SecurityFilterChain apiDocFilterChain(HttpSecurity http) throws Exception {
            http
                    .authorizeRequests(authorize ->
                            authorize
                                    .antMatchers(AUTH_LIST)
                                    .authenticated())
                    .httpBasic(httpSecurityHttpBasicConfigurer ->
                            httpSecurityHttpBasicConfigurer
                                    .authenticationEntryPoint(swaggerAuthenticationEntryPoint()))
                    .csrf(AbstractHttpConfigurer::disable);
            return http.build();
        }

        @Bean
        public BasicAuthenticationEntryPoint swaggerAuthenticationEntryPoint() {
            BasicAuthenticationEntryPoint entryPoint = new BasicAuthenticationEntryPoint();
            entryPoint.setRealmName("Swagger Realm");
            return entryPoint;
        }
    }
}