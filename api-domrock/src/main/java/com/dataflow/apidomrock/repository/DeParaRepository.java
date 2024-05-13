package com.dataflow.apidomrock.repository;

import com.dataflow.apidomrock.entities.database.DePara;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeParaRepository extends JpaRepository<DePara, Integer> {
}
