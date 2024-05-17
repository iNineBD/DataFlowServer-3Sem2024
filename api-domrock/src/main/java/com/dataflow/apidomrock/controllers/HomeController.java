package com.dataflow.apidomrock.controllers;

//internal imports
import com.dataflow.apidomrock.controllers.exceptions.CustomException;
import com.dataflow.apidomrock.dto.customresponse.ResponseCustomDTO;
import com.dataflow.apidomrock.dto.deleteFile.RequestBodyDeleteFileDTO;
import com.dataflow.apidomrock.dto.homedados.RequestBodyUsuarioDTO;
import com.dataflow.apidomrock.dto.homedados.ResponseArquivosDTO;
import com.dataflow.apidomrock.dto.homedados.ResponseHomeDTO;
import com.dataflow.apidomrock.entities.database.Arquivo;
import com.dataflow.apidomrock.services.HomeService;
// spring imports
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
//java imports
import java.io.IOException;
import java.util.List;

//swagger imports
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping(value = "/home", produces = "application/json", consumes = "application/json")
@CrossOrigin("*")
@Tag(name = "Home", description = "Retorna os arquivos do usuário")
public class HomeController {
    @Autowired
    HomeService homeService;

    @PostMapping("/files/find")
    @Operation(summary = "Busca arquivos do usuário", method = "POST")
    @ApiDefaultResponses
    public ResponseEntity<ResponseCustomDTO<ResponseHomeDTO>> homeDados(@RequestBody RequestBodyUsuarioDTO usuario)
            throws IOException {
        List<Arquivo> arquivosFiltrados = homeService.getArquivosUsuario(usuario.email());
        List<ResponseArquivosDTO> arquivos = homeService.arquivosHome(arquivosFiltrados);

        ResponseHomeDTO response = new ResponseHomeDTO(arquivos);
        return ResponseEntity.ok().body(new ResponseCustomDTO<>("Processamento efetuado com sucesso", response));
    }

    @DeleteMapping("/files")
    @Operation(summary = "Deleta um arquivo", method = "DELETE")
    @ApiDefaultResponses
    public ResponseEntity<ResponseCustomDTO<String>> deleteFile(@RequestBody RequestBodyDeleteFileDTO request)
            throws CustomException {
        homeService.deleteFile(request);
        return ResponseEntity.ok().body(new ResponseCustomDTO<>("Processamento efetuado com sucesso", null));
    }
}
