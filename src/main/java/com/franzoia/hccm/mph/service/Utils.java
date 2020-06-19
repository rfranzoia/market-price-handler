package com.franzoia.hccm.mph.service;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Simple utility class
 * 
 * @author franzoiar
 *
 */
public class Utils {

	public static final SimpleDateFormat TIMESTAMP_FORMAT = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss:sss");
	
	public static String getFormattedTimestamp(Date date) {
		return TIMESTAMP_FORMAT.format(date);
	}
	
	
}
