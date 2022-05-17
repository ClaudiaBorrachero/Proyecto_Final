package org.iesalixar.servidor.services;

import java.util.List;

import org.iesalixar.servidor.model.Job;
import org.iesalixar.servidor.model.User;

public interface UsuarioService {

	public User findByEmail(String email);
//	public List<Job> showJobs(String userName);
//	public List<Job> showFinishedJobs(String userName);
//	public List<Job> showRequestedJobs(String userName);
	
	public User insertUsuario(User usuario);
	
}
