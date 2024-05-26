package com.dataflow.apidomrock.dto.fileAllOrganizations;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Schema(description = "DTO para requisição de organizações")
public class OrganizationDTO {
    @Schema(description = "Nome da organização", example = "Empresa 1")
    private String name;
    @Schema(description = "Cnpj da organização", example = "99.205.190/0001-40")
    private String cnpj;
}
