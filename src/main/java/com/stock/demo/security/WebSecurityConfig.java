package com.stock.demo.security;


import com.stock.demo.common.UserLoginAuthenticationFailureHandler;
import com.stock.demo.common.UserLoginAuthenticationSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @Auther: FengYunJun
 * @Date: 2019/7/18 13:38
 * @Description:
 */
@Configuration//标识为配置类
@EnableWebSecurity//启动Spring Security的安全管理
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {


    @Autowired
    private MyUserDetailsService myUserDetailsService;

    @Autowired
    private UserLoginAuthenticationSuccessHandler userLoginAuthenticationSuccessHandler;

    @Autowired
    private UserLoginAuthenticationFailureHandler userLoginAuthenticationFailureHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception{
        /*http
                .authorizeRequests()

                .antMatchers("/","/login","/zuce","/saveuser").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/index")
                .permitAll()
                .and()
                .logout()
                .permitAll();*/
        //  允许所有用户访问"/"和"/index.html"
        http.authorizeRequests()
                .antMatchers("/user/*","/financialProduct/*","/job/*","/personalFinancialAssets/*","/fundEarnings/*","/fund/*","/goldEarnings/*","/gold/*","/historicalOperation/*","/manager/*","/personalCollection/*","/regular/*","/regularEarnings/*","/stock/*","/stockEarnings/*","/personalFinancialAssetsVO/*").permitAll()
                .anyRequest().authenticated()   // 其他地址的访问均需验证权限
                .and()
                .formLogin()
//                .loginPage("/login.html")   //  登录页
                .loginProcessingUrl("/login")      //发送Ajax请求的路径    //*****先行注释
                .usernameParameter("username")   //请求参数    //*****先行注释
                .passwordParameter("password")   //请求参数   //*****先行注释

                .failureHandler(userLoginAuthenticationFailureHandler)     //验证失败处理
                .successHandler(userLoginAuthenticationSuccessHandler)     //验证成功处理
                .and()

                .logout()
                .logoutSuccessUrl("/login.html");
        http.cors().and().csrf().disable();        //开启Cors，关闭Csrf跨域
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {                 //自定义AuthenticationProvider，使其不将UserNotFoundException转化为AuthenticationException
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setHideUserNotFoundExceptions(false);
        provider.setUserDetailsService(myUserDetailsService);   //*****先行注释
        provider.setPasswordEncoder(new BCryptPasswordEncoder());
        return provider;
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth.authenticationProvider(authenticationProvider());
        //auth.userDetailsService(myUserDetailsService).passwordEncoder(new BCryptPasswordEncoder());
    }
    @Override
    public void configure(WebSecurity web)throws Exception{
        web.ignoring().antMatchers("/resources/static/**");
    }


}
