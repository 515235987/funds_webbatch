package com.ibm.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertiesFile {
//	private static final String FUND_CONF = "conf/fund.properties";
	private static final String FUND_CONF = "D:/work/bigbang/funds_batch/src/fund.properties";

	Properties fund_prop = null;
	public PropertiesFile() throws IOException{
		FileInputStream fund_inStream = new FileInputStream(FUND_CONF);
		fund_prop = new Properties();
		fund_prop.load(fund_inStream);
	}
	public String getItemAsString(String key){
		return String.valueOf(fund_prop.get(key));
	}
}
