package com.dataflow.apidomrock.services.utils;

import com.dataflow.apidomrock.controllers.exceptions.CustomException;
import com.dataflow.apidomrock.dto.log.*;
import com.dataflow.apidomrock.entities.database.*;
import com.dataflow.apidomrock.entities.enums.Acao;
import com.dataflow.apidomrock.entities.enums.Estagio;
import com.dataflow.apidomrock.entities.enums.NivelAcessoEnum;
import com.dataflow.apidomrock.repository.ArquivoRepository;
import com.dataflow.apidomrock.repository.LogRepository;
import com.dataflow.apidomrock.repository.OrganizacaoRepository;
import com.dataflow.apidomrock.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class Logger {

    @Autowired
    private LogRepository loggerRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private ArquivoRepository arquivoRepository;
    @Autowired
    private OrganizacaoRepository organizacaoRepository;

    public void insert(Integer idUser, Integer idArquivo, String obs, Estagio estagio, Acao acao) throws CustomException {
        Optional<Usuario> userBD = usuarioRepository.findById(idUser);
        if (userBD.isEmpty()) {
            throw new CustomException("Usuário não identificado", HttpStatus.BAD_REQUEST);
        }

        Log log = new Log();
        if (!(idArquivo == null)) {
            Optional<Arquivo> arqBD = arquivoRepository.findById(idArquivo);
            if (arqBD.isEmpty()) {
                throw new CustomException("Arquivo não identificado", HttpStatus.BAD_REQUEST);
            }
            log.setArquivo(arqBD.get());
        } else {
            log.setArquivo(null);
        }

        log.setUsuario(userBD.get());
        log.setAcao(acao.toString().toUpperCase());
        log.setDataHora(LocalDateTime.now());
        log.setObservacao(obs);
        log.setEstagio(estagio.getDescricao());

        loggerRepository.save(log);
    }

    public void insertToLogout(Integer idUser, Integer idArquivo, String obs, Estagio estagio, Acao acao, Log login) throws CustomException {
        Optional<Usuario> userBD = usuarioRepository.findById(idUser);
        if (userBD.isEmpty()) {
            throw new CustomException("Usuário não identificado", HttpStatus.BAD_REQUEST);
        }

        Log log = new Log();
        if (!(idArquivo == null)) {
            Optional<Arquivo> arqBD = arquivoRepository.findById(idArquivo);
            if (arqBD.isEmpty()) {
                throw new CustomException("Arquivo não identificado", HttpStatus.BAD_REQUEST);
            }
            log.setArquivo(arqBD.get());
        } else {
            log.setArquivo(null);
        }

        log.setUsuario(userBD.get());
        log.setAcao(acao.toString().toUpperCase());
        log.setDataHora(LocalDateTime.now());
        log.setObservacao(obs);
        log.setEstagio(estagio.getDescricao());

        loggerRepository.save(log);
    }

    public List<ResponseLog> visualizarLog(RequestLogDTO requestLogDTO) throws CustomException {

        Optional<Usuario> u = usuarioRepository.findByEmailCustom(requestLogDTO.email());
        if (u.isEmpty()) {
            throw new CustomException("Usuario não encontrado", HttpStatus.NOT_FOUND);
        }

        boolean hasPermission = false;

        for (NivelAcesso n : u.get().getNiveisAcesso()) {
            if (n.getNivel().equals(NivelAcessoEnum.MASTER.toString())) {
                hasPermission = true;
                break;
            }
        }

        if (!hasPermission) {
            throw new CustomException("Apenas usuarios MASTER tem acesso ao histórico", HttpStatus.UNAUTHORIZED);
        }

        Optional<Arquivo> arquivo = arquivoRepository.findByNameAndOrganization(requestLogDTO.nomeArquivo(), requestLogDTO.organizacao());
        if (arquivo.isEmpty()) {
            throw new CustomException("Arquivo não encontrado", HttpStatus.NOT_FOUND);
        }
        Organizacao organizacao = arquivo.get().getOrganizacao();
        List<Log> logBD = loggerRepository.findByArquivo(arquivo.get());
        if (logBD.isEmpty()) {
            throw new CustomException("Arquivo sem registros de alteração", HttpStatus.NOT_FOUND);
        }

        List<ResponseLog> logs = new ArrayList<>();
        for (Log log : logBD) {
            ResponseLog responseLog = new ResponseLog(
                    organizacao.getNome(), arquivo.get().getNomeArquivo(), LocalDateTime.from(log.getDataHora()).format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")), log.getObservacao(),
                    log.getEstagio(), log.getAcao(), log.getUsuario().getNome(), log.getUsuario().getEmail());
            logs.add(responseLog);
        }
        return logs;
    }

    public static Estagio getEstagioByStatus(String statusString) {
        // Convertendo a string para uppercase para garantir correspondência
        statusString = statusString.toUpperCase();

        return switch (statusString) {
            case "AGUARDANDO APROVAÇÃO DA BRONZE", "BRONZE ZONE", "NÃO APROVADO PELA SILVER" -> Estagio.B;
            case "AGUARDANDO APROVAÇÃO DA SILVER", "SILVER ZONE" -> Estagio.S;
            default -> Estagio.LZ;
        };
    }

    public List<ResponseLogUsuario> visualizarLogUsuario(RequestLogUsuario requestLogUsuario) throws CustomException {
        Optional<Usuario> usuarioBD = usuarioRepository.findByEmailCustom(requestLogUsuario.emailUsuario());
        if (usuarioBD.isEmpty()) {
            throw new CustomException("Usuario não encontrado", HttpStatus.NOT_FOUND);
        }

        Organizacao organizacao = usuarioBD.get().getOrganizacao();
        List<Log> logBD = loggerRepository.findByUsuario(usuarioBD.get().getId());
        if (logBD.isEmpty()) {
            throw new CustomException("O usuario " + usuarioBD.get().getNome() + " não possui registros de acesso", HttpStatus.NOT_FOUND);
        }

        List<ResponseLogUsuario> logs = new ArrayList<>();

        for (Log log : logBD) {
            ResponseLogUsuario responseLogUsuario = new ResponseLogUsuario(
                    organizacao.getCnpj(), LocalDateTime.from(log.getDataHora()).format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")),
                    log.getAcao(), usuarioBD.get().getNome(), usuarioBD.get().getEmail());
            logs.add(responseLogUsuario);
        }
        return logs;
    }


    public List<UserDTO> findAllUsers() {
        List<Usuario> usersBD = usuarioRepository.findAll();
        List<UserDTO> response = new ArrayList<>();
        for (Usuario u : usersBD) {
            if (u.getNome() == null){continue;}
            StringBuilder niveis = new StringBuilder();
            int len = u.getNiveisAcesso().size() - 1;
                for (NivelAcesso n : u.getNiveisAcesso()) {
                niveis.append(n.getNivel());
                if (len > 0){
                    niveis.append(", ");
                }
                len--;
            }


            response.add(new UserDTO(u.getOrganizacao().getNome(),u.getOrganizacao().getCnpj(), u.getNome(), niveis.toString(), u.getEmail()));
        }

        return response;
    }
}
