package co.istad.ite.features.file;

import co.istad.ite.features.file.dto.FileUploadResponse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FileUplaodRepository extends JpaRepository<FileUpload, Long> {
    Optional<FileUpload> findByName(String name);
}
