package br.com.rekome.services;

import br.com.rekome.entities.Groups;
import br.com.rekome.interfaces.ValidationInterface;
import br.com.rekome.operations.InsertInGroupOperation;

public class GroupInsertInValidation implements ValidationInterface {

	private final Groups group;
	
	private final InsertInGroupOperation groupOp;

	public GroupInsertInValidation(Groups group, InsertInGroupOperation groupOp) {
		this.group = group;
		this.groupOp = groupOp;
	}

	@Override
	public void execute() {
		if(group.getUsers().size() <= 100) {
			throw new RuntimeException("Grupo está cheio.");
		}
		
		if(groupOp.getUsersUUID().size() <= 100) {
			throw new RuntimeException("quantidade invalida de usuários.");
		}
		
	}
	
	
	
}
