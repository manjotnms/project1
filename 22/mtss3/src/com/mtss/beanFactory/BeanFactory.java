package com.mtss.beanFactory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.mtss.daoImpl.AdminDetailImplement;

public class BeanFactory {
	static ApplicationContext appCtx = null;
	static Log log = LogFactory.getLog(BeanFactory.class.getName());
	static{
		appCtx = new ClassPathXmlApplicationContext("Spring-Module.xml");
	}
	
	public static ApplicationContext getContext(){
		return appCtx;
	}
	
	public static Object getRequestBean(String beanName){
		log.info("***** BeanFactory(getRequestBean): Get Bean From IOC ***** "+beanName);
		return appCtx.getBean(beanName);
	}
}
