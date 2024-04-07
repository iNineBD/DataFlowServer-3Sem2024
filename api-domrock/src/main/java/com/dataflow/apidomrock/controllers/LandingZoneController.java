package com.dataflow.apidomrock.controllers;

import com.dataflow.apidomrock.dto.customResponse.CustomResponseDTO;
import com.dataflow.apidomrock.dto.getMetadados.RequestBodyGetMetadadosDTO;
import com.dataflow.apidomrock.dto.getMetadados.ResponseBodyGetMetadadosDTO;
import com.dataflow.apidomrock.dto.saveMetadado.RequestBodySaveDTO;
import com.dataflow.apidomrock.dto.processUploadCSV.UploadCSVResponseDTO;
import com.dataflow.apidomrock.dto.updateMetados.RequestBodyUpdateMetaDTO;
import com.dataflow.apidomrock.services.LandingZoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

//Esta classe é o controller da landing zone
@RestController
@RequestMapping(value = "/landing")
@CrossOrigin("*")
public class LandingZoneController {

    //injecao de dependencia do service responsavel pela landing zone
    @Autowired
    LandingZoneService lzService;

    //Metodo que é executado quando o client manda um POST no /landind/upload
    @PostMapping( "/upload")
    public ResponseEntity<CustomResponseDTO<UploadCSVResponseDTO>> processUploadCSV(@RequestParam("multipartFile") MultipartFile multipartFile, @RequestParam(required = false) String delimiter) throws IOException {
        UploadCSVResponseDTO response = lzService.processUploadCSV(multipartFile, delimiter);
        return ResponseEntity.ok().body(new CustomResponseDTO<>("Processamento efetuado com sucesso", response));
    }

    @PostMapping( "/upsert")
    public ResponseEntity<CustomResponseDTO<String>> updateMetadadosInDataBase(@RequestBody RequestBodyUpdateMetaDTO requestBodyUpdateMetaDTO) throws IOException {
        lzService.updateMetadadosInDatabase(requestBodyUpdateMetaDTO);
        return ResponseEntity.ok().body(new CustomResponseDTO<>("Processamento efetuado com sucesso", null));
    }

    @PostMapping( "/search")
    public ResponseEntity<CustomResponseDTO<ResponseBodyGetMetadadosDTO>> getMetadadosInDataBase(@RequestBody RequestBodyGetMetadadosDTO request) throws IOException {
        ResponseBodyGetMetadadosDTO response = lzService.getMetadadosInDatabase(request.getUsuario(), request.getNomeArquivo());
        return ResponseEntity.ok().body(new CustomResponseDTO<>("Processamento efetuado com sucesso", response));
    }
}