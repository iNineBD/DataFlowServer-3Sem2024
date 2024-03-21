package com.dataflow.apidomrock.services;

import com.dataflow.apidomrock.entities.database.Metadado;
import com.dataflow.apidomrock.entities.http.CustomResponseEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
public class LandingZoneService {

    public List<Metadado> processUploadCSV(MultipartFile multipartFile){
        return null;
    }
}
