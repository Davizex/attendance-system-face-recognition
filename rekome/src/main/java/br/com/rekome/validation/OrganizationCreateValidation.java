package br.com.rekome.validation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.rekome.interfaces.ValidationInterface;

public class OrganizationCreateValidation implements ValidationInterface{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(OrganizationCreateValidation.class);

	@Override
	public void execute() {
		LOGGER.debug("initialize organization create validation.");

	}

}
