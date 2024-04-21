package com.dataflow.apidomrock.repository;

import com.dataflow.apidomrock.entities.database.NivelAcesso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NivelAcessoRepository extends JpaRepository<NivelAcesso, Integer> {
    @Query("SELECT na FROM NivelAcesso na WHERE na.nivel = 'master'")
    Optional<NivelAcesso> findByTipo();
}
