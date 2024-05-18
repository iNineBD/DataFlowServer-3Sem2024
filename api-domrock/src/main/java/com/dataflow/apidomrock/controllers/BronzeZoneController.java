package com.dataflow.apidomrock.controllers;

import com.dataflow.apidomrock.controllers.exceptions.CustomException;
import com.dataflow.apidomrock.dto.createHash.RequestArquivoDTO;
import com.dataflow.apidomrock.dto.createHash.ResponseMetaDTO;
import com.dataflow.apidomrock.dto.createHash.ResponseMetadadoDTO;
import com.dataflow.apidomrock.dto.customresponse.ResponseCustomDTO;
import com.dataflow.apidomrock.dto.editarhash.RequestEditHashDTO;
import com.dataflow.apidomrock.dto.getmetadados.RequestBodyGetMetadadosDTO;
import com.dataflow.apidomrock.dto.getmetadados.ResponseBodyGetMetadadosDTO;
import com.dataflow.apidomrock.dto.savehash.RequestHashDTO;
import com.dataflow.apidomrock.dto.savehash.RequestMetadadoDTO;
import com.dataflow.apidomrock.dto.setstatusbz.RequestBodySetStatusBzDTO;
import com.dataflow.apidomrock.dto.visualizeHash.RequestVisualizeHashDTO;
import com.dataflow.apidomrock.dto.visualizeHash.ResponseHashDTO;
import com.dataflow.apidomrock.services.BronzeZoneService;
import com.dataflow.apidomrock.services.GlobalServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bronze")
@CrossOrigin("*")
public class BronzeZoneController {

    @Autowired
    GlobalServices globalServices;

    @Autowired
    BronzeZoneService bronzeZoneService;

//    @PostMapping( "/search")
//    public ResponseEntity<ResponseCustomDTO<ResponseBodyGetMetadadosDTO>> getMetadadosInDataBase(@RequestBody RequestBodyGetMetadadosDTO request) throws CustomException {
//        ResponseBodyGetMetadadosDTO response = globalServices.getMetadadosInDatabase(request.getUsuario(), request.getNomeArquivo(), request.getFileCnpj());
//        return ResponseEntity.ok().body(new ResponseCustomDTO<>("Processamento efetuado com sucesso", response));
//    }

    @PutMapping("/validation")
    public ResponseEntity<ResponseCustomDTO<String>> setStatusBz(@RequestBody RequestBodySetStatusBzDTO request) throws CustomException {
        bronzeZoneService.updateStatus(request);
        return ResponseEntity.ok().body(new ResponseCustomDTO<>("Processamento efetuado com sucesso", null));
    }

    @PostMapping("/createHash")
    public ResponseEntity<ResponseCustomDTO<ResponseMetadadoDTO>> createHash(@RequestBody RequestArquivoDTO request) throws  CustomException{
        List<ResponseMetaDTO> metadados = bronzeZoneService.selectMetadadosToHash(request);
        ResponseMetadadoDTO response = new ResponseMetadadoDTO(metadados);
        return ResponseEntity.ok().body(new ResponseCustomDTO<>("Processamento efetuado com sucesso",response));
    }

    @PostMapping("/saveHash")
    public ResponseEntity<ResponseCustomDTO<String>> saveHash(@RequestBody RequestHashDTO request) throws CustomException {
        bronzeZoneService.save(request,false);
        return ResponseEntity.ok().body(new ResponseCustomDTO<>("Processamento efetuado com sucesso",null));
    }

    @PostMapping("/visualize")
    public ResponseEntity<ResponseCustomDTO<ResponseHashDTO>> visualizeHash(@RequestBody RequestVisualizeHashDTO request) throws CustomException {
        List<RequestMetadadoDTO> metadadosHash = bronzeZoneService.visualizeHash(request);
        List<RequestMetadadoDTO> metadadosForaDoHash = bronzeZoneService.visualizeMetadadosForaHash(request);
        String observacao = bronzeZoneService.visualzeObs(request);
        ResponseHashDTO response = new ResponseHashDTO(request.nomeArquivo(), request.usuario(), metadadosHash,metadadosForaDoHash,observacao);
        return ResponseEntity.ok().body(new ResponseCustomDTO<>("Processamento efetuado com sucesso",response));
    }

    @PostMapping("/editHash")
    public ResponseEntity<ResponseCustomDTO<String>> editarHash(@RequestBody RequestEditHashDTO request) throws CustomException{
        bronzeZoneService.editHash(request);
        return ResponseEntity.ok().body(new ResponseCustomDTO<>("Processamento efetuado com sucesso",null));
    }
}
