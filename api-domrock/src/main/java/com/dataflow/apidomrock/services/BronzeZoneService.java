package com.dataflow.apidomrock.services;

import com.dataflow.apidomrock.controllers.exceptions.CustomException;
import com.dataflow.apidomrock.dto.createHash.RequestArquivoDTO;
import com.dataflow.apidomrock.dto.createHash.ResponseMetaDTO;
import com.dataflow.apidomrock.dto.editarhash.RequestEditHashDTO;
import com.dataflow.apidomrock.dto.savehash.RequestHashDTO;
import com.dataflow.apidomrock.dto.savehash.RequestMetadadoDTO;
import com.dataflow.apidomrock.dto.setstatusbz.RequestBodySetStatusBzDTO;
import com.dataflow.apidomrock.dto.visualizeHash.RequestVisualizeHashDTO;
import com.dataflow.apidomrock.dto.visualizeHash.ResponseHashDTO;
import com.dataflow.apidomrock.entities.database.Arquivo;
import com.dataflow.apidomrock.entities.database.Metadata;
import com.dataflow.apidomrock.entities.database.Usuario;
import com.dataflow.apidomrock.entities.enums.Acao;
import com.dataflow.apidomrock.entities.enums.Estagio;
import com.dataflow.apidomrock.entities.enums.NivelAcessoEnum;
import com.dataflow.apidomrock.entities.enums.StatusArquivo;
import com.dataflow.apidomrock.repository.ArquivoRepository;
import com.dataflow.apidomrock.repository.MetadataRepository;
import com.dataflow.apidomrock.repository.UsuarioRepository;
import com.dataflow.apidomrock.services.utils.Logger;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BronzeZoneService {

    @Autowired
    UsuarioRepository usuarioRepository;
    @Autowired
    ArquivoRepository arquivoRepository;
    @Autowired
    MetadataRepository metadataRepository;

    @Autowired
    Logger logger;

    public void updateStatus (RequestBodySetStatusBzDTO request) throws CustomException {

        Optional<Usuario> user = usuarioRepository.findByEmailCustom(request.usuario());
        if (user.isEmpty()){throw new CustomException("Usuário não encontrado", HttpStatus.BAD_REQUEST);}

        if (request.salvar()) {
            Optional<Arquivo> a = arquivoRepository.findByNameAndOrganization(request.arquivo(), request.cnpjFile());
            if (a.isEmpty()){throw new CustomException("Arquivo não encontrado", HttpStatus.BAD_REQUEST);}
            Arquivo arq =  a.get();
            arq.setStatus(StatusArquivo.BRONZE_ZONE.getDescricao());
            arquivoRepository.save(arq);
            logger.insert(user.get().getId(),arq.getId(),request.obs(), Estagio.B, Acao.APROVAR);
        } else {
            if(!request.obs().isEmpty()){
                Optional<Arquivo> a = arquivoRepository.findByNameAndOrganization(request.arquivo(), request.cnpjFile());
                if (a.isEmpty()){throw new CustomException("Arquivo não encontrado", HttpStatus.BAD_REQUEST);}
                Arquivo arq =  a.get();
                arq.setStatus(StatusArquivo.NAO_APROVADO_PELA_BRONZE.getDescricao());
                arquivoRepository.save(arq);
                logger.insert(user.get().getId(),arq.getId(),request.obs(), Estagio.B, Acao.REPROVAR);
            }else{
                throw new CustomException("Você não pode reprovar sem o preenchimento da observação", HttpStatus.BAD_REQUEST);
            }
        }
    }

    public List<ResponseMetaDTO> selectMetadadosToHash(RequestArquivoDTO request) throws CustomException{

        Optional<Arquivo> arq = arquivoRepository.findByNameAndOrganization(request.nomeArquivo(), request.cnpj());

        if (arq.isEmpty()){
            throw new CustomException("O arquivo não foi encontrado", HttpStatus.BAD_REQUEST);
        }

        if (!arq.get().getMetadados().isEmpty()) {
            List<Metadata> meta = metadataRepository.findByArquivo(arq.get().getId());

            List<ResponseMetaDTO> metaDTO = meta.stream().map(ResponseMetaDTO::new).toList();

            return metaDTO;
        }else{
            throw new CustomException("No arquivo [ "+arq.get().getNomeArquivo()+" ] não existem metadados para criar o hash", HttpStatus.BAD_REQUEST);
        }

    }

    @Transactional(rollbackOn = CustomException.class)
    public void save(RequestHashDTO request, Boolean isUpdate) throws  CustomException{

        Optional<Usuario> user = usuarioRepository.findByEmailCustom(request.usuario());

        Optional<Arquivo> aBD = arquivoRepository.findByNameAndOrganization(request.nomeArquivo(), request.cnpj());

        if (aBD.isEmpty()){
            throw new CustomException("Arquivo não encontrado", HttpStatus.BAD_REQUEST);
        }

        Arquivo arquivo = aBD.get();

        int qtdMeta = request.metadados().size();
        boolean somenteVp = true;

        if(qtdMeta != 0){
            for(int i = 0; i < qtdMeta; i++){
                //Faz a verificação se existe algum metadado que não possui valor padrão.
                if(request.metadados().get(i).valorPadrao().isEmpty()){
                    somenteVp = false;
                }
            }
            if(somenteVp){
                throw new CustomException("Todos os metadados selecionados possuem valor padrão, escolha um que não possua para criar o hash", HttpStatus.BAD_REQUEST);
            }else{
                for(int j = 0; j < qtdMeta; j++) {
                    int idMetadado = metadataRepository.findByArquivoAndMetadado(arquivo.getId(), request.metadados().get(j).nome());

                    arquivoRepository.saveHash(arquivo.getId(), idMetadado);

                    // Alterando o status do arquivo para validação do parceiro silver
                    arquivo.setStatus(StatusArquivo.AGUARDANDO_APROVACAO_SILVER.getDescricao());
                    arquivoRepository.save(arquivo);
                    if(!isUpdate){
                        logger.insert(user.get().getId(), arquivo.getId(), "O metadado " + metadataRepository.findById(idMetadado).get().getNome() + " foi inserido no hash", Estagio.B, Acao.INSERIR);
                    }else{
                        logger.insert(user.get().getId(), arquivo.getId(), "O novo metadado para compor o hash é o " + metadataRepository.findById(idMetadado).get().getNome(), Estagio.B, Acao.ALTERAR);
                    }
                }
            }
        }else{
            throw new CustomException("Nenhum metadado foi selecionado para compor o hash", HttpStatus.BAD_REQUEST);
        }
    }

    public List<RequestMetadadoDTO> visualizeHash(RequestVisualizeHashDTO request) throws CustomException {

        Optional<Usuario> user = usuarioRepository.findByEmailCustom(request.usuario());

        Optional<Arquivo> aBD = arquivoRepository.findByNameAndOrganization(request.nomeArquivo(), request.cnpj());

        if (aBD.isEmpty()){
            throw new CustomException("Arquivo não encontrado", HttpStatus.BAD_REQUEST);
        }

        Arquivo arquivo = aBD.get();

        if(user.isPresent()){
            if(user.get().getOrganizacao().getCnpj().equals(arquivo.getOrganizacao().getCnpj()) || user.get().getNiveisAcesso().getFirst().getNivel().equals(NivelAcessoEnum.MASTER.toString()) || user.get().getNiveisAcesso().getFirst().getNivel().equals(NivelAcessoEnum.FULL.toString())){

                List<Metadata> metaNoHash = arquivoRepository.findByMetadataHash(arquivo.getId());

                List<RequestMetadadoDTO> metaDTO = metaNoHash.stream().map(RequestMetadadoDTO::new).toList();

                return metaDTO;

            }else{
                throw new CustomException("O arquivo ["+ arquivo.getNomeArquivo() + "] não pertence a organização [" + arquivo.getOrganizacao().getNome(), HttpStatus.BAD_REQUEST);
            }
        }else {
            throw new CustomException("O usuário ["+ user.get().getEmail() + "] não foi localizado", HttpStatus.BAD_REQUEST);
        }
    }

    public List<RequestMetadadoDTO> visualizeMetadadosForaHash(RequestVisualizeHashDTO request) throws CustomException {

        Optional<Usuario> user = usuarioRepository.findByEmailCustom(request.usuario());

        Optional<Arquivo> aBD = arquivoRepository.findByNameAndOrganization(request.nomeArquivo(), request.cnpj());

        if (aBD.isEmpty()){
            throw new CustomException("Arquivo não encontrado", HttpStatus.BAD_REQUEST);
        }

        Arquivo arquivo = aBD.get();

        if(user.isPresent()){
            if(user.get().getOrganizacao().getCnpj().equals(arquivo.getOrganizacao().getCnpj()) || user.get().getNiveisAcesso().getFirst().getNivel().equals(NivelAcessoEnum.MASTER.toString()) || user.get().getNiveisAcesso().getFirst().getNivel().equals(NivelAcessoEnum.FULL.toString())){
                List<Metadata> metaForaDoHash = metadataRepository.findByArquivo(arquivo.getId());

                List<Metadata> metadadosNoHash = arquivoRepository.findByMetadataHash(arquivo.getId());

                List<Integer> remover = new ArrayList<>();

                int qtdMetadados = metaForaDoHash.size();

                int qtdMetaNoHash = metadadosNoHash.size();

                for( int i = 0; i < qtdMetadados;i++){
                    for(int j = 0; j < qtdMetaNoHash;j++){
                        if(metaForaDoHash.get(i).getID() == (metadadosNoHash.get(j).getID())){
                            remover.add(i);
                        }
                    }
                    if(!metaForaDoHash.get(i).getIsAtivo()){
                        remover.add(i);
                    }
                }

                int qtdARemover = remover.size() - 1;

                for(int i = qtdARemover; i >= 0; i--){
                    metaForaDoHash.remove(remover.get(i).intValue());
                }

                List<RequestMetadadoDTO> metaDTO = metaForaDoHash.stream().map(RequestMetadadoDTO::new).toList();

                return metaDTO;

            }else{
                throw new CustomException("O arquivo ["+ arquivo.getNomeArquivo() + "] não pertence a organização [" + arquivo.getOrganizacao().getNome(), HttpStatus.BAD_REQUEST);
            }
        }else {
            throw new CustomException("O usuário ["+ user.get().getEmail() + "] não foi localizado", HttpStatus.BAD_REQUEST);
        }
    }

    public String visualzeObs(RequestVisualizeHashDTO request) throws CustomException {

        Optional<Usuario> user = usuarioRepository.findByEmailCustom(request.usuario());

        Optional<Arquivo> aBD = arquivoRepository.findByNameAndOrganization(request.nomeArquivo(), request.cnpj());

        if (aBD.isEmpty()){
            throw new CustomException("Arquivo não encontrado", HttpStatus.BAD_REQUEST);
        }

        Arquivo arquivo = aBD.get();

        if(user.isPresent()){
            if(user.get().getOrganizacao().getCnpj().equals(arquivo.getOrganizacao().getCnpj()) || user.get().getNiveisAcesso().getFirst().getNivel().equals(NivelAcessoEnum.MASTER.toString()) || user.get().getNiveisAcesso().getFirst().getNivel().equals(NivelAcessoEnum.FULL.toString())){

                String obs = arquivoRepository.findObservacao(arquivo.getId());

                return obs;

            }else{
                throw new CustomException("O arquivo ["+ arquivo.getNomeArquivo() + "] não pertence a organização [" + arquivo.getOrganizacao().getNome(), HttpStatus.BAD_REQUEST);
            }
        }else {
            throw new CustomException("O usuário ["+ user.get().getEmail() + "] não foi localizado", HttpStatus.BAD_REQUEST);
        }
    }

    @Transactional(rollbackOn = CustomException.class)
    public void editHash(RequestEditHashDTO request) throws CustomException{

        Optional<Arquivo> aBD = arquivoRepository.findByNameAndOrganization(request.nomeArquivo(), request.cnpj());

        if (aBD.isEmpty()){
            throw new CustomException("Arquivo não encontrado", HttpStatus.BAD_REQUEST);
        }

        Arquivo arquivo = aBD.get();

        arquivoRepository.deleteHash(arquivo.getId());

        RequestHashDTO requestNova = new RequestHashDTO(request);
        save(requestNova, true);
    }
}