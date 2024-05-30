package com.dataflow.apidomrock.services.utils;

import com.dataflow.apidomrock.controllers.exceptions.CustomException;
import com.dataflow.apidomrock.dto.entitiesdto.MetadataDTO;
import com.dataflow.apidomrock.dto.processuploadcsv.ResponseUploadCSVDTO;
import com.dataflow.apidomrock.dto.uploadsilver.MetadadosDeParaVisualizeSilver;
import com.dataflow.apidomrock.dto.uploadsilver.ResponseDeParasSilver;
import com.dataflow.apidomrock.dto.visualizeDePara.DeParasVisualize;
import com.dataflow.apidomrock.dto.visualizeDePara.MetadadosDeParaVisualize;
import com.dataflow.apidomrock.entities.database.Arquivo;
import com.dataflow.apidomrock.entities.database.DePara;
import com.dataflow.apidomrock.entities.database.Metadata;
import com.dataflow.apidomrock.repository.ArquivoRepository;
import com.dataflow.apidomrock.repository.DeParaRepository;
import com.dataflow.apidomrock.repository.MetadataRepository;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;


@Component
public class ProcessFiles {

    @Autowired
    ArquivoRepository arquivoRepository;
    @Autowired
    DeParaRepository deParaRepository;
    @Autowired
    MetadataRepository metadataRepository;

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

