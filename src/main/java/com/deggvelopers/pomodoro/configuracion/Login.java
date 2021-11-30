package com.deggvelopers.pomodoro.configuracion;

import com.deggvelopers.pomodoro.servicio.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity
public class Login extends WebSecurityConfigurerAdapter {

    @Autowired
    private UsuarioServicio usuarioServicio;

//    @Autowired
//    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//        auth
//                .userDetailsService((T) usuarioServicio)
//                .passwordEncoder(new BCryptPasswordEncoder());
//    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

            http.authorizeRequests().antMatchers("/css/*", "/js/*", "/img/*").permitAll().anyRequest().authenticated()
                    
                .and()
                    .formLogin().loginPage("/").defaultSuccessUrl("/usuario/inicio",true)
                .loginProcessingUrl("")
                .usernameParameter("mail")
                .passwordParameter("password")              
                .permitAll()
                .and().logout()
                .logoutUrl("f")
                .logoutSuccessUrl("/")
                .permitAll();
    }

}
