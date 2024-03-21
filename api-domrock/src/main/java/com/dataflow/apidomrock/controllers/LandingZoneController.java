package com.dataflow.apidomrock.controllers;

import com.dataflow.apidomrock.entities.database.Metadado;
import com.dataflow.apidomrock.entities.http.CustomResponseEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping(value = "/landing")
public class LandingZoneController {
    @PostMapping( "/upload")
    public ResponseEntity<CustomResponseEntity<List<Metadado>>> processUploadCSV(@RequestPart MultipartFile multipartFile){

        // TODO: processar o CSV

        return ResponseEntity.ok().body(null);
    }
}
