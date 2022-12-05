package com.t1k.store.config;

import com.t1k.store.service.impl.AuthService;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.annotation.Resource;
import javax.sql.DataSource;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter
{

    @Resource
    AuthService service;

    @Resource
    private DataSource dataSource;

    @Resource
    PersistentTokenRepository repository;

//    @Resource
//    DataSource dataSource;// 这里名字为dataSource的话就是用的yml文件里的数据源
    // 无法自动装配是因为没有引入spring-boot-starter-jdbc依赖
    // 在启动类上添加@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
    // 是因为mybatis的依赖被我注释
    // remember-me底层依赖spring-JDBC

    @Bean
    public PersistentTokenRepository jdbcRepository()
    {
        JdbcTokenRepositoryImpl repository = new JdbcTokenRepositoryImpl();
        repository.setDataSource(dataSource);
//        repository.setCreateTableOnStartup(true); 第一次启动会创建一张表存储token
        return repository;
    }


    // 记住我数据库中token失效时候重启服务器记住我出错
    @Override
    protected void configure(HttpSecurity http) throws Exception
    {
        http
                .authorizeRequests()
                .antMatchers( "/api/auth/**", "/districts/**").permitAll()
                .antMatchers("/api/**").authenticated()
                .anyRequest().permitAll()
                .and()
                .formLogin()
                .loginPage("/api/auth/login-deny")
                .loginProcessingUrl("/api/auth/login")
                .successForwardUrl("/api/auth/login-success")
                .failureForwardUrl("/api/auth/login-failure")
                .and()
                .logout()
                .logoutUrl("/api/auth/logout")
                .logoutSuccessUrl("/api/auth/logout-success")
                .and()
                .csrf().disable()
                .rememberMe()
                .rememberMeParameter("remember")
                .tokenValiditySeconds(60 * 60 * 24 * 7)
                .tokenRepository(repository)
                .userDetailsService(service);

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception
    {
        auth
                .userDetailsService(service)
                .passwordEncoder(new BCryptPasswordEncoder());
    }

//    @Bean
//    public CorsFilter corsFilter()
//    {
//        // 创建CorsConfiguration对象后添加配置
//        CorsConfiguration config = new CorsConfiguration();
//        // 设置放行哪些原始域，这里直接设置为所有
//        config.addAllowedOriginPattern("*");
//        // 你可以单独设置放行哪些原始域 config.addAllowedOrigin("http://localhost:2222")
//        // 放行哪些原始请求头部信息
//        config.addAllowedHeader("*");
//        // 放行哪些请求方式，*代表所有
//        config.addAllowedMethod("*");
//        // 是否允许发送Cookie，必须要开启，因为我们的JSESSIONID需要在Cookie中携带
//        config.setAllowCredentials(true);
//        // 映射路径
//        UrlBasedCorsConfigurationSource corsConfigurationSource = new UrlBasedCorsConfigurationSource();
//        corsConfigurationSource.registerCorsConfiguration("/**", config);
//        // 返回CorsFilter
//        return new CorsFilter(corsConfigurationSource);
//    }

    @Bean
    public PasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder();
    }
}

