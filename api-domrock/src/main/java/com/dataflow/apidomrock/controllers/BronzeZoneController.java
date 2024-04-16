package com.dataflow.apidomrock.controllers;

import com.dataflow.apidomrock.controllers.exceptions.CustomException;
import com.dataflow.apidomrock.dto.customresponse.ResponseCustomDTO;
import com.dataflow.apidomrock.dto.getmetadados.RequestBodyGetMetadadosDTO;
import com.dataflow.apidomrock.dto.getmetadados.ResponseBodyGetMetadadosDTO;
import com.dataflow.apidomrock.dto.setstatusbz.RequestBodySetStatusBzDTO;
import com.dataflow.apidomrock.services.GlobalServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bronze")
@CrossOrigin
public class BronzeZoneController {

    @Autowired
    GlobalServices globalServices;

    @PostMapping( "/search")
    public ResponseEntity<ResponseCustomDTO<ResponseBodyGetMetadadosDTO>> getMetadadosInDataBase(@RequestBody RequestBodyGetMetadadosDTO request) throws CustomException {
        ResponseBodyGetMetadadosDTO response = globalServices.getMetadadosInDatabase(request.getUsuario(), request.getNomeArquivo());
        return ResponseEntity.ok().body(new ResponseCustomDTO<>("Processamento efetuado com sucesso", response));
    }

    @PutMapping("/save")
    public ResponseEntity<ResponseSaveBronzeDTO> setStatusBz(@RequestBody RequestBodySetStatusBzDTO){

    }
}
