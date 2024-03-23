package com.dataflow.apidomrock.controllers;

import com.dataflow.apidomrock.entities.database.Metadata;
import com.dataflow.apidomrock.entities.http.responses.CustomResponseEntity;
import com.dataflow.apidomrock.services.LandingZoneService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping(value = "/landing")
public class LandingZoneController {
    LandingZoneService lzService = new LandingZoneService();
    @PostMapping( "/upload")
    public ResponseEntity<CustomResponseEntity<List<Metadata>>> processUploadCSV(
            @RequestPart MultipartFile multipartFile,
            @RequestParam(required = false) String delimiter){
        List<Metadata> listMetadata = lzService.processUploadCSV(multipartFile, delimiter);
        return ResponseEntity.ok().body(new CustomResponseEntity<>("Processamento efetuado com sucesso", listMetadata));
    }
}