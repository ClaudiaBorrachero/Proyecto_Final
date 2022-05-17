package org.iesalixar.servidor.services;

import java.util.Collections;
import java.util.Optional;

import org.iesalixar.servidor.model.User;
import org.iesalixar.servidor.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class JPAUserDetailsService implements UserDetailsService {

	@Autowired
	UsuarioRepository usuarioRepository;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		
		 Optional<User> userRes = usuarioRepository.findByEmail(email);
	        if(userRes.isEmpty())
	            throw new UsernameNotFoundException("Could not findUser with email = " + email);
	        User user = userRes.get();
	        return new org.springframework.security.core.userdetails.User(
	        		email,
	                user.getPassword(),
	                Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));
		
	}


}
