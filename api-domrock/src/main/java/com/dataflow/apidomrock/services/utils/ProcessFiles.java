package com.dataflow.apidomrock.services.utils;

import com.dataflow.apidomrock.controllers.exceptions.CustomException;
import com.dataflow.apidomrock.dto.entitiesdto.MetadataDTO;
import com.dataflow.apidomrock.dto.processuploadcsv.ResponseUploadCSVDTO;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.HttpStatus;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ProcessFiles {
    public static ResponseUploadCSVDTO processCSVFileWithHeader(MultipartFile multipartFile, String delimiter, boolean header) throws IOException, CustomException {
        //lendo o arquivo
        BufferedReader rd = new BufferedReader(new InputStreamReader(multipartFile.getInputStream(), "UTF-8"));
        String line = "";

        if (header){
            while ((line = rd.readLine()) != null){
               if (!line.trim().isEmpty()){
                   break;
               }
            }
        }
        if (line.isEmpty()){
            throw new CustomException("Não foi possivel identificar o header do arquivo.", HttpStatus.BAD_REQUEST);
        }

        //isso foi feito para minimizar problemas do tipo: CSV não integro
        while (line.endsWith(";")) {
            line = line.substring(0, line.length() - 1);
        }
        String[] headers = line.split(delimiter);

        line = rd.readLine().trim();
        while (line.endsWith(";")) {
            line = line.substring(0, line.length() - 1);
        }
        String[] content1 = line.split(delimiter);

        List<MetadataDTO> metadatas = new ArrayList<>();
        //para cada coluna, crio o Metadado equivalente e ja adiciono numa lista
        int count = 0;
        for (String columName : headers) {
            String sample1 = content1[count];
            metadatas.add(new MetadataDTO(null, columName, null, null, sample1, null, null, null, null));
            count++;
        }

        double fileSize = (double) multipartFile.getSize() / (1024 * 1024);

        return new ResponseUploadCSVDTO(multipartFile.getOriginalFilename(), fileSize, metadatas);
    }

    public static ResponseUploadCSVDTO processCSVFileNotHeader(MultipartFile multipartFile, String delimiter, boolean header) throws IOException, CustomException {
        //lendo o arquivo
        BufferedReader rd = new BufferedReader(new InputStreamReader(multipartFile.getInputStream(), "UTF-8"));
        String line = "";

        if (!header){
            while ((line = rd.readLine()) != null){
                if (!line.trim().isEmpty()){
                    break;
                }
            }
        }
        if (line.isEmpty()){
            throw new CustomException("Não foi possivel manipular o arquivo.", HttpStatus.BAD_REQUEST);
        }

        line = rd.readLine().trim();
        while (line.endsWith(";")) {
            line = line.substring(0, line.length() - 1);
        }
        String[] content1 = line.split(delimiter);

        List<MetadataDTO> metadatas = new ArrayList<>();
        //para cada coluna, crio o Metadado equivalente e ja adiciono numa lista
        int count = 0;
        for (String columName : content1) {

            String sample1 = content1[count];
            metadatas.add(new MetadataDTO(null, "sample_column", null, null, sample1, null, null, null, null));
            count++;
        }

        double fileSize = (double) multipartFile.getSize() / (1024 * 1024);

        return new ResponseUploadCSVDTO(multipartFile.getOriginalFilename(), fileSize, metadatas);
    }

    public static ResponseUploadCSVDTO processExcelFileWithHeader(MultipartFile file) throws CustomException {
        ResponseUploadCSVDTO response = new ResponseUploadCSVDTO();
        List<MetadataDTO> metadatas = new ArrayList<>();

        try (Workbook workbook = new XSSFWorkbook(file.getInputStream())) {
            Sheet sheet = workbook.getSheetAt(0);
            Row headerRow = null;

            int count = 0;
            for (Row currentRow : sheet) {

                if (count > 1){
                    break;
                }
                if (currentRow == null) {
                    continue;
                }

                if (count == 0){
                    headerRow = currentRow;
                    count++;
                    continue;
                }

                for (Cell cell : currentRow) {

                    String cellValue;
                    // Verifica se a célula contém uma data
                    if (cell.getCellType() == CellType.NUMERIC && DateUtil.isCellDateFormatted(cell)) {
                        Date dateCellValue = cell.getDateCellValue();
                        // Formata a data para o formato desejado (por exemplo, "dd/MM/yyyy")
                        cellValue = new SimpleDateFormat("dd/MM/yyyy").format(dateCellValue);
                    } else {
                        // Trata todas as células como texto
                        cell.setCellType(CellType.STRING);
                        cellValue = cell.getStringCellValue();
                    }
                    if (!cellValue.isEmpty()){
                        metadatas.add(new MetadataDTO(null, getColumnName(headerRow, cell.getColumnIndex()), null, null, cellValue, null, null, null, null));
                    } else {
                        metadatas.add(new MetadataDTO(null, getColumnName(headerRow, cell.getColumnIndex()), null, null, "", null, null, null, null));
                    }
                }
                count++;
            }
        } catch (IOException | RuntimeException e) {
            throw new CustomException(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        double fileSize = (double) file.getSize() / (1024 * 1024);

        return new ResponseUploadCSVDTO(file.getOriginalFilename(), fileSize, metadatas);
    }

    public static ResponseUploadCSVDTO processExcelFileWithOutHeader(MultipartFile file) throws CustomException {

        List<MetadataDTO> metadatas = new ArrayList<>();

        try (Workbook workbook = new XSSFWorkbook(file.getInputStream())) {
            Sheet sheet = workbook.getSheetAt(0);
            int count = 0;
            for (Row currentRow : sheet) {
                if (count > 0){
                    break;
                }
                for (Cell cell : currentRow) {
                    cell.setCellType(CellType.STRING);
                    if (!cell.getStringCellValue().isEmpty() && cell.getStringCellValue() != null){
                        metadatas.add(new MetadataDTO(null, "sample_column", null, null, cell.getStringCellValue(), null, null, null, null));
                    }
                }
                count++;
            }
        } catch (IOException | RuntimeException e) {
            throw new CustomException(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        double fileSize = (double) file.getSize() / (1024 * 1024);

        return new ResponseUploadCSVDTO(file.getOriginalFilename(), fileSize, metadatas);
    }

    private static String getColumnName(Row headerRow, int columnIndex) {
        Cell headerCell = headerRow.getCell(columnIndex);
        if (headerCell != null && headerCell.getCellType() == CellType.STRING) {
            return headerCell.getStringCellValue();
        }
        return "";
    }
}
