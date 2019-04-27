package com.jeffy.sqlautoreview.core;

import java.util.Date;

import org.apache.commons.beanutils.Converter;

/**
 * beanutils 时间转换器
 * 
 * @ClassName: DateConverter
 * @author 陈剑飞
 * @date 2016年10月14日 下午3:44:34
 * @version v 1.0
 */
public class DateConverter implements Converter {
	@Override
	public Object convert(Class arg0, Object arg1) {
		if (arg1 == null) {  
            return null;  
        }  
		if(arg1 instanceof Date){
			return (Date)arg1;
		}
		return arg1;
	}

}
