package com.dataflow.apidomrock.repository;

import com.dataflow.apidomrock.entities.database.Metadata;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MetadataRepository extends JpaRepository<Metadata, Integer> {
    @Query(value = "select * from metadado where nome = :metadadoName and arquivo_id = :arquivoId", nativeQuery = true)
    Optional<Metadata> findByNameAndFile(Integer arquivoId, String metadadoName);
}
