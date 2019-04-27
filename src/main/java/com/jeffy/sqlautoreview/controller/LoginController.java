package com.jeffy.sqlautoreview.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jeffy.sqlautoreview.core.BaseController;


/**  
 * 登录控制器   
 * @ClassName: LoginController    
 * @author 陈剑飞    
 * @date 2016年8月18日 上午9:48:00    
 * @version  v 1.0    
 */
@Controller
public class LoginController extends BaseController {
	/**
	 * 登录页面  
	 * @author 陈剑飞    
	 * @Title: showLoginForm    
	 * @param request
	 * @return 
	 * @Return: String 返回值
	 */
	@RequestMapping("/loginPage")
    public String showLoginForm(HttpServletRequest request) {
        return "login";
    }

	/**
	 * 登录操作 
	 * @author 陈剑飞    
	 * @Title: showLoginForm    
	 * @param request
	 * @param model
	 * @Return: String 返回值
	 */
    @RequestMapping("/login")
    public String showLoginForm(Model model,HttpServletRequest request,HttpServletResponse response) {
        return "login";
    }
}
