package com.dataflow.apidomrock.dto.createHash;

import com.dataflow.apidomrock.dto.entitiesdto.MetadataDTO;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ResponseMetadadoDTO(@JsonAlias("Metadados") List<MetadataDTO> meta) {
}
