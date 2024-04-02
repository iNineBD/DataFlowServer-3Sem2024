package com.dataflow.apidomrock.repository;

import com.dataflow.apidomrock.entities.database.Metadata;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MetadataRepository extends JpaRepository<Metadata, Integer> {
}
