package com.dataflow.apidomrock.dto.processuploadcsv;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "RequetsUploadCSVDTO", description = "Request DTO para realizar o upload de um arquivo CSV")
public record RequetsUploadCSVDTO(@Schema(description = "E-mail do usuário", example = "user@gmail.com") String email) {
}
