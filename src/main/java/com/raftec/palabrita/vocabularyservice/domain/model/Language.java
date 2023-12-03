package com.raftec.palabrita.vocabularyservice.domain.model;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import org.springframework.data.domain.Sort;

@Entity
@Table(name = "languages")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@JsonPropertyOrder({"code", "name", "endonym"})
public class Language {
    public static final Sort DEFAULT_SORT = Sort.by("code").ascending();

    /**
     * The ISO 639-1 code for the language
     */
    @Id
    private String code;

    /**
     * The name of the language in English
     */
    @Column(length = 64, nullable = false)
    private String name;

    /**
     * The name of the language in the language itself
     */
    @Column(length = 64, nullable = false)
    private String endonym;
}
