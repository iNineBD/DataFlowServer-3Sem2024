package com.dataflow.apidomrock.repository;

import com.dataflow.apidomrock.entities.database.Arquivo;
import com.dataflow.apidomrock.entities.database.Log;
import com.dataflow.apidomrock.entities.database.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface LogRepository extends JpaRepository<Log, Integer> {
    @Query(value = "select * from log where acao = :acao and estagio = :estagio and id_arquivo = :fileId order by data_hora desc limit 1", nativeQuery = true)
    Optional<Log> findLastObsInFile(Integer fileId, String estagio, String acao);

    List<Log> findByArquivo(Arquivo arquivo);

    @Query("select l from Log l where l.usuario.id = :idUsuario order by l.acao desc limit 1")
    Log findLastActionByUsuario(int idUsuario);
    @Query("select l from Log l where estagio = 'LOGINLOGOUT' and l.usuario.id = :usuario")
    List<Log> findByUsuario(int usuario);

}
