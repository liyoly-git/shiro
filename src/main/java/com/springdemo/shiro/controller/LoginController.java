package com.springdemo.shiro.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @author ly
 * @date 2019/11/14
 */
@Controller
public class LoginController {

    @GetMapping("/login")
    public String login(String username, String password, Model model){
        UsernamePasswordToken token = new UsernamePasswordToken(username,password);
        Subject currenUser = SecurityUtils.getSubject();
        try{
            //主体提交登录请求到securityManager
            currenUser.login(token);
        }catch (IncorrectCredentialsException ice){
            model.addAttribute("msg","密码不正确");

        }catch (UnknownAccountException uae){
            model.addAttribute("msg","账号不存在");

        }catch (AuthenticationException ae){
            model.addAttribute("msg","状态不正常");
        }
        if(currenUser.isAuthenticated()){
            System.out.println("认证成功");
            model.addAttribute("username",username);
            return "success";
        }else {
            token.clear();
            return "login";
        }
    }
}
