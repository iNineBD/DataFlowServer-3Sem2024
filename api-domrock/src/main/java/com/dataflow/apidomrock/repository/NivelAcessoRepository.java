package com.dataflow.apidomrock.repository;

import com.dataflow.apidomrock.entities.database.NivelAcesso;
import org.hibernate.annotations.processing.Find;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NivelAcessoRepository extends JpaRepository<NivelAcesso, Integer> {
    Optional<NivelAcesso> findByNivel(String nivel);

}
