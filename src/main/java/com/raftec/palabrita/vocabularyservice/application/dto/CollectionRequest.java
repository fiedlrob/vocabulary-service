package com.raftec.palabrita.vocabularyservice.application.dto;

import com.raftec.palabrita.vocabularyservice.application.constraints.IsUniqueCollectionId;
import com.raftec.palabrita.vocabularyservice.application.constraints.IsValidLanguage;
import jakarta.validation.constraints.NotBlank;

/**
 * This class represents a collection request.
 *
 * @since 0.0.1
 */
public record CollectionRequest(
        @NotBlank String title,
        @IsUniqueCollectionId String collectionId,
        @IsValidLanguage String sourceLanguageId,
        @IsValidLanguage String targetLanguageId) {
}
