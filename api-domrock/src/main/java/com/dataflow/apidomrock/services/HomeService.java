package com.dataflow.apidomrock.services;

import com.dataflow.apidomrock.controllers.exceptions.CustomException;
import com.dataflow.apidomrock.dto.deleteFile.RequestBodyDeleteFileDTO;
import com.dataflow.apidomrock.dto.homedados.ResponseArquivosDTO;
import com.dataflow.apidomrock.entities.database.Arquivo;
import com.dataflow.apidomrock.entities.database.NivelAcesso;
import com.dataflow.apidomrock.entities.database.Usuario;
import com.dataflow.apidomrock.entities.enums.Acao;
import com.dataflow.apidomrock.entities.enums.StatusArquivo;
import com.dataflow.apidomrock.repository.ArquivoRepository;
import com.dataflow.apidomrock.repository.UsuarioRepository;
import com.dataflow.apidomrock.entities.enums.NivelAcessoEnum;
import com.dataflow.apidomrock.services.utils.Logger;
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

    @Autowired
    Logger logger;

    public List<NivelAcesso> getNivel(String emailUsuario){
        List<NivelAcesso> nivel = usuarioRepository.getNivelUsuario(emailUsuario);

        return nivel;

    }

    public List<Arquivo> getArquivosUsuario(String emailUsuario){
        Optional<Usuario> usuario = usuarioRepository.findByEmailCustom(emailUsuario);
        List<NivelAcesso> nivel = getNivel(emailUsuario);
        List<Arquivo> arquivos = new ArrayList<>();
        if(usuario.isPresent()){
            int qtdNivelAcesso = nivel.size();
            for(int i = 0; i < qtdNivelAcesso; i++){
                if(nivel.get(i).getNivel().equals(NivelAcessoEnum.MASTER.toString()) || nivel.get(i).getNivel().equals(NivelAcessoEnum.FULL.toString())){
                    arquivos = arquivoRepository.findArquivoByAtivo();
                }else {
                    String organizacao = usuario.get().getOrganizacao().getNome();

                    arquivos = arquivoRepository.findByOrganizacao(organizacao);
                }
            }
        }else{
            throw new RuntimeException("Usuário não cadastrado");
        }

        return arquivos;
    }

    public List<ResponseArquivosDTO> arquivosHome(List<Arquivo> arquivos){

        List<ResponseArquivosDTO> arq = arquivos.stream()
                    .map(ResponseArquivosDTO::new)
                    .collect(Collectors.toList());

            return arq;

    }

    @Transactional(rollbackFor = CustomException.class)
    public void deleteFile(RequestBodyDeleteFileDTO request) throws CustomException {
        //CONFERE SE O USUARIO QUE SUBIU O JSON JA EXISTE NA BASE
        Optional<Usuario> userBD = usuarioRepository.findByEmailCustom(request.getUsuario());
        //SE NÃO EXISTIR, ELE SOLTA ESTA "CRITICA"
        if (userBD.isEmpty()) {
            throw new CustomException("Usuário [" + request.getUsuario() + "] não existe", HttpStatus.NOT_FOUND);
        }

        boolean hasPermission = false;
        for (NivelAcesso nvlAccess: userBD.get().getNiveisAcesso()) {
            if (Objects.equals(nvlAccess.getNivel(), NivelAcessoEnum.MASTER.toString()) || Objects.equals(nvlAccess.getNivel(), NivelAcessoEnum.FULL.toString())){
                hasPermission = true;
            }
        }
        if (!hasPermission){
            throw new CustomException("Usuário [" + request.getUsuario() + "] não tem permissão para executar a ação", HttpStatus.UNAUTHORIZED);
        }

        //CONFERE SE O ARQUIVO QUE SUBIU O JSON JA EXISTE NA BASE
        Optional<Arquivo> arqBD = arquivoRepository.findByNameAndOrganizationName(request.getNomeArquivo(), request.getUsuarioOrg());
        if (arqBD.isEmpty()) {
            //SE NÃO EXISTIR, ELE SOLTA ESTA "CRITICA"
            throw new CustomException("Arquivo [" + request.getNomeArquivo() + "] não encontrado para a organização [" + userBD.get().getOrganizacao().getNome() + "]", HttpStatus.NOT_FOUND);
        }

        Arquivo arq = arqBD.get();
        arq.setAtivo(false);
        arquivoRepository.save(arq);


        logger.insert(userBD.get().getId(), arq.getId(), "Delete file", Logger.getEstagioByStatus(arq.getStatus()), Acao.EXCLUIR);
    }
}
