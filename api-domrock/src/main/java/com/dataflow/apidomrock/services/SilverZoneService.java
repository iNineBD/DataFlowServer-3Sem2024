package com.dataflow.apidomrock.services;


import com.dataflow.apidomrock.controllers.exceptions.CustomException;
import com.dataflow.apidomrock.dto.editdepara.RequestEditDePara;
import com.dataflow.apidomrock.dto.excluirdepara.MetadadosExcluirDePara;
import com.dataflow.apidomrock.dto.excluirdepara.RequestExcluirDePara;
import com.dataflow.apidomrock.dto.gethash.ResponseNomeMetadataDTO;
import com.dataflow.apidomrock.dto.gethash.ResquestHashToSilverDTO;
import com.dataflow.apidomrock.dto.getmetadadostotepara.MetadadosDePara;
import com.dataflow.apidomrock.dto.getmetadadostotepara.RequestMetaToDePara;
import com.dataflow.apidomrock.dto.savedepara.MetadadosToDePara;
import com.dataflow.apidomrock.dto.savedepara.RequestSaveDePara;
import com.dataflow.apidomrock.dto.setstatussz.RequestBodySetStatusSz;
import com.dataflow.apidomrock.dto.visualizeDePara.DeParasVisualize;
import com.dataflow.apidomrock.dto.visualizeDePara.MetadadosDeParaVisualize;
import com.dataflow.apidomrock.dto.visualizeDePara.RequestDadosToDePara;
import com.dataflow.apidomrock.entities.database.Arquivo;
import com.dataflow.apidomrock.entities.database.DePara;
import com.dataflow.apidomrock.entities.database.Metadata;
import com.dataflow.apidomrock.entities.database.Usuario;
import com.dataflow.apidomrock.entities.enums.Acao;
import com.dataflow.apidomrock.entities.enums.Estagio;
import com.dataflow.apidomrock.entities.enums.StatusArquivo;
import com.dataflow.apidomrock.repository.ArquivoRepository;
import com.dataflow.apidomrock.repository.DeParaRepository;
import com.dataflow.apidomrock.repository.MetadataRepository;
import com.dataflow.apidomrock.repository.UsuarioRepository;
import com.dataflow.apidomrock.services.utils.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SilverZoneService {

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    ArquivoRepository arquivoRepository;

    @Autowired
    MetadataRepository metadataRepository;

    @Autowired
    DeParaRepository deParaRepository;

    @Autowired
    Logger logger;

    @Transactional(rollbackFor = CustomException.class)
    public void updateStatus(RequestBodySetStatusSz request) throws CustomException {

        Optional<Usuario> user = usuarioRepository.findByEmailCustom(request.usuario());
        if (user.isEmpty()) {
            throw new CustomException("Ocorreu um erro inesperado ao buscar o usuario", HttpStatus.BAD_REQUEST);
        } else {
            if (request.salvar()) {
                Optional<Arquivo> arq = arquivoRepository.findByNameAndOrganization(request.arquivo(), request.cnpj());
                arq.get().setStatus(StatusArquivo.SILVER_ZONE.getDescricao());
                arquivoRepository.save(arq.get());
                logger.insert(user.get().getId(), arq.get().getId(), request.obs(), Estagio.S, Acao.APROVAR);
            } else {
                if (!request.obs().isEmpty()) {
                    Optional<Arquivo> arq = arquivoRepository.findByNameAndOrganization(request.arquivo(), request.cnpj());
                    arq.get().setStatus(StatusArquivo.NAO_APROVADO_PELA_SILVER.getDescricao());
                    arquivoRepository.save(arq.get());
                    logger.insert(user.get().getId(), arq.get().getId(), request.obs(), Estagio.S, Acao.REPROVAR);
                } else {
                    throw new CustomException("Você não pode reprovar sem o preenchimento da observação", HttpStatus.BAD_REQUEST);
                }
            }
        }
    }

    public List<ResponseNomeMetadataDTO> getMetadadosNoHash(ResquestHashToSilverDTO request) throws CustomException {

        Optional<Usuario> usuario = usuarioRepository.findByEmailCustom(request.usuario());

        Optional<Arquivo> arquivo = arquivoRepository.findByNameAndOrganization(request.nomeArquivo(), request.cnpjOrg());

        if (usuario.isEmpty()) {
            throw new CustomException("Ocorreu um erro inesperado ao buscar o usuario", HttpStatus.BAD_REQUEST);
        } else {

            if (arquivo.isEmpty()) {
                throw new CustomException("Ocorreu um erro inesperado ao buscar o arquivo", HttpStatus.BAD_REQUEST);
            } else {
                List<Metadata> metadadosNoHash = arquivoRepository.findByMetadataHash(arquivo.get().getId());

                List<ResponseNomeMetadataDTO> metadados = metadadosNoHash.stream().map(ResponseNomeMetadataDTO::new).toList();

                return metadados;
            }

        }
    }

    public List<MetadadosDePara> getMetadadosToDePara(RequestMetaToDePara request) throws CustomException {

        Optional<Arquivo> arquivo = arquivoRepository.findByNameAndOrganization(request.arquivo(), request.cnpj());

        Optional<Usuario> usuario = usuarioRepository.findByEmailCustom(request.email());

        if (usuario.isEmpty()) {
            throw new CustomException("Ocorreu um erro inesperado ao buscar o usuario", HttpStatus.BAD_REQUEST);
        } else {
            if (arquivo.isEmpty()) {
                throw new CustomException("Ocorreu um erro inesperado ao buscar o arquivo", HttpStatus.BAD_REQUEST);
            } else {
                List<MetadadosDePara> metadadosNoDePara = request.listMetadados();
                List<Metadata> metadados = arquivo.get().getMetadados();
                int qtdMetadados = metadados.size();
                int qtdMetadadosNoDePara = metadadosNoDePara.size();

                List<Integer> metadadosParaExcluir = new ArrayList<>();

                for (int i = 0; i < qtdMetadados; i++) {
                    for (int j = 0; j < qtdMetadadosNoDePara; j++) {
                        if (metadados.get(i).getNome().equals(metadadosNoDePara.get(j).nome()) ) {
                            metadadosParaExcluir.add(i);
                        }
                    }
                    if(!metadados.get(i).getIsAtivo()){
                        metadadosParaExcluir.add(i);
                    }
                }

                int qtdMetadadosExcluir = metadadosParaExcluir.size() -1;

                for (int i = qtdMetadadosExcluir; i >= 0; i--) {
                    metadados.remove(metadadosParaExcluir.get(i).intValue());
                }

                List<MetadadosDePara> metadadosDisponiveis = metadados.stream().map(MetadadosDePara::new).toList();

                return metadadosDisponiveis;
            }
        }
    }

    @Transactional(rollbackFor = CustomException.class)
    public void saveDePara(RequestSaveDePara request, boolean isUpdate) throws CustomException {

        Optional<Arquivo> arquivo = arquivoRepository.findByNameAndOrganization(request.nomeArquivo(), request.cnpj());

        Optional<Usuario> usuario = usuarioRepository.findByEmailCustom(request.email());

        if (usuario.isEmpty()) {
            throw new CustomException("Ocorreu um erro ao buscar o usuario", HttpStatus.BAD_REQUEST);
        } else {
            if (arquivo.isEmpty()) {
                throw new CustomException("Ocorreu um erro ao buscar o arquivo", HttpStatus.BAD_REQUEST);
            } else {

                List<MetadadosToDePara> metadados = request.metadados();

                int qtdMetadado = metadados.size();


                for (int i = 0; i < qtdMetadado; i++) {
                    int idMetadado = metadataRepository.findByArquivoAndMetadado(arquivo.get().getId(), metadados.get(i).nome());
                    int qtdDePara = metadados.get(i).deParas().size();

                    for (int j = 0; j < qtdDePara; j++) {
                        String de = String.valueOf(metadados.get(i).deParas().get(j).de());
                        String para = String.valueOf(metadados.get(i).deParas().get(j).para());

                        if(de.trim().isEmpty() || para.trim().isEmpty()){
                            throw new CustomException("Existe algum valor que o DE ou PARA que está vazio, por favor revise", HttpStatus.BAD_REQUEST);
                        }else{
                            int deOuParaExiste = deParaRepository.buscaQtdDeParaIguais(de,idMetadado);

                            if(deOuParaExiste > 0){
                                throw new CustomException("Existem valores DE repetidos no mesmo metadado, por favor revise", HttpStatus.BAD_REQUEST);
                            } else if (de.trim().equalsIgnoreCase(para.trim())) {
                                throw new CustomException("Existe algum valor que o DE é igual ao PARA, por favor revise", HttpStatus.BAD_REQUEST);
                            }else {
                                deParaRepository.saveDePara(idMetadado, de.toUpperCase(), para.toUpperCase());
                            }
                        }
                    }

                    if(isUpdate){
                        logger.insert(usuario.get().getId(), arquivo.get().getId(), "Atualizado De Para do metadado" + metadados.get(i).nome(), Estagio.S, Acao.ALTERAR);
                    }else {
                        logger.insert(usuario.get().getId(), arquivo.get().getId(), "Inserido De Para do metadado" + metadados.get(i).nome(), Estagio.S, Acao.INSERIR);
                    }
                }
                if(qtdMetadado == 0){
                    arquivo.get().setStatus(StatusArquivo.SILVER_ZONE.getDescricao());
                    logger.insert(usuario.get().getId(), arquivo.get().getId(), "O arquivo não possui mais De Para, por isso retorno a Silver", Estagio.S, Acao.ALTERAR);
                }
                if(!isUpdate){
                    arquivo.get().setStatus(StatusArquivo.FINALIZADO.getDescricao());
                }
            }
        }
    }

    public List<MetadadosDeParaVisualize> visualizeDePara(RequestDadosToDePara request) throws CustomException {

        Optional<Arquivo> arquivo = arquivoRepository.findByNameAndOrganization(request.arquivo(), request.cnpj());

        Optional<Usuario> usuario = usuarioRepository.findByEmailCustom(request.email());

        if (usuario.isEmpty()) {
            throw new CustomException("Ocorreu um erro ao buscar o usuário", HttpStatus.BAD_REQUEST);
        } else {
            if (arquivo.isEmpty()) {
                throw new CustomException("Ocorreu um erro ao buscar o arquivo", HttpStatus.BAD_REQUEST);
            } else {
                List<Metadata> metadadosAtivos = metadataRepository.findByArquivoAndMetadadoIsAtivo(arquivo.get().getId());

                List<MetadadosDeParaVisualize> metadadosDeParas = new ArrayList<>();
                int qtdMetaAtivos = metadadosAtivos.size();

                for (int i = 0; i < qtdMetaAtivos; i++) {
                    int idMetadado = metadadosAtivos.get(i).getID();

                    List<DePara> lista = deParaRepository.findByIdMetadado(idMetadado);

                    if (lista.size() > 0) {
                        List<DeParasVisualize> deParas = lista.stream().map(DeParasVisualize::new).toList();

                        MetadadosDeParaVisualize temp = new MetadadosDeParaVisualize(deParas, metadadosAtivos.get(i).getNome());

                        metadadosDeParas.add(temp);
                    }

                }
                return metadadosDeParas;

            }
        }
    }

    @Transactional(rollbackFor = CustomException.class)
    public void editDePara(RequestEditDePara request) throws CustomException {

        Optional<Arquivo> arquivo = arquivoRepository.findByNameAndOrganization(request.nomeArquivo(), request.cnpj());

        Optional<Usuario> usuario = usuarioRepository.findByEmailCustom(request.email());

        if (usuario.isEmpty()) {
            throw new CustomException("Ocorreu um erro ao buscar o usuário", HttpStatus.BAD_REQUEST);
        } else {
            if (arquivo.isEmpty()) {
                throw new CustomException("Ocorreu um erro ao buscar o arquivo", HttpStatus.BAD_REQUEST);
            } else {
                List<Metadata> metadata = metadataRepository.findByArquivoAndMetadadoIsAtivo(arquivo.get().getId());
                int qtdMetadados = metadata.size();

                for (int i = 0; i < qtdMetadados; i++) {
                    int idMetadado = metadata.get(i).getID();
                    deParaRepository.deleteDePara(idMetadado);
                }

                RequestSaveDePara requestSaveDePara = new RequestSaveDePara(request);
                saveDePara(requestSaveDePara,true);
            }
        }

    }

    @Transactional(rollbackFor = CustomException.class)
    public void excluirDePara(RequestExcluirDePara request) throws CustomException {

        Optional<Arquivo> arquivo = arquivoRepository.findByNameAndOrganization(request.nomeArquivo(), request.cnpj());

        Optional<Usuario> usuario = usuarioRepository.findByEmailCustom(request.email());

        if (usuario.isEmpty()) {
            throw new CustomException("Ocorreu um erro ao buscar o usuário", HttpStatus.BAD_REQUEST);
        } else {
            if (arquivo.isEmpty()) {
                throw new CustomException("Ocorreu um erro ao buscar o arquivo", HttpStatus.BAD_REQUEST);
            } else {
                List<MetadadosExcluirDePara> metadata = request.metadados();
                int qtdMetadados = metadata.size();

                for (int i = 0; i < qtdMetadados; i++) {
                    int idMetadado = metadataRepository.findByArquivoAndMetadado(arquivo.get().getId(),metadata.get(i).nome());
                    int qtdDePara = metadata.get(i).deParas().size();

                    for(int j = 0; j < qtdDePara; j++){
                        String de = metadata.get(i).deParas().get(j).de();
                        String para = metadata.get(i).deParas().get(j).para();
                        deParaRepository.deleteDeParaCustom(idMetadado, de);
                        logger.insert(usuario.get().getId(), arquivo.get().getId(), "Excluído De Para do metadado" + metadata.get(i).nome()+ ", onde DE era "+ de+ " e PARA era "+ para, Estagio.S, Acao.EXCLUIR);
                    }

                }

            }
        }

    }
}
