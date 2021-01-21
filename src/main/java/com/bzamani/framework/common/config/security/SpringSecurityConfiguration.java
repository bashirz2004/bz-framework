package com.bzamani.framework.common.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Value("${server.servlet.context-path}")
    private String contextPath;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private CustomJwtAuthenticationFilter customJwtAuthenticationFilter;

    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.headers().frameOptions().sameOrigin();
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/view/**").permitAll()//private pages are here, view of pages are public but in first ajax request @preauthorize controls access
                .antMatchers("/public/**").permitAll()//icludes all public pages like blogs, posts,comments,products,search,...
                .antMatchers("/assets/**").permitAll()
                .antMatchers("/api/public/**").permitAll()
                .antMatchers("/authenticate").permitAll()
                .antMatchers("/login.html").permitAll()
                .antMatchers("/register.html").permitAll()
                .antMatchers("/index.html").permitAll()
                .antMatchers("/aboutUs.html").permitAll()
                .antMatchers("/contactUs.html").permitAll()
                .antMatchers("/forgot-password.html").permitAll()
                .antMatchers("/change-password.html").permitAll()
                .antMatchers("/showAllPosts.html").permitAll()
                .antMatchers("/showPostDetail.html").permitAll()
                .antMatchers("/rest/core/file/download/**").permitAll()
                .anyRequest().fullyAuthenticated()
                .and().exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().addFilterBefore(customJwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    }


}
