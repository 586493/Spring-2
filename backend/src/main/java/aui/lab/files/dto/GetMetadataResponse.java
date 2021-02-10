package aui.lab.files.dto;


import aui.lab.files.entity.Metadata;
import lombok.*;

import java.util.function.Function;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class GetMetadataResponse {

    private Long id;
    private String author;
    private String description;
    private Long size;
    private String filename;
    private String uploadTime;

    public static Function<Metadata, GetMetadataResponse> entityToDtoMapper(
            Function<Metadata, String> uploadTimeToStrFun) {
        return md -> GetMetadataResponse.builder()
                .id(md.getId())
                .author(md.getAuthor())
                .description(md.getDescription())
                .size(md.getSize())
                .filename(md.getFilename())
                .uploadTime(uploadTimeToStrFun.apply(md))
                .build();
    }

}
