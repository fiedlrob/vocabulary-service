package com.raftec.palabrita.vocabularyservice.domain.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "translations")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Translation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="collection_entry_id", nullable=false)
    CollectionEntry collectionEntry;

    @Column(name = "translation_value", length = 128, nullable = false)
    private String value;
}
