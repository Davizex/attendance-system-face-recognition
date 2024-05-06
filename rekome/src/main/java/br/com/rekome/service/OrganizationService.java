package br.com.rekome.service;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.stereotype.Service;

import br.com.rekome.entities.Organization;
import br.com.rekome.entities.User;
import br.com.rekome.enums.UserRolesEnum;
import br.com.rekome.operation.OrganizationCreateOperation;
import br.com.rekome.repository.OrganizationRepository;
import br.com.rekome.validation.OrganizationCreateValidation;

@Service
public class OrganizationService {

	private final OrganizationRepository repository;
	
	private final UserService userService;
	
	public OrganizationService(OrganizationRepository repository, UserService userService) {
		this.repository = repository;
		this.userService = userService;
	}

	public void create(OrganizationCreateOperation organizationOp) {
		try {
			new OrganizationCreateValidation().execute();
			
			var adminList = new ArrayList<User>();
			for(String userUUID : organizationOp.getAdmins()) {
				User user = userService.findUserByUUID(userUUID);
				if(user.getRole() == UserRolesEnum.ADMIN) {
					adminList.add(user);					
				}
			}
			
			var organization = new Organization(organizationOp, adminList);
			repository.save(organization);
	
		} catch (Exception e) {
			throw new RuntimeException("Error on organization creation: " + e.getMessage());
		}
	}

	public Organization findByUUID(String organizationUuid) {
		Optional<Organization> organizationOp = this.repository.findByUuid(organizationUuid);
		
		if(organizationOp.isPresent()) {
			return organizationOp.get();
		}else {
			throw new RuntimeException("organization " + organizationUuid + " not found");
		}
	}

}
