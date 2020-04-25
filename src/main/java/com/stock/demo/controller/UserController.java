package com.stock.demo.controller;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.stock.demo.pojo.User;
import com.stock.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: 刘铄
 * Date: 2020/3/6
 * Time: 10:55
 * Description:
 */
@RestController
@RequestMapping(path={"/user"})
public class UserController implements BaseController<User>{

    @Autowired
    private UserService userService;

    @Override
    @GetMapping("list")
    public List<User> list() {
        return userService.list();
    }

    // TODO:删除该多表测试
    @GetMapping("moretable")
    public List<User> listMore(){
        return userService.selectFromMoreTable();
    }


    @Override
    public int insert(User bean) {
        return 0;
    }

    @Override
    public int delete(Long id) {
        return 0;
    }

    @Override
    public int update(User bean) {
        return 0;
    }

    @Override
    @GetMapping("loadById")
    public User load(@RequestParam(value="userID",required = false) Long userID) {
        return userService.load(userID);
    }

    @Override
    public Integer count() {
        return null;
    }

    @Override
    public IPage<User> pager(Long pageNum, Long pageSize) {
        return null;
    }

    @Override
    public User loadByName(String name) {
        return null;
    }

    @Override
    public IPage<User> pagerByName(Wrapper<User> wrapper, Long pageNum, Long pageSize) {
        return null;
    }
}
