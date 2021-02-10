package aui.lab.files.controller;


import aui.lab.files.dto.GetMetadataListResponse;
import aui.lab.files.dto.GetMetadataResponse;
import aui.lab.files.entity.Metadata;
import aui.lab.files.service.MetadataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.function.Function;

@RestController
@RequestMapping("api/metadata")
public class FileController {

    private final MetadataService metadataService;

    @Autowired
    public FileController(MetadataService metadataService) {
        this.metadataService = metadataService;
    }

    @GetMapping("{id}")
    public ResponseEntity<GetMetadataResponse> getMetadata(@PathVariable("id") long id) {
        return this.metadataService.find(id)
                .map(value -> ResponseEntity.ok(GetMetadataResponse.entityToDtoMapper(
                        this.metadataService::uploadTimeToString).apply(value)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<GetMetadataListResponse> getMetadataList() {
        final Function<Collection<Metadata>, GetMetadataListResponse> mapper =
                GetMetadataListResponse.entityToDtoMapper(this.metadataService::uploadTimeToString);
        return ResponseEntity.ok(mapper.apply(this.metadataService.findAll()));
    }

}
