package com.ibm.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.regex.Pattern;

public class DateFormatUtil {

	public static String dateFormat(String original,String format) throws ParseException{
		String result = original;
		SimpleDateFormat datefomat = new SimpleDateFormat(format);
		if(Pattern.matches("[0-9]{4}/[0-9]+/[0-9]+ [0-9]+:[0-9]+:[0-9]+", original)){
			SimpleDateFormat datefomat1 = new SimpleDateFormat("yyyy/MM/dd HH:mm:SS");
			result = datefomat.format(datefomat1.parse(original));
		}else if(Pattern.matches("[0-9]{4}-[0-9]+-[0-9]+ [0-9]+:[0-9]+:[0-9]+", original)){
			SimpleDateFormat datefomat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:SS");
			result = datefomat.format(datefomat1.parse(original));
		}else if(Pattern.matches("[0-9]{4}/[0-9]+/[0-9]+", original)){
			SimpleDateFormat datefomat1 = new SimpleDateFormat("yyyy/MM/dd");
			result = datefomat.format(datefomat1.parse(original));
		}else if(Pattern.matches("[0-9]+/[0-9]+/[0-9]{4}", original)){
			SimpleDateFormat datefomat1 = new SimpleDateFormat("MM/dd/yyyy");
			result = datefomat.format(datefomat1.parse(original));
		}
		
		return result;
	}
}
