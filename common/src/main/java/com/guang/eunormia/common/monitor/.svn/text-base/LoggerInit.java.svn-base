/*
 * Copyright (C) 2013 ferdi
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or any later version.
 *
 */

package com.guang.eunormia.common.monitor;

import java.io.File;
import java.util.Enumeration;
import org.apache.log4j.Appender;
import org.apache.log4j.DailyRollingFileAppender;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.xml.DOMConfigurator;

/**
 *
 * @author ferdi email:ferdi@blueferdi.com
 * @since Jul 1, 2013 4:29:29 PM
 * @version 0.0.1
*/
public class LoggerInit 
{
    public static final Logger EUNORMIA_LOG = Logger.getLogger("EUNORMIA_LOG");
	public static final Logger logger = EUNORMIA_LOG;

	static private volatile boolean initOK = false;

	private static String getLogPath() {
		String userHome = System.getProperty("user.home");
		if (!userHome.endsWith(File.separator)) {
			userHome += File.separator;
		}
		String path = userHome + "logs" + File.separator + "eunormia" + File.separator;
		File dir = new File(path);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		return path;
	}

	static {
		initEunormiaLog();
	}

	private static Appender buildAppender(String name, String fileName, String pattern) {
		DailyRollingFileAppender appender = new DailyRollingFileAppender();
		appender.setName(name);
		appender.setAppend(true);
		appender.setEncoding("GBK");
		appender.setLayout(new PatternLayout(pattern));
		appender.setFile(new File(getLogPath(), fileName).getAbsolutePath());
		appender.activateOptions();
		return appender;
	}

	static public void initEunormiaLog() {
		if (initOK)
			return;
		Appender statisticAppender = buildAppender("Eunormia_Appender", "eunormia.log", "%m");
		EUNORMIA_LOG.setAdditivity(false);
		EUNORMIA_LOG.removeAllAppenders();
		EUNORMIA_LOG.addAppender(statisticAppender);
		EUNORMIA_LOG.setLevel(Level.INFO);
	}

	static public void initEunormiaLogByFile() {
		if (initOK)
			return;

		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		Thread.currentThread().setContextClassLoader(LoggerInit.class.getClassLoader());
		DOMConfigurator.configure(LoggerInit.class.getClassLoader().getResource("eunormia-log4j.xml"));

		String logPath = getLogPath();
		for (Enumeration<?> e = Logger.getLogger("only_for_get_all_appender").getAllAppenders(); e.hasMoreElements();) {
			Appender appender = (Appender) e.nextElement();
			if (FileAppender.class.isInstance(appender)) {
				FileAppender logFileAppender = (FileAppender) appender;
				File deleteFile = new File(logFileAppender.getFile());
				File logFile = new File(logPath, logFileAppender.getFile());
				logFileAppender.setFile(logFile.getAbsolutePath());
				logFileAppender.activateOptions();
				if (deleteFile.exists()) {
					deleteFile.delete();
				}
				logger.warn("成功添加日志文件" + deleteFile.getName() + "到" + logFile.getAbsolutePath());
			}
		}
		Thread.currentThread().setContextClassLoader(loader);
		initOK = true;
	}
}
