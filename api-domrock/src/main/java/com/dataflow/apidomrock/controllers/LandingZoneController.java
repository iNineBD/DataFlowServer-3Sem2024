package com.dataflow.apidomrock.controllers;

//internal imports
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.dataflow.apidomrock.controllers.exceptions.CustomException;
import com.dataflow.apidomrock.dto.customresponse.ResponseCustomDTO;
import com.dataflow.apidomrock.dto.getmetadados.RequestBodyGetMetadadosDTO;
import com.dataflow.apidomrock.dto.getmetadados.ResponseBodyGetMetadadosDTO;
import com.dataflow.apidomrock.dto.getmetadados.ResponseCompleteGetMetadadosLandingDTO;
import com.dataflow.apidomrock.dto.processuploadcsv.ResponseUploadCSVDTO;
import com.dataflow.apidomrock.dto.updatemetados.RequestBodyUpdateMetaDTO;
import com.dataflow.apidomrock.entities.enums.Acao;
import com.dataflow.apidomrock.entities.enums.Estagio;
import com.dataflow.apidomrock.services.GlobalServices;
import com.dataflow.apidomrock.services.LandingZoneService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

//Esta classe é o controller da landing zone
@RestController
@RequestMapping(value = "/landing", consumes = "application/json", produces = "application/json")
@CrossOrigin("*")
@Tag(name = "LandingZone", description = "Operações de manipulação de arquivos na landing zone")
public class LandingZoneController {

    // injecao de dependencia do service responsavel pela landing zone
    @Autowired
    LandingZoneService lzService;

    @Autowired
    GlobalServices globalServices;
    @Autowired
    private LandingZoneService landingZoneService;

    // Metodo que é executado quando o client manda um POST no /landind/upload
    @Operation(summary = "Processa upload de arquivo CSV", method = "POST")
    @ApiDefaultResponses
    @PostMapping(value ="/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ResponseCustomDTO<ResponseUploadCSVDTO>> processUploadCSV(
            @RequestParam("multipartFile") MultipartFile multipartFile,
            @RequestParam(required = false) String delimiter, @RequestParam(required = false) boolean header,
            @RequestParam String email) throws IOException, CustomException {
        System.out.println(email);
        ResponseUploadCSVDTO response = lzService.processUploadCSV(multipartFile, delimiter, header, email);
        return ResponseEntity.ok().body(new ResponseCustomDTO<>("Processamento efetuado com sucesso", response));
    }

    @Operation(summary = "Busca metadados no banco de dados", method = "POST")
    @ApiDefaultResponses
    @PostMapping("/upsert")
    public ResponseEntity<ResponseCustomDTO<String>> updateMetadadosInDataBase(
            @RequestBody RequestBodyUpdateMetaDTO requestBodyUpdateMetaDTO) throws CustomException {
        lzService.updateMetadadosInDatabase(requestBodyUpdateMetaDTO);
        return ResponseEntity.ok().body(new ResponseCustomDTO<>("Processamento efetuado com sucesso", null));
    }

    @PostMapping("/search")
    public ResponseEntity<ResponseCustomDTO<ResponseCompleteGetMetadadosLandingDTO>> getMetadadosInDataBase(
            @RequestBody RequestBodyGetMetadadosDTO request) throws CustomException {
        ResponseBodyGetMetadadosDTO response = landingZoneService.getMetadados(request.getUsuario(),
                request.getNomeArquivo(), request.getFileCnpj());

        String lastOBS = globalServices.getLastObs(response.getIdArquivo(), Estagio.B, Acao.REPROVAR);

        ResponseCompleteGetMetadadosLandingDTO retorno = new ResponseCompleteGetMetadadosLandingDTO(response, lastOBS);

        return ResponseEntity.ok().body(new ResponseCustomDTO<>("Processamento efetuado com sucesso", retorno));
    }
}