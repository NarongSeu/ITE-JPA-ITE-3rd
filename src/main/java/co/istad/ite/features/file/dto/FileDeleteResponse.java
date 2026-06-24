package co.istad.ite.features.file.dto;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record FileDeleteResponse(
        String fileName,
        String name,
        Boolean deleted,
        String message,
        LocalDateTime timestamp
) {
}
