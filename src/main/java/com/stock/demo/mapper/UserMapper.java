package com.stock.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.stock.demo.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

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

    // TODO:删除该测试代码（用户 & 职位 多表连接）
    @Select("select a.*,b.job from user a,job b where a.userID=b.userID")
    public List<User> selectFromMoreTable();
}
