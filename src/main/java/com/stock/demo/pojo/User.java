package com.stock.demo.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * Created with IntelliJ IDEA.
 * User: 刘铄
 * Date: 2020/2/9
 * Time: 12:09
 * Description: 用户表
 */
@TableName("user")
@Data
public class User implements UserDetails {
    public User(){}

    @TableId(value = "userID",type = IdType.AUTO)
    private Long userID;

    @TableField("userName")
    private String userName;

    @TableField("userPassword")
    private String userPassword;

    @TableField("investmentCharacter")
    private String inertmentCharacter;

    //    实现了UserDetails接口之后生成的方法
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return userPassword;
    }

    @Override
    public String getUsername() {
        return userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}