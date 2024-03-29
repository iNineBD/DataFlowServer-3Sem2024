package com.dataflow.apidomrock.repository;

import com.dataflow.apidomrock.entities.database.NivelAcesso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NivelAcessoRepository extends JpaRepository<NivelAcesso, String> {
}
