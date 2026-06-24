package co.istad.ite.features.file.dto;

import lombok.Builder;

@Builder
public record FileUploadResponse(
        String name,
        String caption,
        Long size,
        String mediaType,
        String message,
        String status,
        //http://localhost:9990/file/istad.png
        String uri

) {
}
