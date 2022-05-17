package org.iesalixar.servidor.repository;

import java.util.Optional;

import org.iesalixar.servidor.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<User,Long> {

	public Optional<User> findByEmail(String email);
}
