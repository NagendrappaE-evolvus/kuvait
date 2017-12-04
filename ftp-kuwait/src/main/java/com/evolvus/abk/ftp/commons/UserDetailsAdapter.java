package com.evolvus.abk.ftp.commons;

import java.util.Collection;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.evolvus.abk.ftp.domain.User;

public class UserDetailsAdapter implements UserDetails {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7901049477674668805L;
	
	private User user;
	
	@SuppressWarnings("unused")
	private UserRoleMap userRoleMap;
	
	public UserDetailsAdapter(final User user, final UserRoleMap userRoleMap) {
		// TODO Auto-generated constructor stub
		this.user = user;
		this.userRoleMap = userRoleMap;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return user.getUsername();
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return false;
	}

	public void setUser(final User user) {
		this.user = user;
	}
	
	public void setUserRoleMap(final UserRoleMap userRoleMap) {
		this.userRoleMap=userRoleMap;
	}
	
	public User getUser() {
		return this.user;
	}
	
}
