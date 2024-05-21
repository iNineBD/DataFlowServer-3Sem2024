package com.dataflow.apidomrock.services;

import com.dataflow.apidomrock.controllers.exceptions.CustomException;
import com.dataflow.apidomrock.dto.fileAllOrganizations.OrganizationDTO;
import com.dataflow.apidomrock.dto.fileAnalytics.MetricsFilesDTO;
import com.dataflow.apidomrock.dto.fileAnalytics.MetricsUsersDTO;
import com.dataflow.apidomrock.dto.fileAnalytics.ResponseFileAnalyticDTO;
import com.dataflow.apidomrock.entities.database.Arquivo;
import com.dataflow.apidomrock.entities.database.Organizacao;
import com.dataflow.apidomrock.entities.enums.StatusArquivo;
import com.dataflow.apidomrock.repository.ArquivoRepository;
import com.dataflow.apidomrock.repository.OrganizacaoRepository;
import com.dataflow.apidomrock.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AnalyticsService {

    @Autowired
    OrganizacaoRepository organizacaoRepository;

    @Autowired
    ArquivoRepository arquivoRepository;

    @Autowired
    UsuarioRepository usuarioRepository;

    public List<OrganizationDTO> findAllOrganizations() throws CustomException {
        List<Organizacao> orgBD = organizacaoRepository.findAll();
        if (orgBD.isEmpty()) {
            throw new CustomException("Nenhuma organização encontrada", HttpStatus.BAD_REQUEST);
        }

        List<OrganizationDTO> organizacoes = new ArrayList<>();
        for (Organizacao org : orgBD) {
            organizacoes.add(new OrganizationDTO(org.getNome(),org.getCnpj()));
        }

        return organizacoes;
    }

    public ResponseFileAnalyticDTO<MetricsFilesDTO> fileAnalytc(String fileName, String cnpj) throws CustomException {
        if (fileName.equalsIgnoreCase("all")) {
            return new ResponseFileAnalyticDTO<>(findAllByEtapa());
        }
        return new ResponseFileAnalyticDTO<>(findOneByOrganization(cnpj));
    }


    public List<MetricsFilesDTO> findAllByEtapa() throws CustomException {

        List<MetricsFilesDTO> metricsFilesDTOList = new ArrayList<>();
        Map<String, Integer> metrica = new HashMap<>(Map.of("LZ", 0, "BZ", 0, "SZ", 0));

        List<Organizacao> orgBD = organizacaoRepository.findAll();
        if (orgBD.isEmpty()) {
            throw new CustomException("Nenhuma organização encontrada", HttpStatus.BAD_REQUEST);
        }

        for (Organizacao o : orgBD) {
            List<Arquivo> arqsBD = arquivoRepository.findAllByOrganizacao_Cnpj(o.getCnpj());
            if (arqsBD.isEmpty()) {
                continue;
            }

            for (Arquivo a : arqsBD) {
                switch (StatusArquivo.fromString(a.getStatus())) {
                    case NAO_APROVADO_PELA_BRONZE -> metrica.put("LZ", metrica.get("LZ") + 1);
                    case BRONZE_ZONE, AGUARDANDO_APROVACAO_BRONZE, NAO_APROVADO_PELA_SILVER ->
                            metrica.put("BZ", metrica.get("BZ") + 1);
                    case AGUARDANDO_APROVACAO_SILVER, SILVER_ZONE, FINALIZADO -> metrica.put("SZ", metrica.get("SZ") + 1);
                }
            }

        }
        metricsFilesDTOList.add(new MetricsFilesDTO("ALL", metrica));
        return metricsFilesDTOList;
    }

    public List<MetricsFilesDTO> findOneByOrganization(String cnpj) throws CustomException {

        List<MetricsFilesDTO> metricsFilesDTOList = new ArrayList<>();
        Map<String, Integer> metrica = new HashMap<>(Map.of("LZ", 0, "BZ", 0, "SZ", 0));

        Optional<Organizacao> orgBD = organizacaoRepository.findById(cnpj);
        if (orgBD.isEmpty()) {
            throw new CustomException("Organização não encontrada", HttpStatus.BAD_REQUEST);
        }

        List<Arquivo> arqsBD = arquivoRepository.findAllByOrganizacao_Cnpj(cnpj);
        if (arqsBD.isEmpty()) {
            metricsFilesDTOList.add(new MetricsFilesDTO(orgBD.get().getNome(), metrica));
            return metricsFilesDTOList;
        }

        for (Arquivo a : arqsBD) {
            switch (StatusArquivo.fromString(a.getStatus())) {
                case NAO_APROVADO_PELA_BRONZE -> metrica.put("LZ", metrica.get("LZ") + 1);
                case BRONZE_ZONE, AGUARDANDO_APROVACAO_BRONZE, NAO_APROVADO_PELA_SILVER ->
                        metrica.put("BZ", metrica.get("BZ") + 1);
                case AGUARDANDO_APROVACAO_SILVER, SILVER_ZONE, FINALIZADO -> metrica.put("SZ", metrica.get("SZ") + 1);
            }
        }
        metricsFilesDTOList.add(new MetricsFilesDTO(orgBD.get().getNome(), metrica));
        return metricsFilesDTOList;
    }


    public List<MetricsUsersDTO> findAllUsers() throws CustomException {

        List<Organizacao> orgBD = organizacaoRepository.findAll();
        if (orgBD.isEmpty()) {
            throw new CustomException("Nenhuma organização encontrada", HttpStatus.BAD_REQUEST);
        }

        List<MetricsUsersDTO> metricsUsersDTOList = new ArrayList<>();
        for (Organizacao o : orgBD) {
            Integer n = usuarioRepository.countAllByOrganizacao(o);
            MetricsUsersDTO m = new MetricsUsersDTO(o.getNome(), n);
            metricsUsersDTOList.add(m);
        }

        return metricsUsersDTOList;
    }
}
