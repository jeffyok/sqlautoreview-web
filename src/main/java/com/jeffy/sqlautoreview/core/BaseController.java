package com.jeffy.sqlautoreview.core;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

/**  
 * 基础控制器    
 * @ClassName: BaseController    
 * @author 陈剑飞    
 * @date 2016年8月17日 下午4:46:00    
 * @version  v 1.0    
 */
public abstract class BaseController {
	public final static String SUCCESS ="success";  
	
	public final static String MSG ="msg";  
	
	public final static String DATA ="data";  
	
	public final static String LOGOUT_FLAG = "logoutFlag";  
	
	protected Logger logger = LoggerFactory.getLogger(getClass());
	
    @InitBinder  
    protected void initBinder(WebDataBinder binder) {  
		 binder.registerCustomEditor(Date.class, new CustomDateEditor(
                new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"), true));
    }  
}
