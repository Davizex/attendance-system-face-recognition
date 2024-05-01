package br.com.rekome.enums;

import org.springframework.security.core.GrantedAuthority;

public enum UserRolesEnum implements GrantedAuthority{
	
	DEFAULT,
	MANAGER,
	ADMIN,;

	@Override
	public String getAuthority() {
		return this.name();
	}
	
}
