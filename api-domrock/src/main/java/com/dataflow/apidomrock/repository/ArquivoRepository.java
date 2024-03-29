package com.dataflow.apidomrock.repository;

import com.dataflow.apidomrock.controllers.HomeController;
import com.dataflow.apidomrock.dto.HomeResponseDTO;
import com.dataflow.apidomrock.entities.database.Arquivo;
import com.dataflow.apidomrock.entities.database.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ArquivoRepository extends JpaRepository<Arquivo, Long> {

    Optional<Usuario> findByUsuario(Optional<Usuario> usuario);

    @Query("select a from Arquivo a where a.organizacao.nome = :organizacao")
    List<Arquivo> findByOrganizacao(String organizacao);

//    @Query("select a.nomeArquivo, a.status from Usuario u inner join Arquivo a on a.organizacao = u.organizacao where u.email = :emailUsuario")
//    HomeResponseDTO getArquivos (String emailUsuario);
}
