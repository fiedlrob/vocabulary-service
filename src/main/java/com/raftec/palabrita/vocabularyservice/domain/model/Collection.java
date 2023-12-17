package com.raftec.palabrita.vocabularyservice.domain.model;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.domain.Sort;

import java.util.List;

@Entity
@Table(name = "collections")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
@JsonPropertyOrder({
        "id",
        "userId",
        "collectionId",
        "title",
        "sourceLanguage",
        "targetLanguage",
        "created"})
public class Collection {
    public static final Sort DEFAULT_SORT = Sort.by("created").ascending();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The user identifier of the collection
     */
    @Column(length = 64, nullable = false)
    private String userId;

    /**
     * Business identifier of the collection (unique), used for search and identification purposes
     */
    @Column(length = 8, nullable = false, unique = true)
    private String collectionId;

    /**
     * The date when the collection was created
     */
    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private java.sql.Timestamp created;

    /**
     * The title of the collection
     */
    @Column(length = 64, nullable = false)
    private String title;

    @ManyToOne
    @JoinColumn(name = "source_language_code")
    private Language sourceLanguage;
    @ManyToOne
    @JoinColumn(name = "target_language_code")
    private Language targetLanguage;

    @OneToMany(mappedBy = "parentId", cascade = CascadeType.ALL)
    private List<CollectionEntry> collectionEntries;
}
