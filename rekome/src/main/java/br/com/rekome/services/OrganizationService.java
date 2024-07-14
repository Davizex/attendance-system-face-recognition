package br.com.rekome.services;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.stereotype.Service;

import br.com.rekome.entities.Organization;
import br.com.rekome.entities.User;
import br.com.rekome.enums.UserRolesEnum;
import br.com.rekome.operations.OrganizationCreateOperation;
import br.com.rekome.operations.OrganizationEditOperation;
import br.com.rekome.repository.OrganizationRepository;
import br.com.rekome.validations.OrganizationCreateValidation;

@Service
public class OrganizationService {

	private final OrganizationRepository repository;
	
	private final UserService userService;
	
	public OrganizationService(OrganizationRepository repository, UserService userService) {
		this.repository = repository;
		this.userService = userService;
	}

	public Organization create(OrganizationCreateOperation organizationOp) throws Exception{
		try {
			new OrganizationCreateValidation().execute();
			
			var adminList = new ArrayList<User>();
			for(String userUUID : organizationOp.getAdmins()) {
				User user = userService.findByUUID(userUUID);
				if(user.getRole() == UserRolesEnum.ADMIN) {
					adminList.add(user);					
				}
			}
			
			var organization = new Organization(organizationOp, adminList);
			return repository.save(organization);
	
		} catch (Exception e) {
			throw new RuntimeException("Error on organization creation: " + e.getMessage());
		}
	}

	public void delete(String uuid) throws Exception {
		var organization = findByUUID(uuid);

		if (organization.getGroups().isEmpty()) {
			//TODO
		} else {
			throw new Exception("Organization can't be deleted has " + organization.getGroups().size()
					+ " groups, need to delete them.");
		}
	}

	public Organization findByUUID(String uuid) {
		Optional<Organization> organizationOp = this.repository.findByUuid(uuid);
		
		if(organizationOp.isPresent()) {
			return organizationOp.get();
		}else {
			throw new RuntimeException("organization " + uuid + " not found");
		}
	}

	public void edit(OrganizationEditOperation organization) {
		// TODO
	}

}
