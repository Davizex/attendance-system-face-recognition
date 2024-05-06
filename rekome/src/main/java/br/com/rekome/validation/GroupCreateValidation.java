package br.com.rekome.validation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.rekome.interfaces.ValidationInterface;
import br.com.rekome.operation.GroupsCreateOperation;

public class GroupCreateValidation implements ValidationInterface {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(GroupCreateValidation.class);

	private GroupsCreateOperation group;

	public GroupCreateValidation(GroupsCreateOperation group) {
		this.group = group;
	}
	
	@Override
	public void execute() {
		LOGGER.debug("initialize group create validation.");

	}
}
