package br.com.rekome.utils;

import java.util.Date;
import java.time.LocalDate;
import java.time.ZoneId;

public class DateUtils {

	public static LocalDate dateToLocalDate(Date date) {
		return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
	}
		
}
