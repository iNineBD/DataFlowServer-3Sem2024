package com.dataflow.apidomrock.controllers;

import com.dataflow.apidomrock.dto.UploadCSVResponseDTO;
import com.dataflow.apidomrock.entities.database.Metadata;
import com.dataflow.apidomrock.dto.CustomResponseDTO;
import com.dataflow.apidomrock.services.LandingZoneService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

//Esta classe é o controller da landing zone
@RestController
@RequestMapping(value = "/landing")
public class LandingZoneController {

    //injecao de dependencia do service responsavel pela landing zone
    LandingZoneService lzService = new LandingZoneService();

    //Metodo que é executado quando o client manda um POST no /landind/upload
    @PostMapping( "/upload")
    public ResponseEntity<CustomResponseDTO<UploadCSVResponseDTO>> processUploadCSV(
            @RequestPart MultipartFile multipartFile,
            @RequestParam(required = false) String delimiter){
        //invoca a func do service
        UploadCSVResponseDTO response = lzService.processUploadCSV(multipartFile, delimiter);
        return ResponseEntity.ok().body(new CustomResponseDTO<>("Processamento efetuado com sucesso", response));
    }
}