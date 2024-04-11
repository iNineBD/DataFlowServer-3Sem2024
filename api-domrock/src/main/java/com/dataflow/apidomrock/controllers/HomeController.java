package com.dataflow.apidomrock.controllers;

import com.dataflow.apidomrock.dto.customresponse.ResponseCustomDTO;
import com.dataflow.apidomrock.dto.homedados.RequestBodyUsuarioDTO;
import com.dataflow.apidomrock.dto.homedados.ResponseArquivosDTO;
import com.dataflow.apidomrock.dto.homedados.ResponseHomeDTO;
import com.dataflow.apidomrock.entities.database.Arquivo;
import com.dataflow.apidomrock.services.HomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/home")
public class HomeController {
    @Autowired
    HomeService homeService;

    @PostMapping("/files/find")
    public ResponseEntity<ResponseCustomDTO<ResponseHomeDTO>> homeDados(@RequestBody RequestBodyUsuarioDTO usuario) throws IOException {
        String nivel = homeService.getNivel(usuario.email());
        List<Arquivo> arquivos =  homeService.getArquivosUsuario(usuario.email());
        List<ResponseArquivosDTO> arquivosLanding = homeService.arquivosLanding(nivel,arquivos);
        List<ResponseArquivosDTO> arquivosBronze = homeService.arquivosBronze(nivel, arquivos);
        List<ResponseArquivosDTO> arquivosSilver = homeService.arquivosSilver(nivel, arquivos);

        ResponseHomeDTO response = new ResponseHomeDTO(nivel,arquivosLanding,arquivosBronze,arquivosSilver);
        return ResponseEntity.ok().body(new ResponseCustomDTO<>("Processamento efetuado com sucesso", response));
    }
}
