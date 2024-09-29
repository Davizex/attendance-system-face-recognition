package br.com.rekome.enums;

import org.springframework.security.core.GrantedAuthority;

public enum UserRolesEnum implements GrantedAuthority{
	
	ROLE_DEFAULT,
	ROLE_MANAGER,
	ROLE_ADMIN,;

	@Override
	public String getAuthority() {
		return this.name();
	}
	
}
