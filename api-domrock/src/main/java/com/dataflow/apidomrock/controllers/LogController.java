
package com.dataflow.apidomrock.controllers;

import com.dataflow.apidomrock.controllers.exceptions.CustomException;
import com.dataflow.apidomrock.dto.customresponse.ResponseCustomDTO;
import com.dataflow.apidomrock.dto.log.RequestLogUsuario;
import com.dataflow.apidomrock.dto.log.ResponseLog;
import com.dataflow.apidomrock.dto.log.RequestLogDTO;
import com.dataflow.apidomrock.dto.log.ResponseLogUsuario;
import com.dataflow.apidomrock.services.utils.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/log")
@CrossOrigin("*")
public class LogController {
    @Autowired
    Logger logger;
    @PostMapping("/visualizar")
    public ResponseEntity<ResponseCustomDTO<List<ResponseLog>>>visualizarLog(@RequestBody RequestLogDTO requestLogDTO)throws CustomException{
        List<ResponseLog> log = logger.visualizarLog(requestLogDTO);
        return ResponseEntity.ok().body(new ResponseCustomDTO<>("Processamento efetuado com sucesso", log));
    }

    @PostMapping("/usuario")
    public  ResponseEntity<ResponseCustomDTO<List<ResponseLogUsuario>>> visualizarLogUsuario (@RequestBody RequestLogUsuario requestLogUsuario) throws CustomException{
        List<ResponseLogUsuario> logUsuario = logger.visualizarLogUsuario(requestLogUsuario);
        return ResponseEntity.ok().body(new ResponseCustomDTO<>("Processamento efetuado com sucesso", logUsuario));
    }
}