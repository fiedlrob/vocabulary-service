package com.raftec.palabrita.vocabularyservice.domain.services;

import com.raftec.palabrita.vocabularyservice.application.dto.CollectionRequest;
import com.raftec.palabrita.vocabularyservice.application.exceptions.CollectionNotFoundException;
import com.raftec.palabrita.vocabularyservice.domain.model.Collection;
import com.raftec.palabrita.vocabularyservice.infrastructure.repositories.CollectionRepository;
import com.raftec.palabrita.vocabularyservice.infrastructure.repositories.LanguageRepository;
import com.raftec.palabrita.vocabularyservice.infrastructure.repositories.specifications.CollectionSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CollectionService implements ICollectionService {
    private final CollectionRepository collectionRepository;
    private final LanguageRepository languageRepository;

    @Override
    public Collection getCollection(String userId, String collectionId) {
        return collectionRepository.findOne(CollectionSpecification.byUserIdAndCollectionId(userId, collectionId))
                .orElseThrow(() -> new CollectionNotFoundException(collectionId));
    }

    @Override
    public List<Collection> getCollections(String userId) {
        return collectionRepository.findAll(CollectionSpecification.byUserId(userId));
    }

    @Override
    public List<Collection> getCollections(String userId, int page, int size) {

        return collectionRepository.findAll(CollectionSpecification.byUserId(userId),
                PageRequest.of(page, size, Collection.DEFAULT_SORT)).toList();
    }

    @Override
    public long getCollectionCount(String userId) {
        return collectionRepository.count(CollectionSpecification.byUserId(userId));
    }

    @Override
    public Collection createCollection(String userId, CollectionRequest collectionRequest) {
        var collection = Collection.builder()
                .userId(userId)
                .collectionId(collectionRequest.collectionId())
                .title(collectionRequest.title())
                .sourceLanguage(languageRepository.findById(collectionRequest.sourceLanguageId()).orElseThrow())
                .targetLanguage(languageRepository.findById(collectionRequest.targetLanguageId()).orElseThrow())
                .build();

        return collectionRepository.save(collection);
    }

    @Override
    public void deleteCollection(String userId, String collectionId) {
        var collection = collectionRepository.findOne(
                CollectionSpecification.byUserIdAndCollectionId(userId, collectionId));

        collection.ifPresent(collectionRepository::delete);
    }
}
