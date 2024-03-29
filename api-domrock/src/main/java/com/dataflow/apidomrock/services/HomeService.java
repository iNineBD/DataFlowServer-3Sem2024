package com.dataflow.apidomrock.services;

import com.dataflow.apidomrock.dto.HomeResponseDTO;
import com.dataflow.apidomrock.entities.database.Usuario;
import com.dataflow.apidomrock.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class HomeService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public void getUsuario(String emailUsuario){
        Optional<Usuario> usuario = usuarioRepository.findById(emailUsuario);
        if(usuario.isPresent()){
//            HomeResponseDTO responseToHome = usuarioRepository.
        }else{
            throw new RuntimeException("Usuário não está cadastrado");
        }
    }



}
