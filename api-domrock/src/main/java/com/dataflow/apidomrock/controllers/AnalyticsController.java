package com.dataflow.apidomrock.controllers;

import com.dataflow.apidomrock.controllers.exceptions.CustomException;
import com.dataflow.apidomrock.dto.fileAllOrganizations.OrganizationDTO;
import com.dataflow.apidomrock.dto.fileAnalytics.RequestFileAnalyticDTO;
import com.dataflow.apidomrock.dto.customresponse.ResponseCustomDTO;
import com.dataflow.apidomrock.dto.fileAnalytics.ResponseFileAnalyticDTO;
import com.dataflow.apidomrock.services.AnalyticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@CrossOrigin("*")
@RequestMapping(value = "/analytics")
public class AnalyticsController {

    @Autowired
    private AnalyticsService analyticsService;

    @PostMapping("/files")
    public ResponseEntity<ResponseCustomDTO<ResponseFileAnalyticDTO>> fileAnalytc(@RequestBody RequestFileAnalyticDTO request) throws CustomException {
        return ResponseEntity.ok().body(new ResponseCustomDTO<>("Processamento efetuado com sucesso", analyticsService.fileAnalytc(request.getSearchType(), request.getCnpj())));
    }

    @GetMapping("/organizations")
    public ResponseEntity<ResponseCustomDTO<List<OrganizationDTO>>> fileAllOrganizations() throws CustomException {
        return ResponseEntity.ok().body(new ResponseCustomDTO<>("Processamento efetuado com sucesso", analyticsService.findAllOrganizations()));
    }
}
