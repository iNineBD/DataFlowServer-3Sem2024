package com.dataflow.apidomrock.services;

import com.dataflow.apidomrock.dto.UploadCSVResponseDTO;
import com.dataflow.apidomrock.entities.database.Metadata;
import com.dataflow.apidomrock.services.utils.ValidateRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Service
public class LandingZoneService {

    public UploadCSVResponseDTO processUploadCSV(MultipartHttpServletRequest request, String delimiter) throws IOException {

        List<Metadata> metadatas = new ArrayList<>();
        MultipartFile multipartFile;

        //realiza validacoes nos parametros da request (se o arquivo existe, está ok...)
        //Se estiver ruim, internamente é lançada uma exceção que o controller trata pelo advice
        multipartFile = ValidateRequest.validateprocessUploadCSV(request, delimiter);

        //lendo o arquivo
        InputStream in = multipartFile.getInputStream();
        BufferedReader rd = new BufferedReader(new InputStreamReader(in));

        //pego o cabeçalho (nome das colunas)
        String line = rd.readLine().trim();

        //isso foi feito para minimizar problemas do tipo: CSV não integro
        while (line.endsWith(";")) {
            line = line.substring(0, line.length() - 1);
        }

        //divido o nome das colunas pelo delimitador especificado
        String[] headers = line.split(delimiter);

        //para cada coluna, crio o Metadado equivalente e ja adiciono numa lista
        for (String columName : headers) {
            metadatas.add(new Metadata(columName, null, null, null, null, null));
        }

        if (multipartFile == null) {
            throw new RuntimeException("Erro ao interpretar o arquivo inserido");
        }

        double fileSize = (double) multipartFile.getSize() / (1024 * 1024);

        return new UploadCSVResponseDTO(multipartFile.getOriginalFilename(), fileSize, metadatas);
    }

}
