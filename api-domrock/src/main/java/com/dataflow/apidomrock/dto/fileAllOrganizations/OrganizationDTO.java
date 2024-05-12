package com.dataflow.apidomrock.dto.fileAllOrganizations;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class OrganizationDTO {
    private String name;
    private String cnpj;
}
