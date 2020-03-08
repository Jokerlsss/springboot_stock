package com.stock.demo.service;

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
}
