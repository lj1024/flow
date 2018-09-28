package com.lwj.flow.common.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lwj.flow.common.entity.AjaxResponse;
public abstract class BaseController {
	
	private Logger logger = LoggerFactory.getLogger(BaseController.class);
	 
    @ExceptionHandler  
    public ModelAndView  exp(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Exception ex) {
		logger.error("请求异常:", ex);

    	boolean isAjax = false;
    	String requestedWith = httpServletRequest.getHeader("X-Requested-With");
		if (requestedWith != null && requestedWith.trim().equals("XMLHttpRequest")) {// 如果是ajax返回指定格式数据
			isAjax = true;
		} 
		if(isAjax){
			AjaxResponse<Object> ajaxResponse = new AjaxResponse<Object>();
			ajaxResponse.setStatus(false);
			ajaxResponse.setErrMsg("后台异常!!!");
			
			if(ex instanceof UnauthorizedException){
				ajaxResponse.setErrMsg("您没有权限做此操作!!!");
			} 
			httpServletResponse.setCharacterEncoding("UTF-8");
			httpServletResponse.setContentType("application/json");
			ObjectMapper mapper = new ObjectMapper();
			try {
				PrintWriter  writer = httpServletResponse.getWriter();
				writer.write(mapper.writeValueAsString(ajaxResponse));
				writer.flush();
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
			
			
		} else {
			return new ModelAndView("redirect:/login/unauthorized");
		}
		
		
          /*ex instanceof 
        request.setAttribute("ex", ex);  */
          
        
    }  
    
    public static void main(String[] args) throws ParseException {
    	
    	SimpleDateFormat  dateFormat  = new SimpleDateFormat("yyyy-MM-dd");
    	Calendar  calendar = Calendar.getInstance();
    	calendar.setTime(dateFormat.parse("2017-07-31"));
    	calendar.add(Calendar.DAY_OF_MONTH, +5);
    	System.out.println(dateFormat.format(calendar.getTime()));;
	}

}
