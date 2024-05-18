package com.dataflow.apidomrock.controllers;

import com.dataflow.apidomrock.controllers.exceptions.CustomException;
import com.dataflow.apidomrock.dto.customresponse.ResponseCustomDTO;
import com.dataflow.apidomrock.dto.editdepara.RequestEditDePara;
import com.dataflow.apidomrock.dto.excluirdepara.RequestExcluirDePara;
import com.dataflow.apidomrock.dto.gethash.ResponseHashToSilverDTO;
import com.dataflow.apidomrock.dto.gethash.ResponseNomeMetadataDTO;
import com.dataflow.apidomrock.dto.gethash.ResquestHashToSilverDTO;
import com.dataflow.apidomrock.dto.getmetadadostotepara.MetadadosDePara;
import com.dataflow.apidomrock.dto.getmetadadostotepara.RequestMetaToDePara;
import com.dataflow.apidomrock.dto.getmetadadostotepara.ResponseMetaToDePara;
import com.dataflow.apidomrock.dto.savedepara.RequestSaveDePara;
import com.dataflow.apidomrock.dto.setstatussz.RequestBodySetStatusSz;
import com.dataflow.apidomrock.dto.visualizeDePara.MetadadosDeParaVisualize;
import com.dataflow.apidomrock.dto.visualizeDePara.RequestDadosToDePara;
import com.dataflow.apidomrock.dto.visualizeDePara.ResponseDeParas;
import com.dataflow.apidomrock.services.SilverZoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/silver")
@CrossOrigin("*")
public class SilverZoneController {

    @Autowired
    SilverZoneService silverZoneService;

    @PostMapping( "/search")
    public ResponseEntity<ResponseCustomDTO<ResponseHashToSilverDTO>> getHash(@RequestBody ResquestHashToSilverDTO request) throws CustomException {
        List<ResponseNomeMetadataDTO> hash = silverZoneService.getMetadadosNoHash(request);
        ResponseHashToSilverDTO response = new ResponseHashToSilverDTO(request.nomeArquivo(),request.usuario(),hash);
        return ResponseEntity.ok().body(new ResponseCustomDTO<>("Processamento efetuado com sucesso", response));
    }

    @PutMapping("/validation")
    public ResponseEntity<ResponseCustomDTO<String>> setStatusSz(@RequestBody RequestBodySetStatusSz request) throws CustomException {
        silverZoneService.updateStatus(request);
        return ResponseEntity.ok().body(new ResponseCustomDTO<>("Processamento efetuado com sucesso", null));
    }

    @PostMapping("/create")
    public ResponseEntity<ResponseCustomDTO<ResponseMetaToDePara>> getMetadadosToDePara(@RequestBody RequestMetaToDePara request) throws CustomException {
        List<MetadadosDePara> metadadosNoDePara = silverZoneService.getMetadadosToDePara(request);
        ResponseMetaToDePara response = new ResponseMetaToDePara(request.email(),request.arquivo(),request.cnpj(),metadadosNoDePara);
        return ResponseEntity.ok().body(new ResponseCustomDTO<>("Processamento efetuado com sucesso",response));

    }

    @PostMapping("/save")
    public ResponseEntity<ResponseCustomDTO<String>> saveDePara(@RequestBody RequestSaveDePara request) throws CustomException {
        silverZoneService.saveDePara(request,false);
        return ResponseEntity.ok().body(new ResponseCustomDTO<>("Processamento efetuado com sucesso", null));
    }

    @PostMapping("/visualize")
    public ResponseEntity<ResponseCustomDTO<ResponseDeParas>> visualizeDePara(@RequestBody RequestDadosToDePara request) throws CustomException {
        List<MetadadosDeParaVisualize> metadadosNoDePara = silverZoneService.visualizeDePara(request);
        ResponseDeParas response = new ResponseDeParas(request.email(),request.arquivo(),request.cnpj(),metadadosNoDePara);
        return ResponseEntity.ok().body(new ResponseCustomDTO<>("Processamento efetuado com sucesso",response));
    }

    @PostMapping("/edit")
    public ResponseEntity<ResponseCustomDTO<String>> editDePara(@RequestBody RequestEditDePara request) throws CustomException {
        silverZoneService.editDePara(request);
        return ResponseEntity.ok().body(new ResponseCustomDTO<>("Processamento efetuado com sucesso", null));
    }

    @PostMapping("/excluir")
    public ResponseEntity<ResponseCustomDTO<String>> excluirDePara(@RequestBody RequestExcluirDePara request) throws CustomException {
        silverZoneService.excluirDePara(request);
        return ResponseEntity.ok().body(new ResponseCustomDTO<>("Processamento efetuado com sucesso", null));
    }

}
