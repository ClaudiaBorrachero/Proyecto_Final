package org.iesalixar.servidor.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;

import java.util.Map;

import org.iesalixar.servidor.error.CreateJobError;
import org.iesalixar.servidor.error.EmailAlreadyUsed;
import org.iesalixar.servidor.error.InvalidLogin;
import org.iesalixar.servidor.error.TokenInvalidException;
import org.iesalixar.servidor.model.JPAUserDetails;
import org.iesalixar.servidor.model.User;
import org.iesalixar.servidor.repository.UsuarioRepository;
import org.iesalixar.servidor.security.JWTUtil;
import org.iesalixar.servidor.services.UsuarioServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = {"http://localhost:4200", "http://claudiaborrachero.github.io"})
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
    		try {
    			byte[] defaultImage = Files.readAllBytes(Paths.get("src/main/resources/static/img/profileDefault.png"));
    			user.setFile(defaultImage);
    		} catch (IOException e) {
    			System.out.println("salta error");
    			throw new CreateJobError();
    		}
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
    
    
    /**
	 * Este método recibe un email por parámetro, comprueba si el email existe y devuelve el usuario al que pertenece,
	 * en caso contrario devuelve null
	 * @param email
	 * @return true | false
	 */
	@GetMapping("usuario")
    public ResponseEntity<Boolean> usuarioPorEmail(String email){
		if(userService.findByEmail(email) != null) {
			return ResponseEntity.ok(true);
//			return ResponseEntity.ok(usuarioService.usuarioPorEmail(email));
		}
		else {
//			throw new EmailNotFoundException();
			return ResponseEntity.ok(false);
		}
		
    }
	
	/**
	 * Este metodo recibe unos parámetros para actualizar la contraseña
	 * 
	 * @return usuarioEditado
	 */
	@PutMapping("/profile/updatePass")
	public ResponseEntity<User> updatePass(@RequestParam String passwordOld,@RequestParam String passwordNew,
			@RequestParam String passwordNew2) {
		String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		System.out.println("meca gago");
		if (email != null && userRepo.findByEmail(email).orElse(null) != null) {
			try {
				System.out.println(passwordOld);
	            UsernamePasswordAuthenticationToken authInputToken =
	                    new UsernamePasswordAuthenticationToken(email, passwordOld);

	            authManager.authenticate(authInputToken);

	            return new ResponseEntity<User>(userService.editPass(passwordNew, passwordNew2, email), HttpStatus.CREATED);
	        }catch (AuthenticationException authExc){
	        	//nota cambiar exception
	        	throw new InvalidLogin();
	        }
				
			
		} else {
			throw new TokenInvalidException();
		}
	}
	
}
