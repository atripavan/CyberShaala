package com.cybershaala.util;

import java.util.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class CyberShaalaUtilities {
	
	public static Timestamp convertStrToTimestamp(String dateString) throws ParseException{
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
	    Date parsedDate = dateFormat.parse(dateString);
	    return new Timestamp(parsedDate.getTime());
	}
}
