package aui.lab.files.dto;

import aui.lab.files.entity.Metadata;
import lombok.*;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class GetMetadataListResponse {

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @ToString
    @EqualsAndHashCode
    public static class Data {
        private Long id;
        private Long size;
        private String filename;
        private String uploadTime;
    }

    @Singular("metadataList")
    private List<Data> metadataList;

    public static Function<Collection<Metadata>, GetMetadataListResponse> entityToDtoMapper(
            Function<Metadata, String> uploadTimeToStrFun) {
        return collection -> {
            GetMetadataListResponseBuilder response = GetMetadataListResponse.builder();
            collection.stream()
                    .map(md -> Data.builder()
                            .id(md.getId())
                            .size(md.getSize())
                            .filename(md.getFilename())
                            .uploadTime(uploadTimeToStrFun.apply(md))
                            .build())
                    .forEach(response::metadataList);
            return response.build();
        };
    }


}
