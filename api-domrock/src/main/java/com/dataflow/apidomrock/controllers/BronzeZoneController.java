package com.dataflow.apidomrock.controllers;

import com.dataflow.apidomrock.controllers.exceptions.CustomException;
import com.dataflow.apidomrock.dto.createHash.RequestArquivoDTO;
import com.dataflow.apidomrock.dto.createHash.ResponseMetaDTO;
import com.dataflow.apidomrock.dto.createHash.ResponseMetadadoDTO;
import com.dataflow.apidomrock.dto.customresponse.ResponseCustomDTO;
import com.dataflow.apidomrock.dto.getmetadados.RequestBodyGetMetadadosDTO;
import com.dataflow.apidomrock.dto.getmetadados.ResponseBodyGetMetadadosDTO;
import com.dataflow.apidomrock.dto.savehash.RequestHashDTO;
import com.dataflow.apidomrock.dto.setstatusbz.RequestBodySetStatusBzDTO;
import com.dataflow.apidomrock.services.BronzeZoneService;
import com.dataflow.apidomrock.services.GlobalServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bronze")
@CrossOrigin
public class BronzeZoneController {

    @Autowired
    GlobalServices globalServices;

    @Autowired
    BronzeZoneService bronzeZoneService;

    @PostMapping( "/search")
    public ResponseEntity<ResponseCustomDTO<ResponseBodyGetMetadadosDTO>> getMetadadosInDataBase(@RequestBody RequestBodyGetMetadadosDTO request) throws CustomException {
        ResponseBodyGetMetadadosDTO response = globalServices.getMetadadosInDatabase(request.getUsuario(), request.getNomeArquivo());
        return ResponseEntity.ok().body(new ResponseCustomDTO<>("Processamento efetuado com sucesso", response));
    }

    @PutMapping("/validation")
    public ResponseEntity<ResponseCustomDTO<String>> setStatusBz(@RequestBody RequestBodySetStatusBzDTO request) throws CustomException {
        bronzeZoneService.updateStatus(request);
        return ResponseEntity.ok().body(new ResponseCustomDTO<>("Processamento efetuado com sucesso", null));
    }

    @PostMapping("/createHash")
    public ResponseEntity<ResponseCustomDTO<ResponseMetadadoDTO>> createHash(@RequestBody RequestArquivoDTO request) throws  CustomException{
        List<ResponseMetaDTO> metadados = bronzeZoneService.createHash(request);
        ResponseMetadadoDTO response = new ResponseMetadadoDTO(metadados);
        return ResponseEntity.ok().body(new ResponseCustomDTO<>("Processamento efetuado com sucesso",response));
    }

    @PostMapping("/saveHash")
    public ResponseEntity<ResponseCustomDTO<String>> saveHash(@RequestBody RequestHashDTO request) throws CustomException {
        bronzeZoneService.save(request);
        return ResponseEntity.ok().body(new ResponseCustomDTO<>("Processamento efetuado com sucesso",null));
    }
}
