package com.stock.demo.controller;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.stock.demo.mapper.UserMapper;
import com.stock.demo.pojo.User;
import com.stock.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

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

    @Autowired
    private UserMapper userMapper;

    @GetMapping("login")
    public int login(@RequestParam(value="userName",required = false) String userName,
                     @RequestParam(value="userPassword",required = false) String userPassword){
        int flag=0;
        int loginSuccess=1;
        int loginFail=2;

        int isExist=userService.isExist(userName);
        if(isExist==1){
            // 登录
            /** 查出数据库中对应该昵称的密码 */
            User user=userService.loadByName(userName);

            BCryptPasswordEncoder encoder=new BCryptPasswordEncoder();
            String realPassword=user.getUserPassword();

            /** 将数据库中的密码进行解密并匹配 */
            boolean isLogin=encoder.matches(userPassword,realPassword);

            if(isLogin){
                // 登录成功
                flag=loginSuccess;
            }else{
                // 登录失败
                flag=loginFail;
            }
        }else{
            // 返回错误码，提示“是否注册并登录”-用户同意后再进行注册操作
            flag=0;
        }
        return flag;
    }

    /**
     * 注册
     * @param userName
     * @param userPassword
     * @return
     */
    @GetMapping("registered")
    public int registered(@RequestParam(value="userName",required = false) String userName,
                          @RequestParam(value="userPassword",required = false) String userPassword){
        int flag=0;
        try{
            User user=new User();
            user.setUserName(userName);
            user.setInvestmentCharacter("保守");

            BCryptPasswordEncoder encoder=new BCryptPasswordEncoder();
            user.setUserPassword(encoder.encode(userPassword));
            userMapper.insert(user);
            flag=1;
        }catch (Exception e){
            throw e;
        }
        return flag;
    }


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
    @GetMapping("loadUserByName")
    public User loadByName(@RequestParam(value="userName",required = false) String userName) {
        return userService.loadByName(userName);
    }

    @Override
    public IPage<User> pagerByName(Wrapper<User> wrapper, Long pageNum, Long pageSize) {
        return null;
    }
}
