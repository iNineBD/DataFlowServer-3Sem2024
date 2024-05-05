package com.dataflow.apidomrock.services.utils;

import com.dataflow.apidomrock.controllers.exceptions.CustomException;
import com.dataflow.apidomrock.entities.database.Arquivo;
import com.dataflow.apidomrock.entities.database.Log;
import com.dataflow.apidomrock.entities.database.Usuario;
import com.dataflow.apidomrock.entities.enums.Acao;
import com.dataflow.apidomrock.entities.enums.Estagio;
import com.dataflow.apidomrock.entities.enums.StatusArquivo;
import com.dataflow.apidomrock.repository.ArquivoRepository;
import com.dataflow.apidomrock.repository.LogRepository;
import com.dataflow.apidomrock.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

@Component
public class Logger {

  @Autowired
  private LogRepository loggerRepository;
  @Autowired
  private UsuarioRepository usuarioRepository;
  @Autowired
  private ArquivoRepository arquivoRepository;

  public void insert(Integer idUser, Integer idArquivo, String obs, Estagio estagio, Acao acao) throws CustomException {
      Optional<Usuario> userBD = usuarioRepository.findById(idUser);
      if (userBD.isEmpty()) {
          throw new CustomException("Usuário não identificado", HttpStatus.BAD_REQUEST);
      }

      Optional<Arquivo> arqBD = arquivoRepository.findById(idArquivo);
      if (arqBD.isEmpty()) {
          throw new CustomException("Arquivo não identificado", HttpStatus.BAD_REQUEST);
      }

      Log log = new Log();
      log.setUsuario(userBD.get());
      log.setArquivo(arqBD.get());
      log.setAcao(acao.toString().toUpperCase());
      log.setDataHora(LocalDateTime.now());
      log.setObservacao(obs);
      log.setEstagio(estagio.getDescricao());

      loggerRepository.save(log);
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

}
