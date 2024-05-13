package com.dataflow.apidomrock.repository;

import com.dataflow.apidomrock.entities.database.DePara;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface DeParaRepository extends JpaRepository<DePara, Integer> {

    @Transactional
    @Modifying
    @Query("insert into depara(metadado_id,de,para) values(:idMetadado,:de,:para)")
    void saveDePara(int idMetadado, String de, String para);
}
