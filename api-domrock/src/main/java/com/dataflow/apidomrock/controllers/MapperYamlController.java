package com.dataflow.apidomrock.controllers;

import com.dataflow.apidomrock.controllers.exceptions.CustomException;
import com.dataflow.apidomrock.dto.customresponse.ResponseCustomDTO;
import com.dataflow.apidomrock.dto.homedados.ResponseHomeDTO;
import com.dataflow.apidomrock.dto.mapperYaml.RequestMapperDTO;
import com.dataflow.apidomrock.services.MapperYAMLService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/mapper/yaml")
@CrossOrigin("*")
public class MapperYamlController {

    @Autowired
    MapperYAMLService mapperYAMLService;

    @PostMapping("/landing")
    public ResponseEntity<Resource>  mapperLandingYaml(@RequestBody RequestMapperDTO request) throws CustomException, JsonProcessingException {
        return mapperYAMLService.generateLandingYAML(request.nomeArquivo(), request.organizacao());
    }

    @PostMapping("/bronze")
    public ResponseEntity<Resource>  mapperBronzeYaml(@RequestBody RequestMapperDTO request) throws CustomException, JsonProcessingException {
        return mapperYAMLService.generateBronzeYAML(request.nomeArquivo(), request.organizacao());
    }

    @PostMapping("/silver")
    public ResponseEntity<Resource>  mapperSilverYaml(@RequestBody RequestMapperDTO request) throws CustomException, JsonProcessingException {
        return mapperYAMLService.generateSilverYAML(request.nomeArquivo(), request.organizacao());
    }
}
