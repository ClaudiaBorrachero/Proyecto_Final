package org.iesalixar.servidor.model;

public class JPAUserDetails{

	private String email;
	private String password;
//	private boolean active;
//	private List<GrantedAuthority> authorities;
	
	public JPAUserDetails(String email, String password) {
		
		this.email = email;
		this.password = password;
		
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

//	public boolean isActive() {
//		return active;
//	}
//
//	public void setActive(boolean active) {
//		this.active = active;
//	}
//
//	public List<GrantedAuthority> getAuthorities() {
//		return authorities;
//	}
//
//	public void setAuthorities(List<GrantedAuthority> authorities) {
//		this.authorities = authorities;
//	}
	
	
	
}
