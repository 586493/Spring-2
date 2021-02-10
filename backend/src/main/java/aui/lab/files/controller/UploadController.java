package aui.lab.files.controller;

import aui.lab.files.entity.Metadata;
import aui.lab.files.service.MetadataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.time.LocalDateTime;

@RestController
@RequestMapping("api/uploads")
public class UploadController {

    private final MetadataService metadataService;

    @Autowired
    public UploadController(MetadataService metadataService) {
        this.metadataService = metadataService;
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> uploadFile(@RequestParam("author") String author,
                                           @RequestParam("description") String description,
                                           @RequestParam("file") MultipartFile file,
                                           UriComponentsBuilder builder) {
        try {
            if (author == null || description == null || file == null) throw new NullPointerException();
            final Metadata metadata = this.metadataService.saveFileMetadata(Metadata.builder()
                    .author(author)
                    .description(description)
                    .size(file.getSize())
                    .filename(file.getOriginalFilename())
                    .uploadTime(LocalDateTime.now()).build());
            this.metadataService.uploadFile(file, metadata);
            return ResponseEntity.created(builder.pathSegment("api", "downloads", "{id}")
                    .buildAndExpand(metadata.getId()).toUri()).build();
        } catch (NullPointerException | IOException | java.nio.file.InvalidPathException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.unprocessableEntity().build();
        }
    }

}
