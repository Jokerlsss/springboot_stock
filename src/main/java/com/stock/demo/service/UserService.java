package com.stock.demo.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.stock.demo.pojo.User;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: 刘铄
 * Date: 2020/2/9
 * Time: 12:11
 * Description:
 */
@Service
public interface UserService extends BaseService<User>{
    // TODO:删除该多表测试
    public List<User> selectFromMoreTable();
    /** 根据 wrapper 更新 */
    public int updateByWrapper(User bean, QueryWrapper<User> queryWrapper);

    /** 注册 */
    public int registered(String userName,String userPassword);

    /** 查询该用户是否存在 */
    public int isExist(String userName);
}
