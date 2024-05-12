package com.dataflow.apidomrock.controllers;

import com.dataflow.apidomrock.controllers.exceptions.CustomException;
import com.dataflow.apidomrock.dto.customresponse.ResponseCustomDTO;
import com.dataflow.apidomrock.dto.gethash.ResponseHashToSilverDTO;
import com.dataflow.apidomrock.dto.gethash.ResponseNomeMetadataDTO;
import com.dataflow.apidomrock.dto.gethash.ResquestHashToSilverDTO;
import com.dataflow.apidomrock.dto.setstatussz.RequestBodySetStatusSz;
import com.dataflow.apidomrock.services.SilverZoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/silver")
@CrossOrigin("*")
public class SilverZoneController {

    @Autowired
    SilverZoneService silverZoneService;

    @PostMapping( "/search")
    public ResponseEntity<ResponseCustomDTO<ResponseHashToSilverDTO>> getHash(@RequestBody ResquestHashToSilverDTO request) throws CustomException {
        List<ResponseNomeMetadataDTO> hash = silverZoneService.getMetadadosNoHash(request);
        ResponseHashToSilverDTO response = new ResponseHashToSilverDTO(request.nomeArquivo(),request.usuario(),hash);
        return ResponseEntity.ok().body(new ResponseCustomDTO<>("Processamento efetuado com sucesso", response));
    }

    @PutMapping("/validation")
    public ResponseEntity<ResponseCustomDTO<String>> setStatusSz(@RequestBody RequestBodySetStatusSz request) throws CustomException {
        silverZoneService.updateStatus(request);
        return ResponseEntity.ok().body(new ResponseCustomDTO<>("Processamento efetuado com sucesso", null));
    }

}
