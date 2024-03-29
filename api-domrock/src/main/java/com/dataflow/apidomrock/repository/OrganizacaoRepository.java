package com.dataflow.apidomrock.repository;

import com.dataflow.apidomrock.entities.database.Organizacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrganizacaoRepository extends JpaRepository<Organizacao, String> {
}
