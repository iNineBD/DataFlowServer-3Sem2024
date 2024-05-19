package com.dataflow.apidomrock.dto.savedepara;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DeParas(@JsonAlias("de") String de,
                      @JsonAlias("para") String para) {
}
