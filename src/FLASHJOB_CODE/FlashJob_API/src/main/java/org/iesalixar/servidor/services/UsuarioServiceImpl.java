package org.iesalixar.servidor.services;

import java.util.List;


import org.iesalixar.servidor.error.JobNotFound;
import org.iesalixar.servidor.error.NotFound;
import org.iesalixar.servidor.model.Job;
import org.iesalixar.servidor.model.User;
import org.iesalixar.servidor.repository.CategoryRepository;
import org.iesalixar.servidor.repository.JobRepository;
import org.iesalixar.servidor.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioServiceImpl implements UsuarioService{

	@Autowired
	UsuarioRepository userRepo;
	@Autowired
	JobRepository jobRepo;
	
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

	
}
