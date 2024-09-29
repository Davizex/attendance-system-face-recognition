package br.com.rekome.utils;

import java.util.Date;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;

public class DateUtils {

	public static LocalDate dateToLocalDate(Date date) {
		return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
	}
	
	public static Date expirationTime(long amountToAdd, ChronoUnit unit) {
		return Date.from(Instant.now().plus(amountToAdd, unit));
	}
}
