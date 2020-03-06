package com.stock.demo.security;


import com.stock.demo.pojo.User;
import com.stock.demo.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

/**
 * Created with IntelliJ IDEA.
 * User: 刘铄
 * Date: 2019/10/1
 * Time: 22:48
 * Description:
 */

/**
 * 该层应看成控制层
 */
@Component("MyUserDetailsService")
@Slf4j
public class MyUserDetailsService implements UserDetailsService {
    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        System.out.println("-----------------------------------------");
        System.out.println("s:"+s);
        User user=userService.loadByName(s);
        System.out.println("user:"+user);
        if (null == user) {
            System.out.println("not found");
            throw new UsernameNotFoundException("用户名不存在");
        }
        return user;
    }
}
