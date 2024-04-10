package com.dataflow.apidomrock.services;

import com.dataflow.apidomrock.dto.homedados.ResponseArquivosDTO;
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
        // Aqui ele busca o usuário no banco, se ele não acha no banco cai no else.
        Optional<Usuario> usuario = usuarioRepository.findById(emailUsuario);
        if(usuario.isPresent()){
            //Aqui dentro de um usuário ele pega a organização que ela pertence.
            String organizacao = usuario.get().getOrganizacao().getNome();

            //Aqui ele busca e retorna todos os arquivos da organização
            return arquivoRepository.findByOrganizacao(organizacao);

        }else{
            throw new RuntimeException("Usuário não cadastrado");
        }
    }

    public List<ResponseArquivosDTO> arquivosLanding(List<Arquivo> arquivos){
        int qtdArquivos = arquivos.size();
        List<Arquivo> arq = new ArrayList<>();

        // Dos arquivos buscados no método anterior ele separa quais estão na landing.
        for (int i = 0; i < qtdArquivos; i++) {
            if (arquivos.get(i).getStatus().getId() == 1 || arquivos.get(i).getStatus().getId() == 98) {
                arq.add(arquivos.get(i));
            }
        }

        // Neste passo ele só ajusta o retorno passando para o formato necessário no response.
        List<ResponseArquivosDTO> arquivosLz = arq.stream()
                .map(ResponseArquivosDTO::new)
                .collect(Collectors.toList());

        return arquivosLz;
    }

    public List<ResponseArquivosDTO> arquivosBronze(List<Arquivo> arquivos){
        int qtdArquivos = arquivos.size();
        List<Arquivo> arq = new ArrayList<>();

        // Dos arquivos buscados no método anterior ele separa quais estão na bronze.
        for (int i = 0; i < qtdArquivos; i++){
            if(arquivos.get(i).getStatus().getId() == 2 || arquivos.get(i).getStatus().getId() == 3 || arquivos.get(i).getStatus().getId() == 99){
                arq.add(arquivos.get(i));
            }
        }

        // Neste passo ele só ajusta o retorno passando para o formato necessário no response.
        List<ResponseArquivosDTO> arquivosBz = arq.stream()
                .map(ResponseArquivosDTO::new)
                .collect(Collectors.toList());

        return arquivosBz;
    }

    public List<ResponseArquivosDTO> arquivosSilver(List<Arquivo> arquivos){
        int qtdArquivos = arquivos.size();
        List<Arquivo> arq = new ArrayList<>();

        // Dos arquivos buscados no método anterior ele separa quais estão na silver.
        for (int i = 0; i < qtdArquivos; i++){
            if(arquivos.get(i).getStatus().getId() == 4 || arquivos.get(i).getStatus().getId() == 5){
                arq.add(arquivos.get(i));
            }
        }

        // Neste passo ele só ajusta o retorno passando para o formato necessário no response.
        List<ResponseArquivosDTO> arquivosSz = arq.stream()
                .map(ResponseArquivosDTO::new)
                .collect(Collectors.toList());

        return arquivosSz;
    }

}
