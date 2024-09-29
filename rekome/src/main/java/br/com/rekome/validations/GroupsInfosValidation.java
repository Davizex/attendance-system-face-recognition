package br.com.rekome.validations;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.rekome.interfaces.ValidationInterface;
import br.com.rekome.operations.GroupsInfoCreateOperation;

public class GroupsInfosValidation implements ValidationInterface {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(GroupsInfosValidation.class);

	private GroupsInfoCreateOperation groupInfo;

	public GroupsInfosValidation(GroupsInfoCreateOperation groupInfo) {
		this.groupInfo = groupInfo;
	}

	@Override
	public void execute() {
		LOGGER.debug("initialize group infos create validation.");
		var attendanceLimit = groupInfo.getAttendanceLimit();
		
		if(attendanceLimit < 0 || attendanceLimit > 100) {
			throw new RuntimeException("Invalid attendance limit.");
		}
	}
}
