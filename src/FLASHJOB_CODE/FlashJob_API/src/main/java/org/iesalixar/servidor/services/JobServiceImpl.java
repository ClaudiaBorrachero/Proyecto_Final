package org.iesalixar.servidor.services;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.iesalixar.servidor.error.CreateJobError;
import org.iesalixar.servidor.error.JobNotFound;
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
	 * Metodo para mostrar anuncio por id
	 * @param idAnuncio
	 * @return el anuncio concreto
	 */
	@Override
	public Job showJob(Long idAnuncio) {
		System.out.println(jobRepo.getById(idAnuncio).getTitle());
		return jobRepo.getById(idAnuncio);
	}
	
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
			nuevoAnuncio.setJobDate(LocalDateTime.now());
			nuevoAnuncio.setLocation(location);
			
		
		try {
			nuevoAnuncio.setFile(file.getBytes());
		} catch (IOException e) {
			System.out.println("salta error");
			throw new CreateJobError();
		}
		
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
	
	@Override
	public Job addJobNoImg(String email, String title, String description, String price, String categoryJ,
			String location, String file) {
		System.out.println(price);
		Double convertPrecio = Double.parseDouble(price);
		if(title.isBlank() || title == null || convertPrecio < 0) {
			throw new CreateJobError();
		}
		else {
			System.out.println(categoryJ);
			Category categoriaNueva = categoryRepo.findById(categoryJ).orElse(null);
			Job nuevoAnuncio = new Job(title, description, convertPrecio, categoriaNueva);
			nuevoAnuncio.setJobDate(LocalDateTime.now());
			nuevoAnuncio.setLocation(location);
			
			
			try {
				byte[] defaultImage = Files.readAllBytes(Paths.get("src/main/resources/static/img/imagenload.png"));
				nuevoAnuncio.setFile(defaultImage);
			} catch (IOException e) {
				System.out.println("salta error");
				throw new CreateJobError();
			}
			
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

	
	/**
	 * Metodo para mostrar una lista de anuncios según los filtros elegidos
	 * @param termino
	 * @param categoria
	 * @param rangoPrecio
	 * @param orden
	 * @return lista de anuncios filtrada
	 */
	@Override
	public List<Job> showFilteredJobs(String termino, String categoria, int[] rangoPrecio, String orden) {
		List<Job> listaAnuncios = new ArrayList<Job>();
		if(!categoria.equals("Todas las categorias") && rangoPrecio[0] == 0 && rangoPrecio[1] == 5000) {
			if(orden.equals("Novedades")) {
				listaAnuncios = jobRepo.getJobByNewestCategory(termino, categoria);
			}
			else if(orden.equals("De más barato a más caro")) {
				listaAnuncios = jobRepo.getJobByCategoryAscPrice(termino, categoria);
			}
			else if(orden.equals("De más caro a más barato")) {
				listaAnuncios = jobRepo.getJobByCategoryDescPrice(termino, categoria);
			}
			else if(orden.equals("Distancia")) {
				//No disponible
			}
			else {
				listaAnuncios = jobRepo.getJobByOldestCategory(termino, categoria);
			}
		}
		else if((rangoPrecio[0] != 0 || rangoPrecio[1] != 5000) && categoria.equals("Todas las categorias")) {
			if(orden.equals("Novedades")) {
				listaAnuncios = jobRepo.getJobByNewestPrice(termino, rangoPrecio[0], rangoPrecio[1]);
			}
			else if(orden.equals("De más barato a más caro")) {
				listaAnuncios = jobRepo.getJobByAscPrice(termino, rangoPrecio[0], rangoPrecio[1]);
			}
			else if(orden.equals("De más caro a más barato")) {
				listaAnuncios = jobRepo.getJobByDescPrice(termino, rangoPrecio[0], rangoPrecio[1]);
			}
			else if(orden.equals("Distancia")) {
				//No disponible
			}
			else {
				listaAnuncios = jobRepo.getJobByOldestPrice(termino, rangoPrecio[0], rangoPrecio[1]);
			}
			
		}
		else if(!categoria.equals("Todas las categorias") && rangoPrecio[0] != 0 || rangoPrecio[1] != 5000) {
			if(orden.equals("Novedades")) {
				listaAnuncios = jobRepo.getJobByNewestCategoryAndPrice(termino, categoria, rangoPrecio[0], rangoPrecio[1]);
			}
			else if(orden.equals("De más barato a más caro")) {
				listaAnuncios = jobRepo.getJobByAscCategoryAndPrice(termino, categoria, rangoPrecio[0], rangoPrecio[1]);
			}
			else if(orden.equals("De más caro a más barato")) {
				listaAnuncios = jobRepo.getJobByDescCategoryAndPrice(termino, categoria, rangoPrecio[0], rangoPrecio[1]);
			}
			else if(orden.equals("Distancia")) {
				//No disponible
			}
			else {
				listaAnuncios = jobRepo.getJobByOldestCategoryAndPrice(termino, categoria, rangoPrecio[0], rangoPrecio[1]);
			}
			
		}
		else if(termino != null) {
			if(orden.equals("Novedades")) {
				listaAnuncios = jobRepo.getJobsByNewest(termino);
			}
			else if(orden.equals("De más barato a más caro")) {
				listaAnuncios = jobRepo.getJobsByDescPrice(termino);
			}
			else if(orden.equals("De más caro a más barato")) {
				listaAnuncios = jobRepo.getJobsByAscPrice(termino);
			}
			else if(orden.equals("Distancia")) {
				//No disponible
			}
			else {
				listaAnuncios = jobRepo.getJobsByOldest(termino);
			}
			
		}
		else {
			if(orden.equals("Novedades")) {
				listaAnuncios = jobRepo.getJobByNewestNoTerm();
			}
			else if(orden.equals("De más barato a más caro")) {
				listaAnuncios = jobRepo.getJobByAscPriceNoTerm();
			}
			else if(orden.equals("De más caro a más barato")) {
				listaAnuncios = jobRepo.getJobByDescPriceNoTerm();
			}
			else if(orden.equals("Distancia")) {
				//No disponible
			}
			else {
				listaAnuncios = jobRepo.getJobByOldestNoTerm();
			}			
		}
		return listaAnuncios;
	}

	
	/**
	 * Este método sirve para borrar un anuncio, para ello tenemos que eliminar el anuncio en cascada
	 * @param idAnuncio
	 * @param email
	 */
	@Override
	public void removeJob(Long idAnuncio, String email) {
		Job anuncioBorrar = jobRepo.getById(idAnuncio);
		if (email.equals("admin@gmail.com")) {
			User userDelete = userRepo.findByEmail(anuncioBorrar.getUser().getEmail()).orElse(null);
			userDelete.getJob().remove(anuncioBorrar);
			System.out.println("1");
			jobRepo.deleteById(idAnuncio);
			System.out.println("2");
			userRepo.save(userDelete);
		}
		else if(userRepo.findByEmail(email).orElse(null).getJob().contains(anuncioBorrar)) {
			categoryRepo.getById(anuncioBorrar.getCategoryJ().getName()).getJobs().remove(anuncioBorrar);
			User userDelete = userRepo.findByEmail(email).orElse(null);
			System.out.println("0");
			userDelete.getJob().remove(anuncioBorrar);
			System.out.println("1");
			jobRepo.deleteById(idAnuncio);
			System.out.println("2");
			userRepo.save(userDelete);
			System.out.println("3");
		}
		else {
			System.out.println("0");
			throw new JobNotFound(idAnuncio);
		}
	}

	

	/**
	 * Metodo para editar un anuncio, le pasamos un id y un anuncio nuevo para editar los cambios
	 * @param id
	 * @param anuncio
	 * @return el nuevo anuncio ya editado
	 */
	@Override
	public Job editAnuncio(Long idAnuncio, MultipartFile file, String title, String category, String price, String description, String location) {
		
		Double convertPrecio = Double.parseDouble(price);

//		nuevoAnuncio.setFechaAnuncio(LocalDate.now());
		if(title.isBlank() || title == null || convertPrecio < 0 ) {
			throw new CreateJobError();
		}
		else {
		Job anuncioEditar = jobRepo.getById(idAnuncio);
		anuncioEditar.setTitle(title);
		anuncioEditar.setCategoryJ(categoryRepo.findById(category).orElse(null));
		anuncioEditar.setPrice(convertPrecio);
		anuncioEditar.setDescription(description);
		try {
			anuncioEditar.setFile(file.getBytes());
		} catch (IOException e) {
			throw new CreateJobError();
		}
		
		
		jobRepo.save(anuncioEditar);
		return anuncioEditar;
		}
	
	}
	
	/**
	 * Metodo para editar un anuncio, le pasamos un id y un anuncio nuevo para editar los cambios
	 * @param id
	 * @param anuncio
	 * @return el nuevo anuncio ya editado
	 */
	@Override
	public Job editAnuncioSinFoto(Long idAnuncio, String title, String category, String price, String description, String location) {
		
		Double convertPrecio = Double.parseDouble(price);
//		nuevoAnuncio.setFechaAnuncio(LocalDate.now());
		if(title.isBlank() || title == null || convertPrecio < 0 ) {
			throw new CreateJobError();
		}
		else {
		Job anuncioEditar = jobRepo.getById(idAnuncio);
		anuncioEditar.setTitle(title);
		anuncioEditar.setCategoryJ(categoryRepo.findById(category).orElse(null));
		anuncioEditar.setPrice(convertPrecio);
		anuncioEditar.setDescription(description);
		
		
		jobRepo.save(anuncioEditar);
		return anuncioEditar;
		}
	}
	
	

	


	
	
}

