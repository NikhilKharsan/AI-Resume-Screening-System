package com.scaler.resumescreener.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Slf4j
@Component
public class FileUtil {

    public String saveFile(MultipartFile file, String uploadPath) throws IOException {
        Files.createDirectories(Paths.get(uploadPath));
        String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();
        String filePath = uploadPath + File.separator + filename;

        try (FileOutputStream fos = new FileOutputStream(filePath)) {
            fos.write(file.getBytes());
        }

        return filePath;
    }

    public String extractTextFromPdf(String filePath) {
        try (PDDocument document = PDDocument.load(new File(filePath))) {
            PDFTextStripper stripper = new PDFTextStripper();
            return stripper.getText(document);
        } catch (IOException e) {
            log.error("Failed to extract text from PDF: {}", filePath, e);
            throw new RuntimeException("Failed to extract text from PDF");
        }
    }
}
