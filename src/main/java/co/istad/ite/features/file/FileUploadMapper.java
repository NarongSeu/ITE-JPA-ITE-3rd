package co.istad.ite.features.file;

import co.istad.ite.features.file.dto.FileUploadResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class FileUploadMapper {

    @Value("${file.base-uri}")
    private String baseUri;

    public FileUploadResponse mapFileUploadToFileUploadResponse(FileUpload fileUpload) {
        return FileUploadResponse.builder()
                .name(fileUpload.getName())
                .extension(fileUpload.getExtension())
                .size(fileUpload.getSize())
                .mediaType(fileUpload.getMediaType())
                .uri(baseUri + fileUpload.getName() + "." + fileUpload.getExtension())
                .build();
    }
}
