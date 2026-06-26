package co.istad.ite.features.file;

import co.istad.ite.features.file.dto.FileUploadResponse;
import co.istad.ite.features.file.dto.MultipleFileUploadResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/files")
@RequiredArgsConstructor
public class FileUploadController {

    private final FileUplaodRepository fileUplaodRepository;


    @GetMapping("/{name}")
    public FileUploadResponse findByName(@PathVariable String name) {
        return fileUploadService.findByName(name);
    }

    @GetMapping
    public Page<FileUploadResponse> findAll(
            @RequestParam(required = false, defaultValue = "0") int pageNumber,
            @RequestParam(required = false, defaultValue = "25") int pageSize
    ) {
        return fileUploadService.findAll(pageNumber, pageSize);
    }

    private final FileUploadService fileUploadService;
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public FileUploadResponse upload(@RequestPart MultipartFile file) {
        return fileUploadService.upload(file);
    }


    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/multiple", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public List<FileUploadResponse> uploadMultiple(@RequestPart MultipartFile[] files) {
        log.info("Uploading {} files", files.length);
        return fileUploadService.uploadMultiple(files).uploadedFiles();
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{name}")
    public void deleteFile(@PathVariable String name) {
        log.info("Deleting file: {}", name);
        fileUploadService.fileDelete(name);
    }
}
