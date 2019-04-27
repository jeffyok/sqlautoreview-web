package com.jeffy.sqlautoreview.core;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.servlet.ModelAndView;

/**
 * 异常处理类
 * 
 * project：
 * @author：Jeffy Chen
 * @version v1.0 
 * @create time：2015-09-20 下午22:10:53  
 *
 */
@ControllerAdvice
public class DefaultExceptionHandler {
	private Logger logger = LoggerFactory.getLogger(DefaultExceptionHandler.class);
    
    /**
     * 异常处理
     */
    @ExceptionHandler({Exception.class})
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    public ModelAndView processException(NativeWebRequest request, Exception e) {
    	logger.error("系统错误",e);
        ModelAndView mv = new ModelAndView();
        mv.addObject("exception", e);
        mv.setViewName("error/500");
        return mv;
    }
}
