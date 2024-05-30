package com.dataflow.apidomrock.services;

import com.dataflow.apidomrock.controllers.exceptions.CustomException;
import com.dataflow.apidomrock.entities.database.Arquivo;
import com.dataflow.apidomrock.entities.database.Metadata;
import com.dataflow.apidomrock.entities.database.Restricao;
import com.dataflow.apidomrock.entities.objectYAML.landing.LandingYAML;
import com.dataflow.apidomrock.entities.objectYAML.landing.MetadadoYAML;
import com.dataflow.apidomrock.entities.objectYAML.landing.OrganizacaoYAML;
import com.dataflow.apidomrock.repository.ArquivoRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MapperYAMLService {

    @Autowired
    ArquivoRepository arquivoRepository;

    @Transactional(readOnly = false, rollbackFor = CustomException.class)
    public Resource generateLandingYAML(String fileName, String companyDocument) throws CustomException, JsonProcessingException {

        Optional<Arquivo> arqBD = arquivoRepository.findByNameAndOrganization(fileName, companyDocument);
        StringBuilder bld = new StringBuilder();
        if (arqBD.isEmpty()) {
            throw new CustomException(bld.append("O arquivo [").append(fileName).append("] não foi encontrado.").toString(), HttpStatus.BAD_REQUEST);
        }

        LandingYAML landingYAML = new LandingYAML();
        landingYAML.setFileName(fileName);
        landingYAML.setCurrentStage(arqBD.get().getStatus());

        OrganizacaoYAML organizacaoYAML = new OrganizacaoYAML(arqBD.get().getOrganizacao().getNome(), arqBD.get().getOrganizacao().getCnpj());
        landingYAML.setOrganization(organizacaoYAML);

        List<MetadadoYAML> metadadoYAMLList = new ArrayList<>();

        for (Metadata m : arqBD.get().getMetadados()) {
            MetadadoYAML metadadoYAML = new MetadadoYAML();
            metadadoYAML.setName(m.getNome());
            metadadoYAML.setDescription(m.getDescricao());
            metadadoYAML.setType(m.getTipo());
            metadadoYAML.setDefaultValue(m.getValorPadrao());
            metadadoYAML.setActive(m.getIsAtivo());

            for (Restricao r : m.getRestricoes()) {
                if (r.getNome().equalsIgnoreCase("obrigatorio")) {
                    metadadoYAML.setRequired(Boolean.parseBoolean(r.getValor()));
                }
                if (r.getNome().equalsIgnoreCase("tamanhoMaximo")) {
                    metadadoYAML.setMaxLen(Integer.parseInt(r.getValor()));
                }
            }

            metadadoYAMLList.add(metadadoYAML);
        }
        landingYAML.setMetadatas(metadadoYAMLList);

        return new ByteArrayResource(landingYAML.toYAML().getBytes());
    }


}
