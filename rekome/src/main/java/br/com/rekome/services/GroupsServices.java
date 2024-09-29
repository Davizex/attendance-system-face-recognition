package br.com.rekome.services;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import br.com.rekome.entities.Group;
import br.com.rekome.entities.User;
import br.com.rekome.enums.UserRolesEnum;
import br.com.rekome.operations.GroupsCreateOperation;
import br.com.rekome.operations.GroupsEditOperation;
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

	public GroupsServices(@Lazy UserService userService, GroupsInfoService groupInfoService,
			OrganizationService organizarionService, GroupsRepository groupsRepository) {
		this.userService = userService;
		this.groupInfoService = groupInfoService;
		this.organizarionService = organizarionService;
		this.groupsRepository = groupsRepository;
	}

	public Group findByUUID(String groupUUID) {
		return this.groupsRepository.findByUuid(groupUUID)
				.orElseThrow(() -> new RuntimeException("group " + groupUUID + " not found."));
	}
	
	@Transactional
	public Group create(GroupsCreateOperation groupOp) {	
		LOGGER.debug("Initialize group creation");
		try {
			new GroupCreateValidation(groupOp).execute();
			
			var usersList = new ArrayList<User>();
			for(String userUUID : groupOp.getUsers()) {
				User user = userService.findByUUID(userUUID);
				if(user.getRole() == UserRolesEnum.ROLE_DEFAULT) {
					usersList.add(user);					
				}
			}
			
			var monitorsList = new ArrayList<User>();
			for(String userUUID : groupOp.getMonitors()) {
				User user = userService.findByUUID(userUUID);
				if(user.getRole().equals(UserRolesEnum.ROLE_MANAGER) ||
						user.getRole().equals(UserRolesEnum.ROLE_ADMIN)) {
					monitorsList.add(user);					
				}
			}
			
			var organization = this.organizarionService.findByUUID(groupOp.getOrganizationUuid());
			
			var group = new Group(groupOp, usersList, monitorsList, organization);
			group = this.groupsRepository.save(group);
			
			this.groupInfoService.create(groupOp.getGroupInfo(), group);
			return group;
		} catch (Exception e) {
			throw new RuntimeException("Error ao criar grupo: " + e.getMessage());
		}
	}

	public void insert(InsertInGroupOperation insert) {
		
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
			throw e;
		}
	}

	public void edit(GroupsEditOperation groupOp) {
		throw new UnsupportedOperationException("Group edit not implemented");
		
	}

	public void delete(String uuid) {
		throw new UnsupportedOperationException("Group delete not implemented");		
	}

}
