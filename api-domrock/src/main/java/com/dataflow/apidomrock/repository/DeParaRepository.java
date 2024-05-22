package com.dataflow.apidomrock.repository;

import com.dataflow.apidomrock.entities.database.DePara;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface DeParaRepository extends JpaRepository<DePara, Integer> {

    @Transactional
    @Modifying
    @Query(value = "insert into depara(metadado_id,de,para) values(?1,?2,?3)",nativeQuery = true)
    void saveDePara(int idMetadado, String de, String para);

    @Query("select d from DePara d where d.metadado.id = :idMetadado")
    List<DePara> findByIdMetadado(int idMetadado);

    @Transactional
    @Modifying
    @Query(value = "delete from depara d where d.metadado_id = ?1", nativeQuery = true)
    void deleteDePara(int idMetado);

    @Transactional
    @Modifying
    @Query(value = "delete from depara d where d.metadado_id = ?1 and d.de = ?2", nativeQuery = true)
    void deleteDeParaCustom(int idMetado, String de);

    @Query("select count(*) From DePara d where d.de = :de and d.metadado.ID = :idMetadado")
    int buscaQtdDeParaIguais(String de, int idMetadado);
}
