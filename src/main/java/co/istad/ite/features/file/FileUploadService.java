package co.istad.ite.features.file;

import co.istad.ite.features.file.dto.FileDeleteResponse;
import co.istad.ite.features.file.dto.FileUploadResponse;
import co.istad.ite.features.file.dto.MultipleFileUploadResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

public interface FileUploadService {
    FileUploadResponse upload(MultipartFile file);

    MultipleFileUploadResponse uploadMultiple(MultipartFile[] files);

    FileUploadResponse findByName(String name);

    Page<FileUploadResponse> findAll(int pageNumber, int pageSize);

    FileDeleteResponse fileDelete(String fileName);
}
