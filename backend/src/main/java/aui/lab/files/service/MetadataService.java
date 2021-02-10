package aui.lab.files.service;

import aui.lab.files.entity.Metadata;
import aui.lab.files.repository.MetadataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import static java.text.MessageFormat.format;

@Service
public class MetadataService {
    private final static String DATETIME_FORMAT = "HH:mm:ss dd/MM/yyyy";
    private final static DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(DATETIME_FORMAT);
    private final static String FILENAME_FORMAT = "{0}_{1}";

    private final String storageLocation;

    private final MetadataRepository repository;

    @Autowired
    public MetadataService(@Value("${app.storage.location}") String storageLocation, MetadataRepository repository) {
        this.repository = repository;
        this.storageLocation = storageLocation;
    }

    public Optional<Metadata> find(Long id) {
        return this.repository.findById(id);
    }

    public List<Metadata> findAll() {
        return this.repository.findAll();
    }

    @Transactional
    public Metadata saveFileMetadata(Metadata fileMetadata) {
        return this.repository.save(makeFilenameUnique(fileMetadata));
    }

    @Transactional
    public void uploadFile(MultipartFile file, Metadata fileMetadata) throws IOException, InvalidPathException {
        file.transferTo(getFileLocation(fileMetadata));
    }

    public Metadata makeFilenameUnique(Metadata fileMetadata) {
        File file;
        int i = 0;
        final String currName = fileMetadata.getFilename();
        do {
            i++;
            if (i > 1) fileMetadata.setFilename(format(FILENAME_FORMAT, i, currName));
            file = getFileLocation(fileMetadata).toFile();
        } while (file.exists());
        if (i == 1) fileMetadata.setFilename(currName);
        return fileMetadata;
    }

    public Path getFileLocation(Metadata fileMetadata) {
        final File storageLocation = new File(this.storageLocation);
        if (!storageLocation.exists()) storageLocation.mkdirs();
        return storageLocation.toPath().resolve(fileMetadata.getFilename());
    }

    public String uploadTimeToString(Metadata fileMetadata) {
        return DATE_TIME_FORMATTER.format(fileMetadata.getUploadTime());
    }

    public HttpHeaders createHeaders(Metadata fileMetadata) {
        final HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileMetadata.getFilename());
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");
        return headers;
    }
}
