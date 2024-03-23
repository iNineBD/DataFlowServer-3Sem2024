package com.dataflow.apidomrock.services;

import com.dataflow.apidomrock.entities.database.Metadata;
import com.dataflow.apidomrock.services.utils.ValidateRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Service
public class LandingZoneService {

    public List<Metadata> processUploadCSV(MultipartFile multipartFile, String delimiter) {

        List<Metadata> metadatas = new ArrayList<>();

        try {
            ValidateRequest.validateprocessUploadCSV(multipartFile, delimiter);

            InputStream in = multipartFile.getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(in));
            String line = rd.readLine().trim();

            while (line.endsWith(";")) {
                line = line.substring(0, line.length() - 1);
            }

            String[] headers = line.split(delimiter);

            for (String columName : headers) {
                metadatas.add(new Metadata(columName, null, null, null, null, null));
            }
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

        return metadatas;
    }

}
