package com.dataflow.apidomrock.repository;

import com.dataflow.apidomrock.entities.database.Organizacao;
import com.dataflow.apidomrock.entities.database.Tipo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

    @Repository
    public interface TipoRepository extends JpaRepository<Tipo, String> {
    }

