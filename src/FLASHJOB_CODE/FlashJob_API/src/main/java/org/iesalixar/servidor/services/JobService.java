package org.iesalixar.servidor.services;

import java.util.List;

import org.iesalixar.servidor.model.Job;
import org.iesalixar.servidor.model.User;
import org.springframework.web.multipart.MultipartFile;

public interface JobService {

	public Job showJob(Long idAnuncio);
	
	public Job addJob(String email, String title, String description, String price,
			String categoryJ, String location, MultipartFile file);
	
	public List<Job> showRecentJobs();
	
	public List<Job> showFilteredJobs(String termino, String categoria, int[] rangoPrecio, String orden);
	
	public void removeJob(Long idAnuncio, String email);
	
	public Job addJobNoImg(String email, String title, String description, String price,
			String categoryJ, String location, String file);
	
	public Job editAnuncio(Long idAnuncio, MultipartFile file, String title, String category, String price, String description, String location);
	public Job editAnuncioSinFoto(Long idAnuncio, String title, String category, String price, String description, String location);

}
