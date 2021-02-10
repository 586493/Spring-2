package aui.lab.files.controller;

import aui.lab.files.entity.Metadata;
import aui.lab.files.service.MetadataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Files;
import java.util.Optional;

@RestController
@RequestMapping("api/downloads")
public class DownloadController {

    private final MetadataService metadataService;

    @Autowired
    public DownloadController(MetadataService metadataService) {
        this.metadataService = metadataService;
    }

    @GetMapping(value = "{id}")
    public ResponseEntity<byte[]> downloadFile(@PathVariable("id") long id) {
        try {
            Optional<Metadata> mdOpt = this.metadataService.find(id);
            if (mdOpt.isEmpty()) return ResponseEntity.notFound().build();
            final Metadata metadata = mdOpt.get();
            return ResponseEntity.ok()
                    .headers(this.metadataService.createHeaders(metadata))
                    .contentLength(metadata.getSize())
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(Files.readAllBytes(this.metadataService.getFileLocation(metadata)));
        } catch (IOException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.unprocessableEntity().build();
        }
    }

}
