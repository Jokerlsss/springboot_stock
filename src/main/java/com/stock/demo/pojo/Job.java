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
 * Description: 职位表
 */
// TODO:删除该测试多表的类
@TableName("job")
@Data
public class Job{
    public Job(){}

    @TableField(value = "userID")
    private Long userID;

    @TableId("job")
    private String job;

    @TableField("money")
    private Float money;
}