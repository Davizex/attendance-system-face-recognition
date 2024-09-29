package br.com.rekome.operations;

import java.time.LocalDate;
import java.util.Date;

public class AttendanceOperation {

	private final String userUuid;
	
	private final String groupUuid;
	
	private final LocalDate attencadeDateTime;

	public AttendanceOperation(String userUuid, String groupUuid, LocalDate attencadeDateTime) {
		this.userUuid = userUuid;
		this.groupUuid = groupUuid;
		this.attencadeDateTime = attencadeDateTime;
	}

	public String getUserUuid() {
		return userUuid;
	}

	public String getGroupUuid() {
		return groupUuid;
	}

	public LocalDate getAttencadeDateTime() {
		return attencadeDateTime;
	}
}
