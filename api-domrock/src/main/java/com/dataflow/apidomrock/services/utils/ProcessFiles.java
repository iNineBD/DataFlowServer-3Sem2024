package com.dataflow.apidomrock.services.utils;

import com.dataflow.apidomrock.controllers.exceptions.CustomException;
import com.dataflow.apidomrock.dto.entitiesdto.MetadataDTO;
import com.dataflow.apidomrock.dto.processuploadcsv.ResponseUploadCSVDTO;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.HttpStatus;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ProcessFiles {
    public static ResponseUploadCSVDTO processCSVFile(MultipartFile multipartFile, String delimiter) throws IOException, CustomException {
        //lendo o arquivo
        BufferedReader rd = new BufferedReader(new InputStreamReader(multipartFile.getInputStream()));

        //pego o cabeçalho (nome das colunas)
        String line = rd.readLine().trim();

        if (line.isEmpty()){
            throw new CustomException("Não foi possivel identificar o header do arquivo.", HttpStatus.BAD_REQUEST);
        }

        //isso foi feito para minimizar problemas do tipo: CSV não integro
        while (line.endsWith(";")) {
            line = line.substring(0, line.length() - 1);
        }
        String[] headers = line.split(delimiter);

        List<MetadataDTO> metadatas = new ArrayList<>();
        //para cada coluna, crio o Metadado equivalente e ja adiciono numa lista
        for (String columName : headers) {
            metadatas.add(new MetadataDTO(null, columName, null, null, null, null, null, null));
        }

        double fileSize = (double) multipartFile.getSize() / (1024 * 1024);

        return new ResponseUploadCSVDTO(multipartFile.getOriginalFilename(), fileSize, metadatas);
    }

    public static ResponseUploadCSVDTO processExcelFile(MultipartFile file) throws CustomException {
        ResponseUploadCSVDTO response = new ResponseUploadCSVDTO();
        List<MetadataDTO> metadatas = new ArrayList<>();

        try (Workbook workbook = new XSSFWorkbook(file.getInputStream())) {
            Sheet sheet = workbook.getSheetAt(0);
            Row headerRow = sheet.getRow(0);
            if (headerRow != null) {
                for (Cell cell : headerRow) {
                    if (!cell.getStringCellValue().isEmpty() && cell.getStringCellValue() != null){
                        metadatas.add(new MetadataDTO(null, cell.getStringCellValue(), null, null, null, null, null, null));
                    }
                }
            } else {
                throw new RuntimeException("Não foi possível encontrar o cabeçalho do arquivo");
            }
        } catch (IOException | RuntimeException e) {
            throw new CustomException(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        double fileSize = (double) file.getSize() / (1024 * 1024);

        return new ResponseUploadCSVDTO(file.getOriginalFilename(), fileSize, metadatas);
    }
}
