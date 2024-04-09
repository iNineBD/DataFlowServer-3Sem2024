package com.dataflow.apidomrock.services;

import com.dataflow.apidomrock.dto.ArquivosLzBS.ArquivoDTO;
import com.dataflow.apidomrock.entities.database.Arquivo;
import com.dataflow.apidomrock.entities.database.Usuario;
import com.dataflow.apidomrock.repository.ArquivoRepository;
import com.dataflow.apidomrock.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class HomeService {

    @Autowired
    private ArquivoRepository arquivoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<Arquivo> getUsuario(String emailUsuario){
        Optional<Usuario> usuario = usuarioRepository.findById(emailUsuario);
        if(usuario.isPresent()){
            String organizacao = usuario.get().getOrganizacao().getNome();

            return arquivoRepository.findByOrganizacao(organizacao);

        }else{
            throw new RuntimeException("Usuário não cadastrado");
        }
    }

    public List<ArquivoDTO> arquivosLanding(List<Arquivo> arquivos){
        int qtdArquivos = arquivos.size();
        List<Arquivo> arq = new ArrayList<>();

        for (int i = 0; i < qtdArquivos; i++) {
            if (arquivos.get(i).getStatus().getId() == 1 || arquivos.get(i).getStatus().getId() == 98) {
                arq.add(arquivos.get(i));
            }
        }

        List<ArquivoDTO> arquivosLz = arq.stream()
                .map(ArquivoDTO::new)
                .collect(Collectors.toList());

        return arquivosLz;
    }

    public List<ArquivoDTO> arquivosBronze(List<Arquivo> arquivos){
        int qtdArquivos = arquivos.size();
        List<Arquivo> arq = new ArrayList<>();

        for (int i = 0; i < qtdArquivos; i++){
            if(arquivos.get(i).getStatus().getId() == 2 || arquivos.get(i).getStatus().getId() == 3 || arquivos.get(i).getStatus().getId() == 99){
                arq.add(arquivos.get(i));
            }
        }

        List<ArquivoDTO> arquivosBz = arq.stream()
                .map(ArquivoDTO::new)
                .collect(Collectors.toList());

        return arquivosBz;
    }

    public List<ArquivoDTO> arquivosSilver(List<Arquivo> arquivos){
        int qtdArquivos = arquivos.size();
        List<Arquivo> arq = new ArrayList<>();

        for (int i = 0; i < qtdArquivos; i++){
            if(arquivos.get(i).getStatus().getId() == 4 || arquivos.get(i).getStatus().getId() == 5){
                arq.add(arquivos.get(i));
            }
        }

        List<ArquivoDTO> arquivosSz = arq.stream()
                .map(ArquivoDTO::new)
                .collect(Collectors.toList());

        return arquivosSz;
    }

}
