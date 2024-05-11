package com.dataflow.apidomrock.services;

import com.dataflow.apidomrock.dto.fileAnalytics.ResponseFileAnalyticDTO;
import com.dataflow.apidomrock.repository.OrganizacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AnalyticsService {

    @Autowired
    OrganizacaoRepository organizacaoRepository;

    public ResponseFileAnalyticDTO fileAnalytc(String fileName, String organizacao){
        if (fileName.equalsIgnoreCase("all")){
            //TODO: buscar metricas de todos os arquivos
            return null;
        }

        //TODO: buscar metricas de um arq em específico
        return null;
    }


    public ResponseFileAnalyticDTO findAllMetrics(String organizacao){
        organizacaoRepository.findByN()
    }

}
