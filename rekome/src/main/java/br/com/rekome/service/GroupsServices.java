package br.com.rekome.service;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import br.com.rekome.entities.Groups;
import br.com.rekome.entities.User;
import br.com.rekome.enums.UserRolesEnum;
import br.com.rekome.operation.GroupsCreateOperation;
import br.com.rekome.repository.GroupsRepository;
import br.com.rekome.validation.GroupCreateValidation;
import jakarta.transaction.Transactional;

@Service
public class GroupsServices {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(GroupsServices.class);

	private final UserService userService;
	
	private final GroupsInfoService groupInfoService;
	
	private final OrganizationService organizarionService;
	
	private final GroupsRepository groupsRepository;

	public GroupsServices(UserService userService, GroupsInfoService groupInfoService,
			OrganizationService organizarionService, GroupsRepository groupsRepository) {
		this.userService = userService;
		this.groupInfoService = groupInfoService;
		this.organizarionService = organizarionService;
		this.groupsRepository = groupsRepository;
	}

	@Transactional
	public void create(GroupsCreateOperation groupOp) {	
		LOGGER.debug("Initialize group creation");
		try {
			new GroupCreateValidation(groupOp).execute();
			
			var usersList = new ArrayList<User>();
			for(String userUUID : groupOp.getUsers()) {
				User user = userService.findUserByUUID(userUUID);
				if(user.getRole() == UserRolesEnum.DEFAULT) {
					usersList.add(user);					
				}
			}
			
			var monitorsList = new ArrayList<User>();
			for(String userUUID : groupOp.getMonitors()) {
				User user = userService.findUserByUUID(userUUID);
				if(user.getRole().equals(UserRolesEnum.MANAGER) ||
						user.getRole().equals(UserRolesEnum.ADMIN)) {
					monitorsList.add(user);					
				}
			}
			
			var organization = this.organizarionService.findByUUID(groupOp.getOrganizationUuid());
			
			var group = new Groups(groupOp, usersList, monitorsList, organization);
			this.groupsRepository.save(group);
			
			this.groupInfoService.create(groupOp.getGroupInfo(), group);
			
		} catch (Exception e) {
			throw new RuntimeException("Error ao criar grupo: " + e.getMessage());
		}
	}

}
