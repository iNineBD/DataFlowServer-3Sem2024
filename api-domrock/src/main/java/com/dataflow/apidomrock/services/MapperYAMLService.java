package com.dataflow.apidomrock.services;

import com.dataflow.apidomrock.controllers.exceptions.CustomException;
import com.dataflow.apidomrock.entities.database.Arquivo;
import com.dataflow.apidomrock.entities.database.DePara;
import com.dataflow.apidomrock.entities.database.Metadata;
import com.dataflow.apidomrock.entities.database.Restricao;
import com.dataflow.apidomrock.entities.objectYAML.bronze.BronzeYAML;
import com.dataflow.apidomrock.entities.objectYAML.landing.LandingYAML;
import com.dataflow.apidomrock.entities.objectYAML.landing.MetadadoYAML;
import com.dataflow.apidomrock.entities.objectYAML.landing.OrganizacaoYAML;
import com.dataflow.apidomrock.entities.objectYAML.silver.MetadadoToFrom;
import com.dataflow.apidomrock.entities.objectYAML.silver.SilverYAML;
import com.dataflow.apidomrock.entities.objectYAML.silver.ToFrom;
import com.dataflow.apidomrock.repository.ArquivoRepository;
import com.dataflow.apidomrock.repository.DeParaRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MapperYAMLService {

    @Autowired
    ArquivoRepository arquivoRepository;

    @Autowired
    DeParaRepository deParaRepository;

    @Transactional(readOnly = false, rollbackFor = CustomException.class)
    public ResponseEntity<Resource> generateLandingYAML(String fileName, String companyDocument) throws CustomException, JsonProcessingException {

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

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=landing.yaml");
        headers.add(HttpHeaders.CONTENT_TYPE, "application/x-yaml");
        headers.add("fileName", arqBD.get().getNomeArquivo().split("\\.")[0] + ".yml");
        return new ResponseEntity<>(new ByteArrayResource(landingYAML.toYAML().getBytes()), headers, HttpStatus.OK);

    }


    @Transactional(readOnly = false, rollbackFor = CustomException.class)
    public ResponseEntity<Resource> generateSilverYAML(String fileName, String companyDocument) throws CustomException, JsonProcessingException {

        Optional<Arquivo> arqBD = arquivoRepository.findByNameAndOrganization(fileName, companyDocument);
        StringBuilder bld = new StringBuilder();
        if (arqBD.isEmpty()) {
            throw new CustomException(bld.append("O arquivo [").append(fileName).append("] não foi encontrado.").toString(), HttpStatus.BAD_REQUEST);
        }

        SilverYAML silverToYaml = new SilverYAML();
        silverToYaml.setFileName(fileName);
        silverToYaml.setCurrentStage(arqBD.get().getStatus());

        OrganizacaoYAML organizacaoYAML = new OrganizacaoYAML(arqBD.get().getOrganizacao().getNome(), arqBD.get().getOrganizacao().getCnpj());
        silverToYaml.setOrganization(organizacaoYAML);

        List<MetadadoToFrom> deparaToYaml = new ArrayList<>();
        for (Metadata m : arqBD.get().getMetadados()) {
            MetadadoToFrom aux = new MetadadoToFrom();
            aux.setName(m.getNome());

            List<DePara> deparasBD =  deParaRepository.findByIdMetadado(m.getID());

            List<ToFrom> temp = new ArrayList<>();
            for (DePara deparaBD : deparasBD) {
                temp.add(new ToFrom(deparaBD.getDe(), deparaBD.getPara()));
            }
            if (temp.size() > 0) {
                aux.setToFrom(temp);
                deparaToYaml.add(aux);
            }

        }

        silverToYaml.setMetadadoToFrom(deparaToYaml);

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=silver.yaml");
        headers.add(HttpHeaders.CONTENT_TYPE, "application/x-yaml");
        headers.add("fileName", arqBD.get().getNomeArquivo().split("\\.")[0] + ".yml");
        return new ResponseEntity<>(new ByteArrayResource(silverToYaml.toYAML().getBytes()), headers, HttpStatus.OK);
    }

    @Transactional(readOnly = false, rollbackFor = CustomException.class)
    public ResponseEntity<Resource> generateBronzeYAML(String fileName, String companyDocument) throws CustomException, JsonProcessingException {

        Optional<Arquivo> arqBD = arquivoRepository.findByNameAndOrganization(fileName, companyDocument);
        StringBuilder bld = new StringBuilder();
        if (arqBD.isEmpty()) {
            throw new CustomException(bld.append("O arquivo [").append(fileName).append("] não foi encontrado.").toString(), HttpStatus.BAD_REQUEST);
        }

        BronzeYAML bronzeYAML = new BronzeYAML();
        bronzeYAML.setFileName(fileName);
        bronzeYAML.setCurrentStage(arqBD.get().getStatus());

        OrganizacaoYAML organizacaoYAML = new OrganizacaoYAML(arqBD.get().getOrganizacao().getNome(), arqBD.get().getOrganizacao().getCnpj());
        bronzeYAML.setOrganization(organizacaoYAML);

        List<String> metadados = new ArrayList<>();
        for (Metadata m : arqBD.get().getHash()) {
            metadados.add(m.getNome());
        }

        bronzeYAML.setHash(metadados);

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=bronze.yaml");
        headers.add(HttpHeaders.CONTENT_TYPE, "application/x-yaml");
        headers.add("fileName", arqBD.get().getNomeArquivo().split("\\.")[0] + ".yml");
        return new ResponseEntity<>(new ByteArrayResource(bronzeYAML.toYAML().getBytes()), headers, HttpStatus.OK);

    }

}
