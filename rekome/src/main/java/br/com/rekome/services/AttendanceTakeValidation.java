package br.com.rekome.services;

import java.time.LocalDate;

import org.springframework.util.Assert;

import br.com.rekome.interfaces.ValidationInterface;

public class AttendanceTakeValidation implements ValidationInterface {

	private final LocalDate attendanceDay;
	
	public AttendanceTakeValidation(LocalDate attendanceDay) {
		this.attendanceDay = attendanceDay;
	}

	@Override
	public void execute() {
        LocalDate today = LocalDate.now();
        Assert.isTrue(attendanceDay.isEqual(today), "Invalid attendance day, try later.");
	}

}
