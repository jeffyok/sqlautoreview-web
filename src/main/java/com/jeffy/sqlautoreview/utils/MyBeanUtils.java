package com.jeffy.sqlautoreview.utils;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.SqlDateConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jeffy.sqlautoreview.core.DateConverter;

/**  
 * beanutis 工具类 
 * @ClassName: MyBeanUtils    
 * @author 陈剑飞    
 * @date 2016年10月14日 下午3:51:10    
 * @version  v 1.0    
 */
public class MyBeanUtils extends BeanUtils {
	private static Logger logger = LoggerFactory.getLogger(MyBeanUtils.class);

	private MyBeanUtils() {
		
	}

	static {
		// 注册sql.date的转换器，即允许BeanUtils.copyProperties时的源目标的sql类型的值允许为空
		ConvertUtils.register(new SqlDateConverter(), java.util.Date.class);
		// 注册util.date的转换器，即允许BeanUtils.copyProperties时的源目标的util类型的值允许为空
		ConvertUtils.register(new DateConverter(), java.util.Date.class);
	}

	public static void copyProperties(Object target, Object source)
			throws InvocationTargetException, IllegalAccessException {
		BeanUtils.copyProperties(target, source);
	}
}
