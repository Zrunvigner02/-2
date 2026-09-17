package com.d5data.exam.security;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final PasswordEncoder passwordEncoder;

    public UserDetailsServiceImpl(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * 内存用户：账号 test，密码 123456。
     * 生产环境应替换为数据库/目录服务查询。
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (!"test".equals(username)) {
            throw new UsernameNotFoundException("用户不存在: " + username);
        }
        return User.withUsername("test")
            .password(passwordEncoder.encode("123456"))
            .roles("USER")
            .build();
    }
}
