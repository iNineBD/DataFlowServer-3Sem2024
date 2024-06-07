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
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.nodes.Tag;
import org.yaml.snakeyaml.representer.Representer;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BronzeYAML {
    private String fileName;
    private String currentStage;
    private OrganizacaoYAML organization;
    private List<String> hash;



    public String toYAML() throws JsonProcessingException {
        DumperOptions options = new DumperOptions();
        options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        options.setPrettyFlow(true);

        // Custom Representer to remove class tag
        Representer representer = new Representer(options);
        representer.getPropertyUtils().setSkipMissingProperties(true);
        representer.addClassTag(BronzeYAML.class, Tag.MAP);
        representer.addClassTag(OrganizacaoYAML.class, Tag.MAP);

        Yaml yaml = new Yaml(representer, options);
        return yaml.dump(this);
    }
}
