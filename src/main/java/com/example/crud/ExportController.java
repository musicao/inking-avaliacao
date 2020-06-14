package com.example.crud;

import org.apache.catalina.connector.Response;
import org.apache.commons.compress.utils.IOUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping({"/excel"})
public class ExportController {

    private UsuariosRepository repository;

    ExportController(UsuariosRepository usuariosRepository) {
        this.repository = usuariosRepository;
    }

    @GetMapping
    public void getFile(HttpServletResponse response) {
        try {

            List dadosList = repository.findAll();

            final String FILE_SAVE_LOCATION = "./";
            final String FILE_NAME = "UserReport.xlsx";

            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet sheet = workbook.createSheet("Report");
            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("ID");
            headerRow.createCell(1).setCellValue("Name");
            headerRow.createCell(2).setCellValue("Email");
            headerRow.createCell(3).setCellValue("Data de Nascimento");
            headerRow.createCell(4).setCellValue("Tipo");
            headerRow.createCell(5).setCellValue("Endereço");

            int rowCount = 0;

            for (Object user : dadosList) {

                Row row = sheet.createRow(++rowCount);

                Cell idCell = row.createCell(0);
                idCell.setCellValue(((Usuarios) user).getId());

                Cell nomeCell = row.createCell(1);
                nomeCell.setCellValue(((Usuarios) user).getNome());

                Cell emailCell = row.createCell(2);
                emailCell.setCellValue(((Usuarios) user).getEmail());

                Cell dtCell = row.createCell(3);
                dtCell.setCellValue(((Usuarios) user).getData_de_nascimento());

                Cell tipoCell = row.createCell(4);
                tipoCell.setCellValue(((Usuarios) user).getTipo());

                Cell enderecoCell = row.createCell(5);
                enderecoCell.setCellValue(((Usuarios) user).getEndereco());

            }

            try (FileOutputStream outputStream = new FileOutputStream(FILE_SAVE_LOCATION + FILE_NAME)) {
                workbook.write(outputStream);
                workbook.close();

                InputStream is = new FileInputStream(FILE_SAVE_LOCATION + FILE_NAME);

                IOUtils.copy(is, response.getOutputStream());
                response.flushBuffer();

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (Exception e) {
            System.out.println(e);
        }

    }

}