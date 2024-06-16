package com.dataflow.apidomrock.entities.objectYAML.silver;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MetadadoToFrom {
    private String name;
    private List<ToFrom> toFrom;
}
