package br.com.rekome.services;

import java.util.ArrayList;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import br.com.rekome.entities.Groups;
import br.com.rekome.entities.User;
import br.com.rekome.enums.UserRolesEnum;
import br.com.rekome.operations.GroupsCreateOperation;
import br.com.rekome.operations.InsertInGroupOperation;
import br.com.rekome.repository.GroupsRepository;
import br.com.rekome.validations.GroupCreateValidation;
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

	public Groups findByUUID(String groupUUID) {
		Optional<Groups> groupOp = this.groupsRepository.findByUuid(groupUUID);
		
		if(groupOp.isPresent()) {
			return groupOp.get();
		}else {
			throw new RuntimeException("group " + groupUUID + " não encontrado.");
		}
	}
	
	@Transactional
	public void create(GroupsCreateOperation groupOp) {	
		LOGGER.debug("Initialize group creation");
		try {
			new GroupCreateValidation(groupOp).execute();
			
			var usersList = new ArrayList<User>();
			for(String userUUID : groupOp.getUsers()) {
				User user = userService.findByUUID(userUUID);
				if(user.getRole() == UserRolesEnum.DEFAULT) {
					usersList.add(user);					
				}
			}
			
			var monitorsList = new ArrayList<User>();
			for(String userUUID : groupOp.getMonitors()) {
				User user = userService.findByUUID(userUUID);
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

	public void insertIn(InsertInGroupOperation insert) {
		
		var group = this.findByUUID(insert.getGroupUUID());
		new GroupInsertInValidation(group, insert).execute();
		
		try {
			var users = new ArrayList<User>();
			insert.getUsersUUID().forEach((userUUID) -> {
				User user = this.userService.findByUUID(userUUID);
				users.add(user);
			});
			
			group.getUsers().addAll(users);
			
			this.groupsRepository.save(group);
			
		} catch (Exception e) {
			
		}
	}

}
