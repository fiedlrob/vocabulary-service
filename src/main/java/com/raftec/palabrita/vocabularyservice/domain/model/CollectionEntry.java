package com.raftec.palabrita.vocabularyservice.domain.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.domain.Sort;

import java.util.List;

@Entity
@Table(name = "collection_entries")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class CollectionEntry {
    public static final Sort DEFAULT_SORT = Sort.by("keyword").ascending();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Long parentId;

    @Column(length = 128, nullable = false)
    private String keyword;

    @OneToMany(mappedBy = "collectionEntry", cascade = CascadeType.ALL)
    private List<Translation> translations;

    public List<String> getTranslations() {
        return translations.stream().map(Translation::getValue).toList();
    }
}
