package br.com.martinesdev.estudospring.security;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.google.common.collect.Sets;


public enum ApplicationUserRole 
{
	STUDENT( Sets.newHashSet() ),
	TEACHER( Sets.newHashSet(
		ApplicationUserPermission.STUDENT_READ		
	)),
	ADMIN( Sets.newHashSet( 
		ApplicationUserPermission.STUDENT_READ,
		ApplicationUserPermission.STUDENT_WRITE,
		ApplicationUserPermission.COURSE_READ,
		ApplicationUserPermission.COURSE_WRITE
	) ),
	ADMINTRAINEE( Sets.newHashSet( 
			ApplicationUserPermission.STUDENT_READ,
			ApplicationUserPermission.COURSE_READ
	) );
	
	private final Set<ApplicationUserPermission> permissions;


	private ApplicationUserRole(Set<ApplicationUserPermission> permissions) {
		this.permissions = permissions;
	}
	
	public Set<ApplicationUserPermission> getPermissions() {
		return permissions;
	}
	
	public Set<SimpleGrantedAuthority> getGrantedAuthorities() {
		Set<SimpleGrantedAuthority> _permissions = getPermissions()
				.stream()
				.map( permissions -> new SimpleGrantedAuthority( permissions.getPermission() ) )
				.collect( Collectors.toSet() );
		
		_permissions.add( new SimpleGrantedAuthority("ROLE_"+this.name() ));
		
		return _permissions;
	}
	
}
