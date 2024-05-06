package br.com.rekome.validation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.rekome.interfaces.ValidationInterface;
import br.com.rekome.operation.GroupsInfoCreateOperation;

public class GroupsInfosValidation implements ValidationInterface {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(GroupsInfosValidation.class);

	private GroupsInfoCreateOperation groupInfo;

	public GroupsInfosValidation(GroupsInfoCreateOperation groupInfo) {
		this.groupInfo = groupInfo;
	}

	@Override
	public void execute() {
		LOGGER.debug("initialize group infos create validation.");
		
	}
}
