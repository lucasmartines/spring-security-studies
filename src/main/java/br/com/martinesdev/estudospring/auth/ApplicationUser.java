package br.com.martinesdev.estudospring.auth;

import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class ApplicationUser implements UserDetails {


	
	
	private final Set<? extends GrantedAuthority> grantedAuthorities;
	private final String password;
	private final String username;
	private final boolean isAccountNonLocked;
	private final boolean isCredentialsNonExpired;
	private final boolean isAccountNonExpired;
	private final boolean isEnabled;

	public ApplicationUser(
			Set<? extends GrantedAuthority> grantedAuthorities,
			String password, 
			String username,
			boolean isAccountNonLocked, 
			boolean isCredentialsNonExpired,
			boolean isAccountNonExpired,
			boolean isEnabled) 
	{

		this.grantedAuthorities = grantedAuthorities;
		this.password = password;
		this.username = username;
		this.isAccountNonLocked = isAccountNonLocked;
		this.isCredentialsNonExpired = isCredentialsNonExpired;
		this.isAccountNonExpired = isAccountNonExpired;
		this.isEnabled = isEnabled;
	}

	@Override
	public Set<? extends GrantedAuthority> getAuthorities() {
		return grantedAuthorities;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return isAccountNonExpired;
	}

	@Override
	public boolean isAccountNonLocked() {
		return isAccountNonLocked;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return isCredentialsNonExpired;
	}

	@Override
	public boolean isEnabled() {
		return isEnabled;
	}

	private static final long serialVersionUID = 6407291202951084917L;
}
