package com.jeffy.sqlautoreview.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jeffy.sqlautoreview.core.BaseController;

/**
 * 首页控制器
 * 
 * @ClassName: IndexController
 * @author 陈剑飞
 * @date 2016年8月25日 下午5:03:09
 * @version v 1.0
 */
@Controller
public class IndexController extends BaseController {
	/**
	 * 首页
	 * 
	 * @author 陈剑飞
	 * @Title: index
	 * @Return: String 返回值
	 */
	@RequestMapping("/")
	public String index(HttpServletRequest request) {
		request.setAttribute("target", "index");
		return "index";
	}

	/**
	 * 登录后欢迎页
	 * 
	 * @author 陈剑飞
	 * @Title: welcome
	 * @param request
	 * @Return: String 返回值
	 */
	@RequestMapping("/welcome")
	public String welcome(HttpServletRequest request) {
		
		return "welcome";
	}


	/**
	 * 登出
	 * 
	 * @author 陈剑飞
	 * @Title: logout
	 * @param request
	 * @Return: String 返回值
	 */
	@RequestMapping("/logout")
	public String logout(HttpServletRequest request) {
		return "loginPage";
	}

	/**
	 * 403页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/error/403")
	public String error403(HttpServletRequest request) {
		return "error/403";
	}

	/**
	 * 404页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/error/404")
	public String error404(HttpServletRequest request) {
		return "error/404";
	}

	/**
	 * 500页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/error/500")
	public String error500(HttpServletRequest request) {
		return "error/500";
	}
}
