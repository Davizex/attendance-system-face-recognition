package br.com.rekome.validations;

import java.time.Period;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.rekome.interfaces.ValidationInterface;
import br.com.rekome.operations.GroupsCreateOperation;

public class GroupCreateValidation implements ValidationInterface {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(GroupCreateValidation.class);

	private GroupsCreateOperation group;

	public GroupCreateValidation(GroupsCreateOperation group) {
		this.group = group;
	}
	
	@Override
	public void execute() {
		LOGGER.debug("initialize group create validation.");
		
		if(group.getStartDate().isAfter(group.getEndDate())) {
			throw new RuntimeException("Start date must be after End date");
		}
		
		var period = Period.between(group.getStartDate(), group.getEndDate());
		
		if(period.getYears() > 2 || (period.getYears() == 2 && (period.getMonths() > 0 || period.getDays() > 0))) {
	        throw new IllegalArgumentException("Group duration must not exceed 2 years.");
		}
	}
}
