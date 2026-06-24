package co.istad.ite.features.file;
import co.istad.ite.features.file.dto.FileDeleteResponse;
import co.istad.ite.features.file.dto.FileUploadResponse;
import co.istad.ite.features.file.dto.MultipleFileUploadResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
@Service
@RequiredArgsConstructor
public class FileUploadServiceImpl implements FileUploadService {

    @Value("${file.storage-location}")
    private String storageLocation;

    @Value("${file.base-uri}")
    private String baseUri;


    @Override
    public FileUploadResponse upload(MultipartFile file) {

        // Prepare file information
        // File name
        String name = UUID.randomUUID().toString();

        // mypro.file.png
        String ext = file.getOriginalFilename()
                .substring(file.getOriginalFilename().lastIndexOf(".") + 1);

        name += "." + ext; // new-unique-filename.ext

        // Create absolute path to store file
        Path path = Paths.get(storageLocation + name);

        try {
            Files.copy(file.getInputStream(), path);
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "File has been failed to upload");
        }

        return FileUploadResponse.builder()
                .name(name)
                .size(file.getSize())
                .mediaType(file.getContentType())
                .uri(baseUri + name)
                .build();
    }

    @Override
    public MultipleFileUploadResponse uploadMultiple(MultipartFile[] files) {
        List<FileUploadResponse> uploadedFiles = new ArrayList<>();
        List<String> errors = new ArrayList<>();
        int successCount = 0;
        int errorCount = 0;

        for (MultipartFile file : files) {
            try {
                // Check if file is empty
                if (file.isEmpty()) {
                    errors.add("File is empty: " + file.getOriginalFilename());
                    errorCount++;
                    continue;
                }

                // Upload the file using existing upload method
                FileUploadResponse response = upload(file);
                uploadedFiles.add(response);
                successCount++;

            } catch (Exception e) {
                errors.add("Failed to upload: " + file.getOriginalFilename() + " - " + e.getMessage());
                errorCount++;
            }
        }

        return MultipleFileUploadResponse.builder()
                .uploadedFiles(uploadedFiles)
                .name("batch-upload-" + System.currentTimeMillis())
                .totalFiles(files.length)
                .successCount(successCount)
                .errorCount(errorCount)
                .errors(errors)
                .status(errorCount > 0 ? "PARTIAL_SUCCESS" : "SUCCESS")
                .timestamp(LocalDateTime.now())
                .build();
    }

    @Override
    public FileDeleteResponse fileDelete(String fileName) {
        try {
            // Construct the full path to the file
            Path filePath = Paths.get(storageLocation + fileName);
            File file = filePath.toFile();

            // Check if file exists
            if (!file.exists()) {
                return FileDeleteResponse.builder()
                        .fileName(fileName)
                        .deleted(false)
                        .message("File not found: " + fileName)
                        .timestamp(LocalDateTime.now())
                        .build();
            }

            // Delete the file
            boolean deleted = Files.deleteIfExists(filePath);

            return FileDeleteResponse.builder()
                    .fileName(fileName)
                    .name(fileName)
                    .deleted(deleted)
                    .message(deleted ? "File deleted successfully" : "Failed to delete file")
                    .timestamp(LocalDateTime.now())
                    .build();

        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Failed to delete file: " + e.getMessage());
        }
    }
}