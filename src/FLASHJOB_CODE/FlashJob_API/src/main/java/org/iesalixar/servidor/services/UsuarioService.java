package org.iesalixar.servidor.services;

import java.util.List;
import org.iesalixar.servidor.model.Job;
import org.iesalixar.servidor.model.User;
import org.springframework.web.multipart.MultipartFile;

public interface UsuarioService {

	public List<User> showAllUsersAdmin();
	public User findByEmail(String email);
	public List<Job> showJobsUser(String email);
	public List<Job> showAllJobsAdmin();
//	public List<Job> showFinishedJobs(String userName);
//	public List<Job> showRequestedJobs(String userName);
	
	public User insertUsuario(User usuario);
//	public User updateUsuario(User usuario, MultipartFile file);
	
	public void removeUser(Long idUsuario, String email);
	public User editProfile(MultipartFile imagenProfile, String firstName, String lastName, String phoneNumber,
			String location, String idUsuario);
	public User editProfileNoImg(String firstName, String lastName, String phoneNumber,
			String location, String idUsuario);
	User editPass(String passwordNew, String passwordNew2, String email);
	
	
}
