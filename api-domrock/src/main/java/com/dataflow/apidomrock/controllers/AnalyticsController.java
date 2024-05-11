package com.dataflow.apidomrock.controllers;

import com.dataflow.apidomrock.dto.fileAnalytics.RequestFileAnalyticDTO;
import com.dataflow.apidomrock.dto.customresponse.ResponseCustomDTO;
import com.dataflow.apidomrock.dto.fileAnalytics.ResponseFileAnalyticDTO;
import com.dataflow.apidomrock.dto.getmetadados.ResponseBodyGetMetadadosDTO;
import com.dataflow.apidomrock.services.AnalyticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@CrossOrigin("*")
@RequestMapping(value = "/analytics")
public class AnalyticsController {

    @Autowired
    private AnalyticsService analyticsService;

    @PostMapping("/files")
    public ResponseEntity<ResponseCustomDTO<ResponseFileAnalyticDTO>> fileAnalytc(@RequestBody RequestFileAnalyticDTO request){

        ResponseFileAnalyticDTO response = analyticsService.fileAnalytc(request.getFileName(), request.getOrgName());

        return ResponseEntity.ok().body(new ResponseCustomDTO<>("Processamento efetuado com sucesso", response));

    }
}
