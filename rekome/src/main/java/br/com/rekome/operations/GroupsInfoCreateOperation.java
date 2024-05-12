package br.com.rekome.operations;

import java.time.DayOfWeek;
import java.util.Set;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

public class GroupsInfoCreateOperation {
	
	@Enumerated(EnumType.STRING)
	private Set<DayOfWeek> days;
	
	@Max(100)
	@Min(0)
	private int attendanceLimit;

	public GroupsInfoCreateOperation() {
	}

	public Set<DayOfWeek> getDays() {
		return days;
	}

	public void setDays(Set<DayOfWeek> days) {
		this.days = days;
	}

	public int getAttendanceLimit() {
		return attendanceLimit;
	}

	public void setAttendanceLimit(int attendanceLimit) {
		this.attendanceLimit = attendanceLimit;
	}
	
	
}
