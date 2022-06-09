package org.iesalixar.servidor.controller;

import java.util.List;


import org.iesalixar.servidor.error.JobNotFound;
import org.iesalixar.servidor.error.NotFound;
import org.iesalixar.servidor.error.TokenInvalidException;
import org.iesalixar.servidor.error.UserNotFound;
import org.iesalixar.servidor.model.Category;
import org.iesalixar.servidor.model.Job;
import org.iesalixar.servidor.model.User;
import org.iesalixar.servidor.repository.JobRepository;
import org.iesalixar.servidor.repository.UsuarioRepository;
import org.iesalixar.servidor.services.CategoryServiceImpl;
import org.iesalixar.servidor.services.JobServiceImpl;
import org.iesalixar.servidor.services.UsuarioServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.authentication.UserServiceBeanDefinitionParser;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class MainController {

	@Autowired
	UsuarioServiceImpl usuarioService;
	@Autowired
	CategoryServiceImpl categoryService;
	@Autowired
	JobServiceImpl jobService;
	@Autowired
	JobRepository jobRepo;
	@Autowired
	UsuarioRepository userRepo;

	@GetMapping("/categorias")
	public ResponseEntity<List<Category>> showCategories() {
		if (categoryService.showCategories().isEmpty()) {
			System.out.println("se queda aqui");

			throw new RuntimeException("Error");
		} else {
			System.out.println("se queda aqui2");
//			List<Category> categories = categoryService.showCategories();
			return ResponseEntity.ok(categoryService.showCategories());
		}
	}

	/**
	 * Este método recibe un token en la cabecera de la petición y comprueba si el
	 * token es válido, en caso de ser válido devuelve el usuario al que pertenece,
	 * en caso contrario devuelve la exepcion correspondiente
	 * 
	 * @return usuario | null
	 */
	@GetMapping("comprobarToken")
	public ResponseEntity<User> validarToken() {
		String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (usuarioService.findByEmail(email) != null) {
			return ResponseEntity.ok(usuarioService.findByEmail(email));
		} else {
			throw new TokenInvalidException();
		}
	}

	/**
	 * Este método recibe un objeto de tipo anuncio como parámetro y añade el
	 * anuncio donde corresponde
	 * 
	 * @param anuncio
	 * @return anuncio en caso que se haya añadido el anuncio, notFound en caso de
	 *         que el usuario no esté logueado y error correspondiente en caso de
	 *         que no se pueda crear debido a campos invalidos
	 */
	@PostMapping("/anuncio")
	public ResponseEntity<Job> addJob(@RequestParam MultipartFile file, @RequestParam String title,
			@RequestParam String category, @RequestParam String price, @RequestParam String description,
			@RequestParam String location) {

		String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//		if (email != null && usuarioService.findByEmail(email).orElse(null) != null) {
		System.out.println(price);

		if (usuarioService.findByEmail(email) != null) {
			return new ResponseEntity<Job>(
					jobService.addJob(email, title, description, price, category, location, file), HttpStatus.CREATED);
			
		} else {
			throw new TokenInvalidException();
		}
	}
	
	@PostMapping("/anuncio/defaultImage")
	public ResponseEntity<Job> addJob(@RequestParam String file, @RequestParam String title,
			@RequestParam String category, @RequestParam String price, @RequestParam String description,
			@RequestParam String location) {

		String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//		if (email != null && usuarioService.findByEmail(email).orElse(null) != null) {
		System.out.println(price);

		if (usuarioService.findByEmail(email) != null) {
			return new ResponseEntity<Job>(
					jobService.addJobNoImg(email, title, description, price, category, location, file), HttpStatus.CREATED);
			
		} else {
			throw new TokenInvalidException();
		}
	}

	/**
	 * Metodo que devuelve todos los anuncios recientes, con un máximo de 6 anuncios
	 * 
	 * @return lista de anuncios recientes
	 */
	@GetMapping("anuncios/anuncios-recientes")
	public ResponseEntity<List<Job>> mostrarAnunciosRecientes() {
		return ResponseEntity.ok(jobService.showRecentJobs());

	}

	// ########## Busquedas de anuncios

	/**
	 * Metodo de filtrado de anuncios, recibirá unos parámetros y devolverá una
	 * lista con el filtrado corresponediente
	 * 
	 * @param termino
	 * @param categoria
	 * @param rangoPrecio
	 * @param orden
	 * @return lista de anuncios
	 */
	@GetMapping("anuncios")
	public ResponseEntity<List<Job>> mostrarAnunciosTermino(String termino, String categoria, int[] rangoPrecio,
			String orden) {

		if (termino == null || categoria == null || rangoPrecio == null || orden == null) {
			throw new NotFound();
		} else {
			if (termino.equals("undefined")) {
				termino = " ";
				return ResponseEntity.ok(jobService.showFilteredJobs(termino, categoria, rangoPrecio, orden));
			} else {
				return ResponseEntity.ok(jobService.showFilteredJobs(termino, categoria, rangoPrecio, orden));
			}

		}

	}

	/**
	 * Este método devolverá un anuncio por id, en caso de no existir el anuncio con
	 * ese id devolverá la exepcion correspondiente
	 * 
	 * @param idAnuncio
	 * @return anuncio
	 */
	@GetMapping("/anuncio/{id}")
	public ResponseEntity<Job> mostrarAnuncio(@PathVariable(value = "id") Long idAnuncio) {
		System.out.println(jobRepo.existsById(idAnuncio));
		if (jobRepo.existsById(idAnuncio)) {
			Job job1 = jobService.showJob(idAnuncio);
			System.out.println(job1);
			return ResponseEntity.ok(job1);
		} else {
			throw new JobNotFound(idAnuncio);
		}
	}
	
	/**
	 * Metodo que devuelve todos los anuncios 
	 * 
	 * @return lista o exepcion en caso de token invalido
	 */
	@GetMapping("anuncio/todos")
	public ResponseEntity<List<Job>> mostrarAnunciosAdmin() {
		String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (email != null && userRepo.findByEmail(email).orElse(null) != null) {
			return ResponseEntity.ok(usuarioService.showAllJobsAdmin());
		} else {
			throw new TokenInvalidException();
		}
	}
	
	/**
	 * Este metodo sirve para borrar anuncio por id, en caso que el token o el id
	 * del anuncio sean inválidos se devolverá la exepcion correspondiente
	 * 
	 * @param idAnuncio
	 * @return no content es caso de que se borre y exepcion correspondiente en caso
	 *         que falle
	 */
	@DeleteMapping("/anuncio/{id}")
	public ResponseEntity<?> borrarAnuncio(@PathVariable(value = "id") Long idAnuncio) {
		String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (email != null && userRepo.findByEmail(email).orElse(null) != null) {
			if (jobRepo.existsById(idAnuncio)) {

				jobService.removeJob(idAnuncio, email);
				return ResponseEntity.noContent().build();
			} else {
				throw new JobNotFound(idAnuncio);
			}

		} else {
			throw new TokenInvalidException();
		}
	}

	
	/**
	 * Metodo que devuelve todos los users 
	 * 
	 * @return lista o exepcion en caso de token invalido
	 */
	@GetMapping("usuario/todos")
	public ResponseEntity<List<User>> mostrarUsersAdmin() {
		String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (email != null && userRepo.findByEmail(email).orElse(null) != null) {
			return ResponseEntity.ok(usuarioService.showAllUsersAdmin());
		} else {
			throw new TokenInvalidException();
		}
	}
	
	
	/**
	 * Metodo que devuelve todos los anuncios que hay en la lista ListaOfertados
	 * 
	 * @return lista o exepcion en caso de token invalido
	 */
	@GetMapping("profile/mis_jobs")
	public ResponseEntity<List<Job>> mostrarAnuncios() {
		String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (email != null && userRepo.findByEmail(email).orElse(null) != null) {
			return ResponseEntity.ok(usuarioService.showJobsUser(email));
		} else {
			throw new TokenInvalidException();
		}
	}
	
	
	/**
	 * Este metodo sirve para borrar user por id
	 * 
	 * @param idAnuncio
	 * @return no content es caso de que se borre y exepcion correspondiente en caso
	 *         que falle
	 */
	@DeleteMapping("/usuario/{id}")
	public ResponseEntity<?> borrarUsuario(@PathVariable(value = "id") Long idUsuario) {
		String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (email != null && userRepo.findByEmail(email).orElse(null) != null) {
			if (userRepo.existsById(idUsuario)) {

				usuarioService.removeUser(idUsuario, email);
				return ResponseEntity.noContent().build();
			} else {
				throw new UserNotFound(idUsuario);
			}

		} else {
			throw new TokenInvalidException();
		}
	}
	
}
