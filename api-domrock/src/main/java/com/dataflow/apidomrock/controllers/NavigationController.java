package com.dataflow.apidomrock.controllers;

//internal imports
import com.dataflow.apidomrock.controllers.exceptions.CustomException;
import com.dataflow.apidomrock.dto.customresponse.ResponseCustomDTO;
import com.dataflow.apidomrock.dto.showzones.RequestDadosDTO;
import com.dataflow.apidomrock.dto.showzones.ResponseNavigationDTO;
import com.dataflow.apidomrock.services.NavigationServices;
//spring imports
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
//swagger imports
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/navigation")
@CrossOrigin("*")
@Tag(name = "Navigation", description = "Operações de navegação entre zonas")
public class NavigationController {

    @Autowired
    NavigationServices navigationService;

    @PostMapping("/zones")
    @Operation(summary = "Busca zonas de acesso", method = "POST")
    public ResponseEntity<ResponseCustomDTO<ResponseNavigationDTO>> showZones(@RequestBody RequestDadosDTO request)
            throws CustomException {
        ResponseNavigationDTO response = navigationService.searchAndShowAccess(request);
        return ResponseEntity.ok().body(new ResponseCustomDTO<>("Processamento efeturado com sucesso", response));
    }
}
