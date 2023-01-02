package io.security.corespringsecurity.security.configs;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.security.cert.Extension;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    //ctrl +o 로 override 할 것 선택
    //ctrl + shift + / 범위 주석처리

    //보안필터를 거치지 않아도될 파일들을 보안필터를 아얘 거치지 않게 설정, 비용적인 면에서 우수
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        //password는 암호화된 방식으로 줘야한다
        //이때 passwordEncoder을 빈으로 만들어 줘야한다
        //빨간글씨 = alt + shift + enter
        String password = passwordEncoder().encode("1111");
        auth.inMemoryAuthentication().withUser("user").password(password).roles("USER");
        auth.inMemoryAuthentication().withUser("manager").password(password).roles("MANAGER","USER");

        //ADMIN 사용자는 USER, MANAGER에도 할당되어야 한다
        auth.inMemoryAuthentication().withUser("admin").password(password).roles("ADMIN","MANAGER","USER");


    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/","/users").permitAll()//root 경로는 permitAll 모두 접근 가능
                .antMatchers("/mypage").hasRole("USER") //각페이지에 맞는 접근권한
                .antMatchers("/message").hasRole("MANAGER")
                .antMatchers("/config").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                .formLogin()
        ;
    }
}
