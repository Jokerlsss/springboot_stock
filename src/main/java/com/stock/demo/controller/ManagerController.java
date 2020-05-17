package com.stock.demo.controller;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.stock.demo.pojo.Manager;
import com.stock.demo.service.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: 刘铄
 * Date: 2020/3/23
 * Time: 10:23
 * Description:
 */
@RestController
@RequestMapping(path={"/manager"})
public class ManagerController implements BaseController<Manager>{

    @Autowired
    ManagerService managerService;

    @GetMapping("login")
    public int login(@RequestParam(value="managerName",required = false) String managerName,
                     @RequestParam(value="managerPassword",required = false) String managerPassword){
        int flag=0;
        int loginSuccess=1;
        int loginFail=2;

            // 登录
            /** 查出数据库中对应该昵称的密码 */
            Manager manager=managerService.loadByName(managerName);
            if(null==manager){
                return flag;
            }else{
                BCryptPasswordEncoder encoder=new BCryptPasswordEncoder();
                String realPassword=manager.getManagerPassword();

                /** 将数据库中的密码进行解密并匹配 */
                boolean isLogin=encoder.matches(managerPassword,realPassword);

                if(isLogin){
                    // 登录成功
                    flag=loginSuccess;
                }else{
                    // 登录失败
                    flag=loginFail;
                }
            }
        return flag;
    }

    @Override
    public List<Manager> list() {
        return null;
    }

    @Override
    public int insert(Manager bean) {
        return 0;
    }

    @Override
    public int delete(Long id) {
        return 0;
    }

    @Override
    public int update(Manager bean) {
        return 0;
    }

    @Override
    public Manager load(Long id) {
        return null;
    }

    @Override
    public Integer count() {
        return null;
    }

    @Override
    public IPage<Manager> pager(Long pageNum, Long pageSize) {
        return null;
    }

    @Override
    public Manager loadByName(String name) {
        return null;
    }

    @Override
    public IPage<Manager> pagerByName(Wrapper<Manager> wrapper, Long pageNum, Long pageSize) {
        return null;
    }
}
