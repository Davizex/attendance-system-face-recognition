package br.com.rekome.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import br.com.rekome.entities.Group;
import br.com.rekome.entities.GroupInfos;
import br.com.rekome.operations.GroupsInfoCreateOperation;
import br.com.rekome.repository.GroupsInfosRepository;
import br.com.rekome.validations.GroupsInfosValidation;

@Service
public class GroupsInfoService {

	private static final Logger LOGGER = LoggerFactory.getLogger(GroupsInfoService.class);

	private final GroupsInfosRepository groupInfoRepository;

	public GroupsInfoService(GroupsInfosRepository groupInfoRepository) {
		this.groupInfoRepository = groupInfoRepository;
	}

	public void create(GroupsInfoCreateOperation groupsInfosOp, Group group) {
		LOGGER.debug("Initialize group info creation");

		new GroupsInfosValidation(groupsInfosOp).execute();

		var groupInfo = new GroupInfos(groupsInfosOp, group);

		groupInfoRepository.save(groupInfo);
	}

}
