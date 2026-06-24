package co.istad.ite.features.file.dto;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record MultipleFileUploadResponse(
        List<FileUploadResponse> uploadedFiles,
        String name,
        int totalFiles,
        int successCount,
        int errorCount,
        List<String> errors,
        String status,
        LocalDateTime timestamp
) {
}
