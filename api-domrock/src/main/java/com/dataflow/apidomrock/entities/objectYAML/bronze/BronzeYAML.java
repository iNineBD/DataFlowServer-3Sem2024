package com.dataflow.apidomrock.entities.objectYAML.bronze;

import com.dataflow.apidomrock.entities.objectYAML.landing.MetadadoYAML;
import com.dataflow.apidomrock.entities.objectYAML.landing.OrganizacaoYAML;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BronzeYAML {
    private String fileName;
    private String currentStage;
    private OrganizacaoYAML organization;
    private List<MetadadoYAML> metadatas;


    public String toYAML() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL); // Exclude null values
        mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        return mapper.writeValueAsString(this);
    }
}
