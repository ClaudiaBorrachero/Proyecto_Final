package org.iesalixar.servidor.controller;

import java.util.Collections;

import java.util.Map;

import org.iesalixar.servidor.error.EmailAlreadyUsed;
import org.iesalixar.servidor.error.InvalidLogin;
import org.iesalixar.servidor.model.JPAUserDetails;
import org.iesalixar.servidor.model.User;
import org.iesalixar.servidor.repository.UsuarioRepository;
import org.iesalixar.servidor.security.JWTUtil;
import org.iesalixar.servidor.services.UsuarioServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class AuthController {

	@Autowired
	UsuarioRepository userRepo;
	@Autowired
	JWTUtil jwtUtil;
	@Autowired
	AuthenticationManager authManager;
	@Autowired
	PasswordEncoder passwordEncoder;
	@Autowired
	UsuarioServiceImpl userService;
	
	@PostMapping("/register")
    public Map<String, Object> registerHandler(@RequestBody User user){
		
    	if(userRepo.findByEmail(user.getEmail()).orElse(null)==null) {
    		String encodedPass = passwordEncoder.encode(user.getPassword());
    		user.setPassword(encodedPass);
    		if (user.getEmail().equals("admin@gmail.com")) {
        		user.setRole("admin");
        		user.setRole(user.getRole());
        	} else {
	    		user.setRole("user");
	    		user.setRole(user.getRole());
        	}
    		user = userRepo.save(user);
    		String token = jwtUtil.generateToken(user.getEmail(), user.getRole());
    		return Collections.singletonMap("jwt_token", token);
    	} 
    	else {
    		throw new EmailAlreadyUsed();
    	}
    }
    
    @PostMapping("/login")
    public Map<String, Object> loginHandler(@RequestBody JPAUserDetails body){
        try {
            UsernamePasswordAuthenticationToken authInputToken =
                    new UsernamePasswordAuthenticationToken(body.getEmail(), body.getPassword());

            authManager.authenticate(authInputToken);

            String role = userService.findByEmail(body.getEmail()).getRole();
            String token = jwtUtil.generateToken(body.getEmail(), role);
            System.out.println(role);
            return Collections.singletonMap("jwt_token", token);
        }catch (AuthenticationException authExc){
        	throw new InvalidLogin();
        }
    }
	
}
