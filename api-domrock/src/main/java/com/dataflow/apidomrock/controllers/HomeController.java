package com.dataflow.apidomrock.controllers;

import com.dataflow.apidomrock.dto.CustomResponseDTO;
import com.dataflow.apidomrock.dto.HomeResponseDTO;
import com.dataflow.apidomrock.services.HomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;

@RestController
@RequestMapping("/home")
public class HomeController {

    @Autowired
    HomeService homeService;

    @GetMapping
    public ResponseEntity<CustomResponseDTO<HomeResponseDTO>> homeDados(@RequestBody String emailUsuario) throws IOException {

        homeService.getUsuario("andre@teste");
        HomeResponseDTO response = new HomeResponseDTO(new ArrayList<>()) ;
        return ResponseEntity.ok().body(new CustomResponseDTO<>("Lista de dados:", response));
    }
}
