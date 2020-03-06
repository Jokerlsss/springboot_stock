package com.stock.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.stock.demo.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * Created with IntelliJ IDEA.
 * User: 刘铄
 * Date: 2020/2/9
 * Time: 12:10
 * Description:
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
    @Select("select * from user where username=#{name}")
    public User selectByName(String name);
}
