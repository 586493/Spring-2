package aui.lab.files.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@ToString(exclude = "description")
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Entity
@Table(name = "files_metadata")
public class Metadata {
    /**
     * Unique id (primary key).
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String author;

    @Column(nullable = false)
    private String description;

    /**
     * Size of the file in bytes.
     */
    @Column(nullable = false)
    private Long size;

    @Column(nullable = false)
    private String filename;

    @Column(nullable = false, name = "upload_datetime", columnDefinition = "TIMESTAMP")
    private LocalDateTime uploadTime;
}
