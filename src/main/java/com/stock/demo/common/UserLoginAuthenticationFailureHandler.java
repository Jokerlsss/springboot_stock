package com.stock.demo.common;


import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by 刘铄 on 2019/10/2.
 *
 * 用户认证失败处理类
 */

@Component("UserLoginAuthenticationFailureHandler")
@Slf4j
public class UserLoginAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {


    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {
        // log.info("{}","认证失败");


        // log.info("{}",exception.getMessage());

        // String username = (String) request.getAttribute("username");


        //JsonData jsonData = null;
        System.out.println("进入fail...");
        JSONObject res = new JSONObject();

        if (exception.getMessage().equals("用户名不存在")){
            res.put("code",402);
            res.put("msg","用户名不存在");
            //jsonData = new JsonData(402,"用户名不存在");
        }


        if(exception.getMessage().equals("Bad credentials")){
            res.put("code",403);
            res.put("msg","密码错误");
            //jsonData = new JsonData(403,"密码错误");
        }
        //System.out.println(jsonData.toString());
        //String json = new Gson().toJson(jsonData);//包装成Json 发送的前台
        //System.out.println(json);
        response.setContentType("application/json;charset=utf-8");
        PrintWriter out = response.getWriter();
        response.setStatus(500);
        out.append(res.toString());
        System.out.println("res:"+res.toString());
        out.flush();
        out.close();
    }
}

