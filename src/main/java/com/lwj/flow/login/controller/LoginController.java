package com.lwj.flow.login.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.lwj.flow.login.entity.User;

@Controller
@RequestMapping("/login")
public class LoginController {
	


	@RequestMapping(value="/login",method=RequestMethod.GET)
	public String login() {
         
		return "login/login";
	}
	
	/**
	 * 用户登录
	 * @param request
	 * @param response
	 * @param user
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/login",method=RequestMethod.POST)
    public String login(HttpServletRequest request, HttpServletResponse response, User user, Model model){
        if (StringUtils.isEmpty(user.getUsername()) || StringUtils.isEmpty(user.getPassword())) {
            request.setAttribute("msg", "用户名或密码不能为空！");
            return "login/login";
        }
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token=new UsernamePasswordToken(user.getUsername(),user.getPassword());
        try {
            subject.login(token);
            // 当验证都通过后，把用户信息放在session里
            return "redirect:/login/home";
        }catch (LockedAccountException lae) {
            token.clear();
            request.setAttribute("msg", "用户已经被锁定不能登录，请与管理员联系！");
            return "login/login";
        } catch (AuthenticationException e) {
            token.clear();
            request.setAttribute("msg", "用户或密码不正确！");
            return "login/login";
        }
    }
	
	/**
	 * 首页
	 * @param request
	 * @param response
	 * @param user
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/home",method=RequestMethod.GET)
    public String loginHome(HttpServletRequest request, HttpServletResponse response, Model model){
		Session session = SecurityUtils.getSubject().getSession();
        session.setAttribute("user",(User) SecurityUtils.getSubject().getPrincipal());
        return "login/home";
    }
	
	/**
	 * 首页
	 * @param request
	 * @param response
	 * @param user
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/unauthorized",method=RequestMethod.GET)
    public String unauthorized(HttpServletRequest request, HttpServletResponse response, Model model){
//		Session session = SecurityUtils.getSubject().getSession();
//        session.setAttribute("user",(User) SecurityUtils.getSubject().getPrincipal());
        return "login/unauthorized";
    }
	
}
