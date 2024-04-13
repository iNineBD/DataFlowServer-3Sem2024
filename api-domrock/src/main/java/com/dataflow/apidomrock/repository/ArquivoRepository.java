package com.dataflow.apidomrock.repository;

import com.dataflow.apidomrock.entities.database.Arquivo;
import com.dataflow.apidomrock.entities.database.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ArquivoRepository extends JpaRepository<Arquivo, Long> {
    @Query(value = "SELECT * FROM Arquivo where nome_arquivo = :nomeArquivo and organizacao_nome = :nomeOrganizacao", nativeQuery = true)
    Optional<Arquivo> findByNameAndOrganization(String nomeArquivo, String nomeOrganizacao);

    Optional<Usuario> findByUsuario(Optional<Usuario> usuario);

    @Query("select a from Arquivo a where a.organizacao.nome = :organizacao")
    List<Arquivo> findByOrganizacao(String organizacao);

}