    public List<MetadadosDeParaVisualize> processCSVFileWithHeaderToSilver(MultipartFile multipartFile, String delimiter, boolean header, String cnpj, String nomeArquivo) throws IOException, CustomException {
        BufferedReader rd = new BufferedReader(new InputStreamReader(multipartFile.getInputStream(), "UTF-8"));
        String line;

        Optional<Arquivo> arquivo = arquivoRepository.findByNameAndOrganization(nomeArquivo, cnpj);
        if (arquivo.isEmpty()) {
            throw new CustomException("Não foi possível identificar o arquivo passado.", HttpStatus.BAD_REQUEST);
        } else {
            List<MetadadosDeParaVisualize> metadadosList = new ArrayList<>();

            if (header) {
                // Ignora a primeira linha se header é verdadeiro
                rd.readLine();
            }

            while ((line = rd.readLine()) != null) {
                line = line.trim();
                while (line.endsWith(delimiter)) {
                    line = line.substring(0, line.length() - 1);
                }
                String[] content = line.split(delimiter);
                if (content.length != 3) {
                    throw new CustomException("O arquivo CSV não está no formato de 3 colunas, por favor, ajuste!", HttpStatus.BAD_REQUEST);
                }

                String metadado = content[0];
                String de = content[1];
                String para = content[2];

                // Verifica se já existe um MetadadosDeParaVisualize com o mesmo nome de metadado
                Optional<MetadadosDeParaVisualize> existingMetadados = metadadosList.stream()
                        .filter(metadados -> metadados.nome().equals(metadado))
                        .findFirst();

                if (existingMetadados.isPresent()) {
                    existingMetadados.get().dePara().add(new DeParasVisualize(de, para));
                } else {
                    MetadadosDeParaVisualize metadados = new MetadadosDeParaVisualize(
                            new ArrayList<>(List.of(new DeParasVisualize(de, para))), metadado);
                    metadadosList.add(metadados);
                }
            }

            return metadadosList;
        }
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

    public List<MetadadosDeParaVisualize> processCSVFileNotHeaderToSilver(MultipartFile multipartFile, String delimiter, boolean header, String cnpj, String nomeArquivo) throws IOException, CustomException {
        // Lendo o arquivo
        BufferedReader rd = new BufferedReader(new InputStreamReader(multipartFile.getInputStream(), "UTF-8"));
        String line = "";

        Optional<Arquivo> arquivo = arquivoRepository.findByNameAndOrganization(nomeArquivo, cnpj);

        if (arquivo.isEmpty()) {
            throw new CustomException("Não foi possível identificar o arquivo passado.", HttpStatus.BAD_REQUEST);
        } else {

            List<MetadadosDeParaVisualize> metadadosList = new ArrayList<>();

            if (!header) {
                while ((line = rd.readLine()) != null) {
                    if (!line.trim().isEmpty()) {
                        break;
                    }
                }
            }
            if (line == null || line.isEmpty()) {
                throw new CustomException("Não foi possível identificar o header do arquivo.", HttpStatus.BAD_REQUEST);
            }

            // Isso foi feito para minimizar problemas do tipo: CSV não íntegro
            while (line.endsWith(";")) {
                line = line.substring(0, line.length() - 1);
            }

            while ((line = rd.readLine()) != null) {
                line = line.trim();
                while (line.endsWith(delimiter)) {
                    line = line.substring(0, line.length() - 1);
                }
                String[] content = line.split(delimiter);
                if (content.length != 3) {
                    throw new CustomException("O arquivo CSV não está no formato de 3 colunas, por favor, ajuste!", HttpStatus.BAD_REQUEST);
                }

                // Criar Metadados usando as 3 colunas
                String metadado = content[0];
                String de = content[1];
                String para = content[2];

                // Verifica se já existe um MetadadosDeParaVisualize com o mesmo nome de metadado
                Optional<MetadadosDeParaVisualize> existingMetadados = metadadosList.stream()
                        .filter(metadados -> metadados.nome().equals(metadado))
                        .findFirst();

                if (existingMetadados.isPresent()) {
                    existingMetadados.get().dePara().add(new DeParasVisualize(de, para));
                } else {
                    MetadadosDeParaVisualize metadados = new MetadadosDeParaVisualize(
                            new ArrayList<>(List.of(new DeParasVisualize(de, para))), metadado);
                    metadadosList.add(metadados);
                }
            }
            return metadadosList;
        }
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

    public List<MetadadosDeParaVisualize>  processExcelFileWithHeaderToSilver(MultipartFile file, String cnpj, String nomeArquivo) throws IOException, CustomException {
        Optional<Arquivo> arquivo = arquivoRepository.findByNameAndOrganization(nomeArquivo, cnpj);

        if (arquivo.isEmpty()) {
            throw new CustomException("Não foi possível identificar o arquivo passado.", HttpStatus.BAD_REQUEST);
        }

        List<MetadadosDeParaVisualize> metadadosList = new ArrayList<>();

        Workbook workbook = new XSSFWorkbook(file.getInputStream());
        Sheet sheet = workbook.getSheetAt(0);
        Row headerRow = sheet.getRow(0);

        if (headerRow == null) {
            throw new CustomException("O arquivo Excel está vazio ou não contém cabeçalho.", HttpStatus.BAD_REQUEST);
        }

        int numberOfColumns = headerRow.getPhysicalNumberOfCells();
        if (numberOfColumns != 3) {
            throw new CustomException("O arquivo Excel não está no formato de 3 colunas, por favor, ajuste!", HttpStatus.BAD_REQUEST);
        }

        // Agora processa as linhas, assumindo que todas têm 3 colunas
        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            Row currentRow = sheet.getRow(i);
            if (currentRow == null || currentRow.getPhysicalNumberOfCells() != 3) {
                throw new CustomException("O arquivo Excel contém uma linha fora do formato de 3 colunas.", HttpStatus.BAD_REQUEST);
            }

            String metadado = currentRow.getCell(0).getStringCellValue().toUpperCase().trim();
            String de = currentRow.getCell(1).getStringCellValue().trim();
            String para = currentRow.getCell(2).getStringCellValue().trim();

            // Verifica se já existe um MetadadosDeParaVisualize com o mesmo nome de metadado
            Optional<MetadadosDeParaVisualize> existingMetadados = metadadosList.stream()
                    .filter(metadados -> metadados.nome().equals(metadado))
                    .findFirst();

            if (existingMetadados.isPresent()) {
                existingMetadados.get().dePara().add(new DeParasVisualize(de, para));
            } else {
                MetadadosDeParaVisualize metadados = new MetadadosDeParaVisualize(
                        new ArrayList<>(List.of(new DeParasVisualize(de, para))), metadado);
                metadadosList.add(metadados);
            }
        }
        return metadadosList;
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

    public List<MetadadosDeParaVisualize> processExcelFileWithOutHeaderToSilver(MultipartFile file, String cnpj, String nomeArquivo) throws CustomException, IOException {

        Optional<Arquivo> arquivo = arquivoRepository.findByNameAndOrganization(nomeArquivo, cnpj);

        if (arquivo.isEmpty()) {
            throw new CustomException("Não foi possível identificar o arquivo passado.", HttpStatus.BAD_REQUEST);
        }

        Workbook workbook = new XSSFWorkbook(file.getInputStream());
        Sheet sheet = workbook.getSheetAt(0);
        Row headerRow = sheet.getRow(0);

        int numberOfColumns = headerRow.getPhysicalNumberOfCells();
        if (numberOfColumns != 3) {
            throw new CustomException("O arquivo Excel não está no formato de 3 colunas, por favor, ajuste!", HttpStatus.BAD_REQUEST);
        }

        List<MetadadosDeParaVisualize> metadadosList = new ArrayList<>();

        // Agora processa as linhas, assumindo que todas têm 3 colunas
        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            Row currentRow = sheet.getRow(i);
            if (currentRow == null || currentRow.getPhysicalNumberOfCells() != 3) {
                throw new CustomException("O arquivo Excel contém uma linha fora do formato de 3 colunas.", HttpStatus.BAD_REQUEST);
            }

            String metadado = currentRow.getCell(0).getStringCellValue().toUpperCase().trim();
            String de = currentRow.getCell(1).getStringCellValue().trim();
            String para = currentRow.getCell(2).getStringCellValue().trim();

            // Verifica se já existe um MetadadosDeParaVisualize com o mesmo nome de metadado
            Optional<MetadadosDeParaVisualize> existingMetadados = metadadosList.stream()
                    .filter(metadados -> metadados.nome().equals(metadado))
                    .findFirst();

            if (existingMetadados.isPresent()) {
                existingMetadados.get().dePara().add(new DeParasVisualize(de, para));
            } else {
                MetadadosDeParaVisualize metadados = new MetadadosDeParaVisualize(
                        new ArrayList<>(List.of(new DeParasVisualize(de, para))), metadado);
                metadadosList.add(metadados);
            }
        }
        return metadadosList;
    }

    private static String getColumnName(Row headerRow, int columnIndex) {
        Cell headerCell = headerRow.getCell(columnIndex);
        if (headerCell != null && headerCell.getCellType() == CellType.STRING) {
            return headerCell.getStringCellValue();
        }
        return "";
    }
}
