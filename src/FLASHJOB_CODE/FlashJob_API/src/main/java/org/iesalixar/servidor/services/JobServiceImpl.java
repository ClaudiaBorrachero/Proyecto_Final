package org.iesalixar.servidor.services;

import java.io.IOException;



import org.iesalixar.servidor.error.CreateJobError;
import org.iesalixar.servidor.model.Category;
import org.iesalixar.servidor.model.Job;
import org.iesalixar.servidor.model.User;
import org.iesalixar.servidor.repository.CategoryRepository;
import org.iesalixar.servidor.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

public class JobServiceImpl implements JobService{

	@Autowired
	UsuarioRepository userRepo;
	@Autowired 
	CategoryRepository categoryRepo;
	
	
	/**
	 * Metodo para añadir un anuncio
	 * @param email
	 * @param anuncio
	 * @return el anuncio que se ha añadido
	 */
//	@Override
//	public Job addJob(String email, String title, String description, int price,
//			Category categoryJ, String location, MultipartFile file) {
//		if(title.isBlank() || title == null ) {
//			throw new CreateJobError();
//		}
//		else {
//		Job nuevoAnuncio = new Job(title, description, price, categoryJ);
//		nuevoAnuncio.setJobDate(null);
//		nuevoAnuncio.setLocation(location);
//		
//		
//		
//		try {
//			nuevoAnuncio.setFile(file.getBytes());
//		} catch (IOException e) {
//			throw new CreateJobError();
//		}
//		
//		User usuario = userRepo.findByEmail(email).orElse(null);
//		Category categoriaNueva = categoryRepo.findById(categoryJ).orElse(null);
//		categoriaNueva.getJobs().add(nuevoAnuncio);
//		usuario.getJob().add(nuevoAnuncio);
//		nuevoAnuncio.setUser(usuario);
//		jobRepo.save(nuevoAnuncio);
//		categoryRepo.save(categoriaNueva);
//		userRepo.save(usuario);
//		return nuevoAnuncio;
//		}
//		return null;
//	}

}
