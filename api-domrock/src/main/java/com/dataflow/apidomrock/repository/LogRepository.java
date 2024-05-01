package com.dataflow.apidomrock.repository;

import com.dataflow.apidomrock.entities.database.Log;
import com.dataflow.apidomrock.entities.database.Metadata;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface LogRepository extends JpaRepository<Log, Integer> {
    @Query(value = "select * from log where acao = :acao and estagio = :estagio and id_arquivo = :fileId order by data_hora desc limit 1", nativeQuery = true)
    Optional<Log> findLastObsInFile(Integer fileId, String estagio, String acao);
}
