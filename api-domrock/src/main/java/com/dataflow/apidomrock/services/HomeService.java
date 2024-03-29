package com.dataflow.apidomrock.services;

import com.dataflow.apidomrock.dto.HomeResponseDTO;
import com.dataflow.apidomrock.entities.database.Arquivo;
import com.dataflow.apidomrock.entities.database.Organizacao;
import com.dataflow.apidomrock.entities.database.Usuario;
import com.dataflow.apidomrock.repository.ArquivoRepository;
import com.dataflow.apidomrock.repository.UsuarioRepository;
import org.aspectj.weaver.ast.Or;
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
            List<String> organizacao = usuario.stream()
                    .map(Usuario::getOrganizacao)
                    .map(Organizacao::getNome)
                    .collect(Collectors.toList());

            List<Arquivo> arquivos = arquivoRepository.findByOrganizacao(organizacao.get(0));

            return arquivos;

        }else{
            throw new RuntimeException("Usuário não cadastrado");
        }
    }

    public List<Arquivo> arquivosLanding(List<Arquivo> arquivos){
        int qtdArquivos = arquivos.size();
        List<Arquivo> arquivosLz = new ArrayList<>();

        for (int i = 0; i < qtdArquivos; i++){
            if(arquivos.get(i).getStatus().getId() == 1 || arquivos.get(i).getStatus().getId()== 2 || arquivos.get(i).getStatus().getId() == 98){
                arquivosLz.add(arquivos.get(i));
            }
        }

        return arquivosLz;
    }

    public List<Arquivo> arquivosBronze(List<Arquivo> arquivos){
        int qtdArquivos = arquivos.size();
        List<Arquivo> arquivosBz = new ArrayList<>();

        for (int i = 0; i < qtdArquivos; i++){
            if(arquivos.get(i).getStatus().getId() == 3 || arquivos.get(i).getStatus().getId() == 4 || arquivos.get(i).getStatus().getId() == 99){
                arquivosBz.add(arquivos.get(i));
            }
        }

        return arquivosBz;
    }

    public List<Arquivo> arquivosSilver(List<Arquivo> arquivos){
        int qtdArquivos = arquivos.size();
        List<Arquivo> arquivosSz = new ArrayList<>();

        for (int i = 0; i < qtdArquivos; i++){
            if(arquivos.get(i).getStatus().getId() == 5){
                arquivosSz.add(arquivos.get(i));
            }
        }

        return arquivosSz;
    }

}
