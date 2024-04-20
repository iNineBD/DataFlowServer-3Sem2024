package com.dataflow.apidomrock.repository;

import com.dataflow.apidomrock.entities.database.Log;
import com.dataflow.apidomrock.entities.database.Metadata;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LogRepository extends JpaRepository<Log, Integer> {
}
