package com.d5data.exam.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 独立的密码编码器配置。
 * 单独成类是为了避免与 SecurityConfig 形成 Bean 依赖环：
 * SecurityConfig 构造注入 UserDetailsServiceImpl，而后者依赖 PasswordEncoder。
 */
@Configuration
public class PasswordConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
