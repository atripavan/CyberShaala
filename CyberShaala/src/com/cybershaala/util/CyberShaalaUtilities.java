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
	
	public static String getMatEmbedLink(String materialLink)
	{
		return "https://www.youtube.com/embed/" + materialLink.
				substring(materialLink.indexOf("watch?v=")+8, materialLink.indexOf("&feature"));
	}
	public static String getOriginalUrl(String materialLink)
	{
		return "https://www.youtube.com/watch?v=" + materialLink.
				substring(materialLink.indexOf("embed/")+6) + "&feature=youtube_gdata";
	}
}
