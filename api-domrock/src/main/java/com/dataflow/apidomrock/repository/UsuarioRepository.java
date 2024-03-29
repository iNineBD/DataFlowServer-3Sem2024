package com.dataflow.apidomrock.repository;

import com.dataflow.apidomrock.entities.database.Arquivo;
import com.dataflow.apidomrock.entities.database.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, String> {

    @Query("select a.nomeArquivo, a.status from Usuario u inner join Arquivo a on a.organizacao = u.organizacao where u.email = :emailUsuario")
    List<Arquivo> arquivosUsuario (String emailUsuario);
}
