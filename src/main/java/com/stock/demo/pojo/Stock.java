package com.stock.demo.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: 刘铄
 * Date: 2020/3/8
 * Time: 11:35
 * Description:
 */
@TableName("stock")
@Data
public class Stock implements Serializable {
    private static final long serialVersionUID = 5711245009813139951L;

    public Stock(){}

    @TableId(value = "productCode")
    private String productCode;


    @TableField("issuePrice")
    private Float issuePrice;
}
