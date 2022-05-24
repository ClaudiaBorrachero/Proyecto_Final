package org.iesalixar.servidor.services;

import java.io.IOException;
import java.util.List;

import org.iesalixar.servidor.error.CreateJobError;
import org.iesalixar.servidor.model.Category;
import org.iesalixar.servidor.model.Job;
import org.iesalixar.servidor.model.User;
import org.iesalixar.servidor.repository.CategoryRepository;
import org.iesalixar.servidor.repository.JobRepository;
import org.iesalixar.servidor.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class JobServiceImpl implements JobService{

	@Autowired
	UsuarioRepository userRepo;
	@Autowired 
	CategoryRepository categoryRepo;
	@Autowired
	JobRepository jobRepo;
	
	/**
	 * Metodo para añadir un anuncio
	 * @param email
	 * @param anuncio
	 * @return el anuncio que se ha añadido
	 */
	@Override
	public Job addJob(String email, String title, String description, String price,
			String categoryJ, String location, MultipartFile file) {
		Double convertPrecio = Double.parseDouble(price);
		if(title.isBlank() || title == null || convertPrecio < 0) {
			throw new CreateJobError();
		}
		else {
			System.out.println(categoryJ);
			Category categoriaNueva = categoryRepo.findById(categoryJ).orElse(null);
			Job nuevoAnuncio = new Job(title, description, convertPrecio, categoriaNueva);
			nuevoAnuncio.setJobDate(null);
			nuevoAnuncio.setLocation(location);
			
		
//		try {
//			nuevoAnuncio.setFile(file.getBytes());
//		} catch (IOException e) {
//			System.out.println("salta error");
//			throw new CreateJobError();
//		}
		
			User usuario = userRepo.findByEmail(email).orElse(null);
			categoriaNueva.getJobs().add(nuevoAnuncio);
			usuario.getJob().add(nuevoAnuncio);
			nuevoAnuncio.setUser(usuario);
			System.out.println("boeobeo"+nuevoAnuncio);

			jobRepo.save(nuevoAnuncio);
			categoryRepo.save(categoriaNueva);
			userRepo.save(usuario);
			System.out.println("boeobeo"+nuevoAnuncio);
			return nuevoAnuncio;
		}
	}

	/**
	 * Metodo para mostrar los 6 anuncios mas recientes
	 * @return lista de anuncios
	 */
	@Override
	public List<Job> showRecentJobs() {
		List<Job> recentJobs = jobRepo.getRecents();
		System.out.println(recentJobs.size());
		return recentJobs;
	}

}
