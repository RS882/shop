package de.aittr.g_38_jp_shop.service;

import de.aittr.g_38_jp_shop.service.interfaces.FileService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {
    @Override
    public String upload(MultipartFile file, String productTitle) {

        try {

            String uniqueFileName = generateUniqueFileName(file);

            Files.copy(file.getInputStream(),
                    Path.of("C:\\Back_work\\shop\\file\\"
                            + file.getOriginalFilename()));
            return uniqueFileName;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private String generateUniqueFileName(MultipartFile file) {
        String sourceFileName = file.getOriginalFilename();
        int dotIndex = sourceFileName.lastIndexOf(".");
        String fileName = sourceFileName.substring(0, dotIndex);
        String extension = sourceFileName.substring(dotIndex);

        return String.format("%s-%s%s", fileName, UUID.randomUUID(), extension);
    }
}
