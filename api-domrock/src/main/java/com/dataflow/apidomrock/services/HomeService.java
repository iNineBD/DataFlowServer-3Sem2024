package com.dataflow.apidomrock.services;

import com.dataflow.apidomrock.controllers.exceptions.CustomException;
import com.dataflow.apidomrock.dto.deleteFile.RequestBodyDeleteFileDTO;
import com.dataflow.apidomrock.dto.homedados.ResponseArquivosDTO;
import com.dataflow.apidomrock.entities.database.Arquivo;
import com.dataflow.apidomrock.entities.database.NivelAcesso;
import com.dataflow.apidomrock.entities.database.Usuario;
import com.dataflow.apidomrock.repository.ArquivoRepository;
import com.dataflow.apidomrock.repository.UsuarioRepository;
import com.dataflow.apidomrock.services.utils.Niveis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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

    public List<ResponseArquivosDTO> arquivosLanding(String nivel, List<Arquivo> arquivos){
        int qtdArquivos = arquivos.size();
        List<Arquivo> arq = new ArrayList<>();
        List<ResponseArquivosDTO> arquivosLz = new ArrayList<>();

        if(nivel.equals(Niveis.LZ.toString()) || nivel.equals(Niveis.MASTER.toString()) || nivel.equals(Niveis.FULL.toString())){
            for (int i = 0; i < qtdArquivos; i++) {
                if (arquivos.get(i).getStatus().getId() == 1 || arquivos.get(i).getStatus().getId() == 98) {
                    arq.add(arquivos.get(i));
                }
            }

            arquivosLz = arq.stream()
                    .map(ResponseArquivosDTO::new)
                    .collect(Collectors.toList());

            return arquivosLz;
        }else {
            return arquivosLz;
        }

    }

    public List<ResponseArquivosDTO> arquivosBronze(String nivel, List<Arquivo> arquivos){
        int qtdArquivos = arquivos.size();
        List<Arquivo> arq = new ArrayList<>();
        List<ResponseArquivosDTO> arquivosBz = new ArrayList<>();

        if(nivel.equals(Niveis.B.toString()) || nivel.equals(Niveis.MASTER.toString()) || nivel.equals(Niveis.FULL.toString())){
            for (int i = 0; i < qtdArquivos; i++){
                if(arquivos.get(i).getStatus().getId() == 2 || arquivos.get(i).getStatus().getId() == 3 || arquivos.get(i).getStatus().getId() == 99){
                    arq.add(arquivos.get(i));
                }
            }

            arquivosBz = arq.stream()
                    .map(ResponseArquivosDTO::new)
                    .collect(Collectors.toList());

            return arquivosBz;
        }else{
            return arquivosBz;
        }
    }

    public List<ResponseArquivosDTO> arquivosSilver(String nivel, List<Arquivo> arquivos){
        int qtdArquivos = arquivos.size();
        List<Arquivo> arq = new ArrayList<>();
        List<ResponseArquivosDTO> arquivosSz = new ArrayList<>();

        if(nivel.equals(Niveis.S.toString()) || nivel.equals(Niveis.MASTER.toString()) || nivel.equals(Niveis.FULL.toString())){
            for (int i = 0; i < qtdArquivos; i++){
                if(arquivos.get(i).getStatus().getId() == 4 || arquivos.get(i).getStatus().getId() == 5){
                    arq.add(arquivos.get(i));
                }
            }

            arquivosSz = arq.stream()
                    .map(ResponseArquivosDTO::new)
                    .collect(Collectors.toList());

            return arquivosSz;
        }else{
            return arquivosSz;
        }
    }

    @Transactional(rollbackFor = CustomException.class)
    public void deleteFile(RequestBodyDeleteFileDTO request) throws CustomException {
        //CONFERE SE O USUARIO QUE SUBIU O JSON JA EXISTE NA BASE
        Optional<Usuario> userBD = usuarioRepository.findById(request.getUsuario());
        //SE NÃO EXISTIR, ELE SOLTA ESTA "CRITICA"
        if (userBD.isEmpty()) {
            throw new CustomException("Usuário [" + request.getUsuario() + "] não existe", HttpStatus.NOT_FOUND);
        }

        boolean hasPermission = false;
        for (NivelAcesso nvlAccess: userBD.get().getNiveisAcesso()) {
            if (Objects.equals(nvlAccess.getNivel(), "MASTER")){
                hasPermission = true;
            }
        }

        if (!hasPermission){
            throw new CustomException("Usuário [" + request.getUsuario() + "] não tem permissão para executar a ação", HttpStatus.UNAUTHORIZED);
        }

        //CONFERE SE O ARQUIVO QUE SUBIU O JSON JA EXISTE NA BASE
        Optional<Arquivo> arqBD = arquivoRepository.findByNameAndOrganization(request.getNomeArquivo(), userBD.get().getOrganizacao().getNome());
        if (arqBD.isEmpty()) {
            //SE NÃO EXISTIR, ELE SOLTA ESTA "CRITICA"
            throw new CustomException("Arquivo [" + request.getNomeArquivo() + "] não encontrado para a organização [" + userBD.get().getOrganizacao().getNome() + "]", HttpStatus.NOT_FOUND);
        }

        arquivoRepository.delete(arqBD.get());
    }
}
