package com.dataflow.apidomrock.controllers;

import com.dataflow.apidomrock.controllers.exceptions.CustomException;
import com.dataflow.apidomrock.dto.customresponse.ResponseCustomDTO;
import com.dataflow.apidomrock.dto.getmetadados.RequestBodyGetMetadadosDTO;
import com.dataflow.apidomrock.dto.getmetadados.ResponseBodyGetMetadadosDTO;
import com.dataflow.apidomrock.dto.getmetadados.ResponseCompleteGetMetadadosLandingDTO;
import com.dataflow.apidomrock.dto.processuploadcsv.RequetsUploadCSVDTO;
import com.dataflow.apidomrock.dto.processuploadcsv.ResponseUploadCSVDTO;
import com.dataflow.apidomrock.dto.updatemetados.RequestBodyUpdateMetaDTO;
import com.dataflow.apidomrock.entities.enums.Acao;
import com.dataflow.apidomrock.entities.enums.Estagio;
import com.dataflow.apidomrock.services.GlobalServices;
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

    @Autowired
    GlobalServices globalServices;
    @Autowired
    private LandingZoneService landingZoneService;

    //Metodo que é executado quando o client manda um POST no /landind/upload
    @PostMapping( "/upload")
    public ResponseEntity<ResponseCustomDTO<ResponseUploadCSVDTO>> processUploadCSV(@RequestParam("multipartFile") MultipartFile multipartFile, @RequestParam(required = false) String delimiter, @RequestParam(required = false) boolean header, @RequestParam String email) throws IOException, CustomException {
        System.out.println(email);
        ResponseUploadCSVDTO response = lzService.processUploadCSV(multipartFile, delimiter, header, email);
        return ResponseEntity.ok().body(new ResponseCustomDTO<>("Processamento efetuado com sucesso", response));
    }

    @PostMapping( "/upsert")
    public ResponseEntity<ResponseCustomDTO<String>> updateMetadadosInDataBase(@RequestBody RequestBodyUpdateMetaDTO requestBodyUpdateMetaDTO) throws CustomException {
        lzService.updateMetadadosInDatabase(requestBodyUpdateMetaDTO);
        return ResponseEntity.ok().body(new ResponseCustomDTO<>("Processamento efetuado com sucesso", null));
    }

    @PostMapping( "/search")
    public ResponseEntity<ResponseCustomDTO<ResponseCompleteGetMetadadosLandingDTO>> getMetadadosInDataBase(@RequestBody RequestBodyGetMetadadosDTO request) throws CustomException {
        ResponseBodyGetMetadadosDTO response = landingZoneService.getMetadados(request.getUsuario(), request.getNomeArquivo());

        String lastOBS = globalServices.getLastObs(response.getIdArquivo(), Estagio.B, Acao.REPROVAR);

        ResponseCompleteGetMetadadosLandingDTO retorno = new ResponseCompleteGetMetadadosLandingDTO(response, lastOBS);

        return ResponseEntity.ok().body(new ResponseCustomDTO<>("Processamento efetuado com sucesso", retorno));
    }
}