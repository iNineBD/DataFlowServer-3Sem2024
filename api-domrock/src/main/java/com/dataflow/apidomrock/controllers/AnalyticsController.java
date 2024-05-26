package com.dataflow.apidomrock.controllers;

// internal imports
import com.dataflow.apidomrock.controllers.exceptions.CustomException;
import com.dataflow.apidomrock.dto.fileAllOrganizations.OrganizationDTO;
import com.dataflow.apidomrock.dto.fileAnalytics.MetricsFilesDTO;
import com.dataflow.apidomrock.dto.fileAnalytics.MetricsUsersDTO;
import com.dataflow.apidomrock.dto.fileAnalytics.RequestFileAnalyticDTO;
import com.dataflow.apidomrock.dto.customresponse.ResponseCustomDTO;
import com.dataflow.apidomrock.dto.fileAnalytics.ResponseFileAnalyticDTO;
import com.dataflow.apidomrock.services.AnalyticsService;
// spring imports
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
// Swagger imports
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
// Java imports
import java.util.List;

@Controller
@CrossOrigin("*")
@RequestMapping("/analytics")
@Tag(name = "Analytics", description = "Operações de análise de dados")
public class AnalyticsController {

    @Autowired
    private AnalyticsService analyticsService;

    @Operation(summary = "Analisa e processa arquivos", method = "POST")
    @ApiDefaultResponses
    @PostMapping("/files")
    public ResponseEntity<ResponseCustomDTO<ResponseFileAnalyticDTO<MetricsFilesDTO>>> fileAnalytc(
            @RequestBody RequestFileAnalyticDTO request) throws CustomException {
        return ResponseEntity.ok().body(new ResponseCustomDTO<>("Processamento efetuado com sucesso",
                analyticsService.fileAnalytc(request.getSearchType(), request.getCnpj())));
    }

    @Operation(summary = "Encontra e retorna todos os usuários", method = "GET")
    @ApiDefaultResponses
    @GetMapping("/users")
    public ResponseEntity<ResponseCustomDTO<ResponseFileAnalyticDTO<MetricsUsersDTO>>> findAllUsers()
            throws CustomException {
        return ResponseEntity.ok().body(new ResponseCustomDTO<>("Processamento efetuado com sucesso",
                new ResponseFileAnalyticDTO<>(analyticsService.findAllUsers())));
    }

    @Operation(summary = "Encontra e retorna todas as organizações", method = "GET")
    @ApiDefaultResponses
    @GetMapping("/organizations")
    public ResponseEntity<ResponseCustomDTO<List<OrganizationDTO>>> fileAllOrganizations() throws CustomException {
        return ResponseEntity.ok().body(
                new ResponseCustomDTO<>("Processamento efetuado com sucesso", analyticsService.findAllOrganizations()));
    }
}
