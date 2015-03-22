package com.mtss.logs;

import mtss.Gui.MainClass;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class LogsMtss {
	Logger log = Logger.getLogger(MainClass.class.getName());
	static {
		PropertyConfigurator.configure("C://mtss//properties//log4j.properties");
	}
	public static Logger getLogger(String className) {
		Logger log = Logger.getLogger(className);
		return log;
	}
}
