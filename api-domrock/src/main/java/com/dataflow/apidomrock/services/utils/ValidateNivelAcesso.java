package com.dataflow.apidomrock.services.utils;

import com.dataflow.apidomrock.controllers.exceptions.CustomException;
import com.dataflow.apidomrock.entities.database.NivelAcesso;
import com.dataflow.apidomrock.repository.NivelAcessoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class ValidateNivelAcesso {
    @Autowired
    NivelAcessoRepository nivelAcessoRepository;
    @Transactional(rollbackFor = CustomException.class)
    public List<NivelAcesso> nivelAcessoList(List<String> lista) throws CustomException {
        List<NivelAcesso> retorno = new ArrayList<>();
        for (String nivelUsuario : lista ){
            Optional<NivelAcesso> nivelAcessoBD = nivelAcessoRepository.findByNivel(nivelUsuario);
            if (nivelAcessoBD.isEmpty()){
                throw new CustomException("O campo nivel deve ser preenchido", HttpStatus.NOT_FOUND);
            }
            retorno.add(nivelAcessoBD.get());
        }
        return retorno;
    }

}
