package com.stock.demo.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * Created with IntelliJ IDEA.
 * User: 刘铄
 * Date: 2020/4/20
 * Time: 10:28
 * Description:
 */
@TableName("question")
@Data
public class Question {
    public Question(){}

    // 问题ID
    @TableId(value = "questionid")
    private int questionid;

    // 问题内容
    @TableField(value = "questioncontent")
    private String questioncontent;
}
