package com.dataflow.apidomrock.controllers;

import com.dataflow.apidomrock.dto.CustomResponseDTO;
import com.dataflow.apidomrock.dto.UploadCSVResponseDTO;
import com.dataflow.apidomrock.services.LandingZoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.IOException;

//Esta classe é o controller da landing zone
@RestController
@RequestMapping(value = "/landing")
public class LandingZoneController {

    //injecao de dependencia do service responsavel pela landing zone
    @Autowired
    LandingZoneService lzService;

    //Metodo que é executado quando o client manda um POST no /landind/upload
    @PostMapping( "/upload")
    public ResponseEntity<CustomResponseDTO<UploadCSVResponseDTO>> processUploadCSV(MultipartHttpServletRequest multipartFile, @RequestParam(required = false) String delimiter) throws IOException {
        UploadCSVResponseDTO response = lzService.processUploadCSV(multipartFile, delimiter);
        return ResponseEntity.ok().body(new CustomResponseDTO<>("Processamento efetuado com sucesso", response));
    }
}