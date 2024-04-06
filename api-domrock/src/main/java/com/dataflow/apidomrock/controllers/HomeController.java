package com.dataflow.apidomrock.controllers;

import com.dataflow.apidomrock.dto.ArquivosLzBS.ArquivoDTO;
import com.dataflow.apidomrock.dto.CustomResponseDTO;
import com.dataflow.apidomrock.dto.HomeResponseDTO;
import com.dataflow.apidomrock.dto.GetArquivosUsuario.UsuarioDTO;
import com.dataflow.apidomrock.entities.database.Arquivo;
import com.dataflow.apidomrock.services.HomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/home")
@CrossOrigin
public class HomeController {
    @Autowired
    HomeService homeService;

    @PostMapping("/filesOrg")
    public ResponseEntity<CustomResponseDTO<HomeResponseDTO>> homeDados(@RequestBody UsuarioDTO usuario) throws IOException {
        String nivel = homeService.getNivel(usuario.email());
        List<Arquivo> arquivos =  homeService.getArquivosUsuario(usuario.email());
        List<ArquivoDTO> arquivosLanding = homeService.arquivosLanding(nivel,arquivos);
        List<ArquivoDTO> arquivosBronze = homeService.arquivosBronze(nivel, arquivos);
        List<ArquivoDTO> arquivosSilver = homeService.arquivosSilver(nivel, arquivos);

        HomeResponseDTO response = new HomeResponseDTO(nivel,arquivosLanding,arquivosBronze,arquivosSilver);
        return ResponseEntity.ok().body(new CustomResponseDTO<>("Processamento efetuado com sucesso", response));
    }
}
