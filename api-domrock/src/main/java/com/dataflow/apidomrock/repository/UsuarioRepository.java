package com.dataflow.apidomrock.repository;

import com.dataflow.apidomrock.entities.database.NivelAcesso;
import com.dataflow.apidomrock.entities.database.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    @Query("select u.niveisAcesso from Usuario u where u.email = :emailUsuario")
    NivelAcesso getNivelUsuario(String emailUsuario);
    @Query("select u from Usuario u where u.email = :email")
    Optional<Usuario> findByEmailCustom(String email);
    @Query("select u.token from Usuario u where u.token = :token")
    Optional<Usuario> findByToken(String token);
    UserDetails findByEmail(String email);
}
