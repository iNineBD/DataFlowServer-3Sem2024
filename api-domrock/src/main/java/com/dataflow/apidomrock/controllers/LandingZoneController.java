package com.dataflow.apidomrock.controllers;

import com.dataflow.apidomrock.dto.CustomResponseDTO;
import com.dataflow.apidomrock.dto.SaveMetadado.RequestBodySaveDTO;
import com.dataflow.apidomrock.dto.UploadCSVResponseDTO;
import com.dataflow.apidomrock.services.LandingZoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.IOException;

//Esta classe é o controller da landing zone
@RestController
@RequestMapping(value = "/landing")
@CrossOrigin("*")
public class LandingZoneController {

    //injecao de dependencia do service responsavel pela landing zone
    @Autowired
    LandingZoneService lzService;

    @GetMapping
    public ResponseEntity<CustomResponseDTO<String>> healthCheck (){
        return ResponseEntity.ok().body(new CustomResponseDTO<>("A API está em pé", null));
    }

    //Metodo que é executado quando o client manda um POST no /landind/upload
    @PostMapping( "/upload")
    public ResponseEntity<CustomResponseDTO<UploadCSVResponseDTO>> processUploadCSV(@RequestParam("multipartFile") MultipartFile multipartFile, @RequestParam(required = false) String delimiter) throws IOException {
        UploadCSVResponseDTO response = lzService.processUploadCSV(multipartFile, delimiter);
        return ResponseEntity.ok().body(new CustomResponseDTO<>("Processamento efetuado com sucesso", response));
    }


    @PostMapping( "/save")
    public ResponseEntity<CustomResponseDTO<String>> saveMetadadosInDataBase(@RequestBody RequestBodySaveDTO requestBodySaveDTO) throws IOException {
        lzService.saveMetadadosInDataBase(requestBodySaveDTO);
        return ResponseEntity.ok().body(new CustomResponseDTO<>("Processamento efetuado com sucesso", null));
    }
}