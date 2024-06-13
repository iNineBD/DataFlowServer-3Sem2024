package com.dataflow.apidomrock.entities.objectYAML.landing;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MetadadoYAML {
    private String name;
    private String description;
    private String type;
    private String defaultValue;
    private boolean isActive;
    private boolean isRequired;
    private Integer maxLen;
}
