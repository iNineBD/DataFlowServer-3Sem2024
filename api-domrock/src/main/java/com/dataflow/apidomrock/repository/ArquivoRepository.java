package com.dataflow.apidomrock.repository;

import com.dataflow.apidomrock.entities.database.Arquivo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ArquivoRepository extends JpaRepository<Arquivo, Long> {
    @Query(value = "SELECT * FROM Arquivo where nome_arquivo = :nomeArquivo", nativeQuery = true)
    Optional<Arquivo> findNomeArquivo(String nomeArquivo);
}
