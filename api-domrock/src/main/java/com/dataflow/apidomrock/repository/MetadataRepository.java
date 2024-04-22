package com.dataflow.apidomrock.repository;

import com.dataflow.apidomrock.entities.database.Arquivo;
import com.dataflow.apidomrock.entities.database.Metadata;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MetadataRepository extends JpaRepository<Metadata, Integer> {
    @Query(value = "select * from metadado where nome = :metadadoName and arquivo_id = :arquivoId", nativeQuery = true)
    Optional<Metadata> findByNameAndFile(Integer arquivoId, String metadadoName);

    void deleteAllByArquivo(Arquivo arq);

    @Query("select m from Metadata m where m.arquivo.id = :idArquivo")
    List<Metadata> findByArquivo(int idArquivo);

    @Query("select m.id from Metadata m where m.arquivo.id = :idArquivo and m.nome = :nomeMetadado")
    int findByArquivoAndMetadado(int idArquivo,String nomeMetadado);
}
