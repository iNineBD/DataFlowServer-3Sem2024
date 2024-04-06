package com.dataflow.apidomrock.services;

import com.dataflow.apidomrock.dto.ArquivosLzBS.ArquivoDTO;
import com.dataflow.apidomrock.entities.database.Arquivo;
import com.dataflow.apidomrock.entities.database.NivelAcesso;
import com.dataflow.apidomrock.entities.database.Usuario;
import com.dataflow.apidomrock.repository.ArquivoRepository;
import com.dataflow.apidomrock.repository.UsuarioRepository;
import com.dataflow.apidomrock.services.utils.Niveis;
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


    public String getNivel(String emailUsuario){
        NivelAcesso nivel = usuarioRepository.getNivelUsuario(emailUsuario);

        return nivel.getNivel().toUpperCase();

    }

    public List<Arquivo> getArquivosUsuario(String emailUsuario){
        Optional<Usuario> usuario = usuarioRepository.findById(emailUsuario);
        String nivel = getNivel(emailUsuario);
        if(usuario.isPresent()){
            if(nivel.equals(Niveis.MASTER.toString()) || nivel.equals(Niveis.FULL.toString())){
                return arquivoRepository.findAll();
            }else {
                String organizacao = usuario.get().getOrganizacao().getNome();

                return arquivoRepository.findByOrganizacao(organizacao);
            }

        }else{
            throw new RuntimeException("Usuário não cadastrado");
        }
    }

    public List<ArquivoDTO> arquivosLanding(String nivel, List<Arquivo> arquivos){
        int qtdArquivos = arquivos.size();
        List<Arquivo> arq = new ArrayList<>();
        List<ArquivoDTO> arquivosLz = new ArrayList<>();

        if(nivel.equals(Niveis.LZ.toString()) || nivel.equals(Niveis.MASTER.toString()) || nivel.equals(Niveis.FULL.toString())){
            for (int i = 0; i < qtdArquivos; i++) {
                if (arquivos.get(i).getStatus().getId() == 1 || arquivos.get(i).getStatus().getId() == 98) {
                    arq.add(arquivos.get(i));
                }
            }

            arquivosLz = arq.stream()
                    .map(ArquivoDTO::new)
                    .collect(Collectors.toList());

            return arquivosLz;
        }else {
            return arquivosLz;
        }

    }

    public List<ArquivoDTO> arquivosBronze(String nivel, List<Arquivo> arquivos){
        int qtdArquivos = arquivos.size();
        List<Arquivo> arq = new ArrayList<>();
        List<ArquivoDTO> arquivosBz = new ArrayList<>();

        if(nivel.equals(Niveis.B.toString()) || nivel.equals(Niveis.MASTER.toString()) || nivel.equals(Niveis.FULL.toString())){
            for (int i = 0; i < qtdArquivos; i++){
                if(arquivos.get(i).getStatus().getId() == 2 || arquivos.get(i).getStatus().getId() == 3 || arquivos.get(i).getStatus().getId() == 99){
                    arq.add(arquivos.get(i));
                }
            }

            arquivosBz = arq.stream()
                    .map(ArquivoDTO::new)
                    .collect(Collectors.toList());

            return arquivosBz;
        }else{
            return arquivosBz;
        }
    }

    public List<ArquivoDTO> arquivosSilver(String nivel, List<Arquivo> arquivos){
        int qtdArquivos = arquivos.size();
        List<Arquivo> arq = new ArrayList<>();
        List<ArquivoDTO> arquivosSz = new ArrayList<>();

        if(nivel.equals(Niveis.S.toString()) || nivel.equals(Niveis.MASTER.toString()) || nivel.equals(Niveis.FULL.toString())){
            for (int i = 0; i < qtdArquivos; i++){
                if(arquivos.get(i).getStatus().getId() == 4 || arquivos.get(i).getStatus().getId() == 5){
                    arq.add(arquivos.get(i));
                }
            }

            arquivosSz = arq.stream()
                    .map(ArquivoDTO::new)
                    .collect(Collectors.toList());

            return arquivosSz;
        }else{
            return arquivosSz;
        }
    }
}
