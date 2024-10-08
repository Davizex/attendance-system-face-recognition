package br.com.rekome.operations;

import java.util.Date;

public class GroupsEditOperation {

	private Date startDate;
	
	private Date endDate;

	public GroupsEditOperation(Date startDate, Date endDate) {
		super();
		this.startDate = startDate;
		this.endDate = endDate;
	}

	public GroupsEditOperation() {
		super();
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
}
