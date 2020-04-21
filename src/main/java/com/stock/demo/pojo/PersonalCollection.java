package com.stock.demo.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * Created with IntelliJ IDEA.
 * User: 刘铄
 * Date: 2020/3/22
 * Time: 18:17
 * Description:
 */
@TableName("personalcollection")
@Data
public class PersonalCollection {
    public PersonalCollection(){}

    @TableId(value = "productCode")
    private String productCode;

    @TableId(value ="userid")
    private Long userid;
}
