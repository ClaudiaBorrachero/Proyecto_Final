package org.iesalixar.servidor.services;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import org.iesalixar.servidor.error.CreateJobError;
import org.iesalixar.servidor.error.JobNotFound;
import org.iesalixar.servidor.error.NotFound;
import org.iesalixar.servidor.model.Category;
import org.iesalixar.servidor.model.Job;
import org.iesalixar.servidor.model.User;
import org.iesalixar.servidor.repository.CategoryRepository;
import org.iesalixar.servidor.repository.JobRepository;
import org.iesalixar.servidor.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UsuarioServiceImpl implements UsuarioService{

	@Autowired
	UsuarioRepository userRepo;
	@Autowired
	JobRepository jobRepo;
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Override
	public User insertUsuario(User usuario) {		
		if (usuario!=null) {			
			return userRepo.save(usuario);
		}		
		return null;		
	}

	public List<User> mostrarJob() {		
		return userRepo.findAll();
	}

	@Override
	public List<User> showAllUsersAdmin() {		
		return userRepo.findAll();
	}
	
	@Override
	public User findByEmail(String email) {
		if(email!=null) {
			return userRepo.findByEmail(email).orElse(null);
		} 
		return null;
	}

	@Override
	public List<Job> showJobsUser(String email) {
		User nuevoUsuario = userRepo.findByEmail(email).orElse(null);
		List<Job>listaAnuncios = nuevoUsuario.getJob();
		return listaAnuncios;
	}
//
//	@Override
//	public List<Job> showFinishedJobs(String userName) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public List<Job> showRequestedJobs(String userName) {
//		// TODO Auto-generated method stub
//		return null;
//	}

	@Override
	public List<Job> showAllJobsAdmin() {		
		return jobRepo.findAll();
	}

	/**
	 * Este método sirve para borrar un user
	 * @param idUsuario
	 * @param email
	 */
	@Override
	public void removeUser(Long idUsuario, String email) {
		User userBorrar = userRepo.getById(idUsuario);
		if(userRepo.findByEmail(email).orElse(null)!=null) {
			
			User userDelete = userRepo.findByEmail(email).orElse(null);
			
//			System.out.println("1");
			userRepo.deleteById(idUsuario);
//			System.out.println("2");
			userRepo.save(userDelete);
//			System.out.println("3");
		}
		else {
			throw new JobNotFound(idUsuario);
		}
	}
	
	@Override
	public User editProfile(MultipartFile imagenProfile, String firstName, String lastName, String phoneNumber,
			String location, String idUsuario) {
		
			User userEdit = userRepo.findByEmail(idUsuario).orElse(null);
			userEdit.setFirstName(firstName);
			userEdit.setLastName(lastName);
			userEdit.setPhoneNumber(phoneNumber);
			userEdit.setLocation(location);
		
		try {
			userEdit.setFile(imagenProfile.getBytes());
		} catch (IOException e) {
			throw new CreateJobError();
		}
		
			userRepo.save(userEdit);
			return userEdit;
	}
	
	@Override
	public User editProfileNoImg(String firstName, String lastName, String phoneNumber,
			String location, String idUsuario) {
		
			User userEdit = userRepo.findByEmail(idUsuario).orElse(null);
			if(firstName != "") {
				userEdit.setFirstName(firstName);
			}
			if(lastName != "") {
				userEdit.setLastName(lastName);
			}
			if(phoneNumber != "") {
				userEdit.setPhoneNumber(phoneNumber);
			}
			if(location != "") {
				userEdit.setLocation(location);
			}
		
		
			userRepo.save(userEdit);
			return userEdit;
	}

	@Override
	public User editPass(String passwordNew, String passwordNew2, String email) {
		User userEdit = userRepo.findByEmail(email).orElse(null);
		String encodedPass = passwordEncoder.encode(passwordNew);
		
		userEdit.setPassword(encodedPass);
		userRepo.save(userEdit);
		return userEdit;
	}

		
}
