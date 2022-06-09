package org.iesalixar.servidor.services;

import java.util.List;
import org.iesalixar.servidor.model.Job;
import org.iesalixar.servidor.model.User;

public interface UsuarioService {

	public List<User> showAllUsersAdmin();
	public User findByEmail(String email);
	public List<Job> showJobsUser(String email);
	public List<Job> showAllJobsAdmin();
//	public List<Job> showFinishedJobs(String userName);
//	public List<Job> showRequestedJobs(String userName);
	
	public User insertUsuario(User usuario);
	public void removeUser(Long idUsuario, String email);
	
	
}
