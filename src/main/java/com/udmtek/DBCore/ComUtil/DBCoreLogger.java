package com.udmtek.DBCore.ComUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DBCoreLogger {
	private static Logger logger=LoggerFactory.getLogger(DBCoreLogger.class);
	
	public static void printInfo(String msg) {
		logger.info(msg);
	}
	
	public static void printError(String msg) {
		logger.error(msg);
	}
}
