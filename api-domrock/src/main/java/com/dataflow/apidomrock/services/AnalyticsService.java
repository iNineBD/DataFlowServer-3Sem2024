package com.dataflow.apidomrock.services;

import com.dataflow.apidomrock.controllers.exceptions.CustomException;
import com.dataflow.apidomrock.dto.fileAllOrganizations.OrganizationDTO;
import com.dataflow.apidomrock.dto.fileAnalytics.Metrics;
import com.dataflow.apidomrock.dto.fileAnalytics.ResponseFileAnalyticDTO;
import com.dataflow.apidomrock.entities.database.Arquivo;
import com.dataflow.apidomrock.entities.database.Organizacao;
import com.dataflow.apidomrock.entities.enums.StatusArquivo;
import com.dataflow.apidomrock.repository.ArquivoRepository;
import com.dataflow.apidomrock.repository.OrganizacaoRepository;
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

    public ResponseFileAnalyticDTO fileAnalytc(String fileName, String cnpj) throws CustomException {
        if (fileName.equalsIgnoreCase("all")) {
            return new ResponseFileAnalyticDTO(findAllByEtapa());
        }
        return new ResponseFileAnalyticDTO(findOneByOrganization(cnpj));
    }




    public List<Metrics> findAllByEtapa() throws CustomException {

        List<Metrics> metricsList = new ArrayList<>();
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
                    case AGUARDANDO_APROVACAO_SILVER, SILVER_ZONE -> metrica.put("SZ", metrica.get("SZ") + 1);
                }
            }

        }
        metricsList.add(new Metrics("ALL", metrica));
        return metricsList;
    }

    public List<Metrics> findOneByOrganization(String cnpj) throws CustomException {

        List<Metrics> metricsList = new ArrayList<>();
        Map<String, Integer> metrica = new HashMap<>(Map.of("LZ", 0, "BZ", 0, "SZ", 0));

        Optional<Organizacao> orgBD = organizacaoRepository.findById(cnpj);
        if (orgBD.isEmpty()) {
            throw new CustomException("Organização não encontrada", HttpStatus.BAD_REQUEST);
        }

        List<Arquivo> arqsBD = arquivoRepository.findAllByOrganizacao_Cnpj(cnpj);
        if (arqsBD.isEmpty()) {
            metricsList.add(new Metrics(orgBD.get().getNome(), metrica));
            return metricsList;
        }

        for (Arquivo a : arqsBD) {
            switch (StatusArquivo.fromString(a.getStatus())) {
                case NAO_APROVADO_PELA_BRONZE -> metrica.put("LZ", metrica.get("LZ") + 1);
                case BRONZE_ZONE, AGUARDANDO_APROVACAO_BRONZE, NAO_APROVADO_PELA_SILVER ->
                        metrica.put("BZ", metrica.get("BZ") + 1);
                case AGUARDANDO_APROVACAO_SILVER, SILVER_ZONE -> metrica.put("SZ", metrica.get("SZ") + 1);
            }
        }
        metricsList.add(new Metrics(orgBD.get().getNome(), metrica));
        return metricsList;
    }
}
