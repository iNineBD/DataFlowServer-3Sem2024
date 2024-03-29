package com.dataflow.apidomrock.controllers;

import com.dataflow.apidomrock.dto.ArquivoDTO;
import com.dataflow.apidomrock.dto.CustomResponseDTO;
import com.dataflow.apidomrock.dto.HomeResponseDTO;
import com.dataflow.apidomrock.dto.UsuarioDTO;
import com.dataflow.apidomrock.entities.database.Arquivo;
import com.dataflow.apidomrock.entities.database.Usuario;
import com.dataflow.apidomrock.services.HomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/home")
public class HomeController {
    @Autowired
    HomeService homeService;

    @GetMapping
    public ResponseEntity<CustomResponseDTO<HomeResponseDTO>> homeDados(@RequestBody UsuarioDTO usuario) throws IOException {
        List<Arquivo> arquivos =  homeService.getUsuario(usuario.email());
        List<ArquivoDTO> arquivosLanding = homeService.arquivosLanding(arquivos);
        List<ArquivoDTO> arquivosBronze = homeService.arquivosBronze(arquivos);
        List<ArquivoDTO> arquivosSilver = homeService.arquivosSilver(arquivos);

        HomeResponseDTO response = new HomeResponseDTO(arquivosLanding,arquivosBronze,arquivosSilver);
        return ResponseEntity.ok().body(new CustomResponseDTO<>("Lista de dados:", response));
    }
}
