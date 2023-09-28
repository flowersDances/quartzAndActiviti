package com.cai.quartzandactiviti.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Slf4j
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    /*拦截静态资源*/
    @Override
    public void configure(WebSecurity web) throws Exception {
        //解决静态资源被拦截的问题
        web.ignoring().antMatchers("/static/**", "/static/img/**", "/js/**", "/img/**", "/css/**", "/element/**", "/**/*.js", "/**/*.css");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/**").permitAll() // 允许公开访问的路径
                .anyRequest().authenticated() // 其他路径需要认证
                .and()
                .formLogin()
                .disable(); // 禁用默认的登录表单
        // 允许跨域
        http.cors();
        // 禁用CSRF，文件上传时不需要CSRF令牌
        http.csrf().disable();
    }
}
