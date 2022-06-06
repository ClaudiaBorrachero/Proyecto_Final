package org.iesalixar.servidor.services;

import java.util.List;


import org.iesalixar.servidor.model.Job;
import org.iesalixar.servidor.model.User;
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

	
}
