package com.creditcard.Creditcard.Security;

import com.creditcard.Creditcard.exception.LogInException;
import com.creditcard.Creditcard.repository.UserRepo;
import com.creditcard.Creditcard.service.UserService;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * This class determines which requests needs authentication and which does not.
 */
@EnableGlobalMethodSecurity(securedEnabled = true)
@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {

    private final UserService userDetailsService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserRepo userRepository;

    /**
     * Class constructor.
     * @param userDetailsService UserDetailService.
     * @param bCryptPasswordEncoder BCryptPasswordEncoder
     * @param userRepository UserRepository
     */
    public WebSecurity(UserService userDetailsService, BCryptPasswordEncoder bCryptPasswordEncoder, UserRepo userRepository) {
        this.userDetailsService = userDetailsService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userRepository = userRepository;
    }

    /**
     * Method affects overall security,permits request,checks for authentication,creates session,also handles login exceptions.
     * @param http HttpSecurity
     * @throws Exception Throws login exceptions
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeRequests().
        antMatchers(HttpMethod.POST,SecurityConstants.SIGN_UP_URL).
        permitAll().
        antMatchers("/v2/api-docs","/configuration/**","/swagger*/**","/webjars/**").
        permitAll().
        anyRequest().authenticated().and().
        addFilter(getAuthenticationFilter()).
        addFilter(new AuthorizationFilter(authenticationManager(),userRepository)).
        sessionManagement().
        sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().
        exceptionHandling().
        authenticationEntryPoint(new LogInException());
}

    /**
     * Method set the password encoder as bcryptPasswordEncoder.
     * @param auth AuthenticationManagerBuilder.
     * @throws Exception Throws Exceptions.
     */
    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }

    /**
     * Method sets the endpoint name for login.
     * @return AuthenticationFilter.
     * @throws Exception Throws login exceptions.
     */
    public AuthenticationFilter getAuthenticationFilter() throws Exception{
        final AuthenticationFilter filter = new AuthenticationFilter(authenticationManager());
        filter.setFilterProcessesUrl("/signin");
        return filter;
    }
}