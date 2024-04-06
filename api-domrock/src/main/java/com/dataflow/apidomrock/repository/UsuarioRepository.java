package com.dataflow.apidomrock.repository;

import com.dataflow.apidomrock.dto.HomeResponseDTO;
import com.dataflow.apidomrock.entities.database.NivelAcesso;
import com.dataflow.apidomrock.entities.database.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, String> {

    @Query("select u.niveisAcesso from Usuario u where u.email = :emailUsuario")
    NivelAcesso getNivelUsuario(String emailUsuario);

}
