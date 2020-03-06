package com.stock.demo;

import com.stock.demo.mapper.UserMapper;
import com.stock.demo.pojo.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootTest
class DemoApplicationTests {

    @Autowired
    private UserMapper userMapper;

    @Test
    void contextLoads() {
    }

    @Test
    public void insertUser(){
        User user=new User();
        user.setUserName("Jokerls");
        user.setInertmentCharacter("稳健");

        BCryptPasswordEncoder encoder=new BCryptPasswordEncoder();
        user.setUserPassword(encoder.encode("123"));
        userMapper.insert(user);
        System.out.println("成功录入用户");
    }

}
