package com.stock.demo.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.stock.demo.mapper.UserMapper;
import com.stock.demo.pojo.User;
import com.stock.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: 刘铄
 * Date: 2020/2/9
 * Time: 12:12
 * Description:
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public List<User> list() {
        return userMapper.selectList(null);
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
    public User load(Long id) {
        return userMapper.selectById(id);
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
        return userMapper.selectByName(name);
    }

    @Override
    public IPage<User> pagerByName(Wrapper<User> wrapper, Long pageNum, Long pageSize) {
        return null;
    }

    @Override
    public List<User> selectFromMoreTable() {
        return userMapper.selectFromMoreTable();
    }

    @Override
    public int updateByWrapper(User bean, QueryWrapper<User> queryWrapper) {
        return userMapper.update(bean,queryWrapper);
    }

    /**
     * 注册
     * @param userName
     * @param userPassword
     * @return
     */
    @Override
    public int registered(String userName, String userPassword) {
        int flag=0;
        try {
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

    /**
     * 查询用户是否存在
     * @param userName
     * @return
     */
    @Override
    public int isExist(String userName) {
        QueryWrapper<User> userQueryWrapper=new QueryWrapper<>();
        userQueryWrapper.eq("userName",userName);
        // 统计数据库中的数量，若为0则不存在，若为1则存在
        return userMapper.selectCount(userQueryWrapper);
    }
}
