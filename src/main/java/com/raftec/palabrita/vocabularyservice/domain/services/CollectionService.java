package com.raftec.palabrita.vocabularyservice.domain.services;

import com.raftec.palabrita.vocabularyservice.application.dto.CollectionEntryRequest;
import com.raftec.palabrita.vocabularyservice.application.dto.CollectionRequest;
import com.raftec.palabrita.vocabularyservice.application.exceptions.CollectionNotFoundException;
import com.raftec.palabrita.vocabularyservice.domain.model.Collection;
import com.raftec.palabrita.vocabularyservice.domain.model.CollectionEntry;
import com.raftec.palabrita.vocabularyservice.domain.model.Translation;
import com.raftec.palabrita.vocabularyservice.infrastructure.repositories.CollectionEntryRepository;
import com.raftec.palabrita.vocabularyservice.infrastructure.repositories.CollectionRepository;
import com.raftec.palabrita.vocabularyservice.infrastructure.repositories.LanguageRepository;
import com.raftec.palabrita.vocabularyservice.infrastructure.repositories.specifications.CollectionEntrySpecification;
import com.raftec.palabrita.vocabularyservice.infrastructure.repositories.specifications.CollectionSpecification;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true)
public class CollectionService implements ICollectionService {
    LanguageRepository languageRepository;
    CollectionRepository collectionRepository;
    CollectionEntryRepository collectionEntryRepository;

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

    @Override
    public void deleteCollectionEntry(String userId, String collectionId, String keyword) {
        collectionRepository.findOne(CollectionSpecification.byUserIdAndCollectionId(userId, collectionId))
                .flatMap(collection -> collectionEntryRepository.findOne(
                        CollectionEntrySpecification.byParentIdAndKeyword(
                                collection.getId(), keyword))).ifPresent(collectionEntryRepository::delete);
    }

    @Override
    public CollectionEntry createCollectionEntry(String userId, String collectionId, CollectionEntryRequest collectionEntryRequest) {
        var collection = collectionRepository.findOne(CollectionSpecification.byUserIdAndCollectionId(userId, collectionId))
                .orElseThrow(() -> new CollectionNotFoundException(collectionId));

        var collectionEntry = CollectionEntry.builder()
                .keyword(collectionEntryRequest.keyword())
                .parentId(collection.getId())
                .build();

        collectionEntry.setTranslations((Arrays.stream(collectionEntryRequest.translations())
                .map(translation -> Translation.builder().value(translation).collectionEntry(collectionEntry).build())
                .toList()));

        collection.getCollectionEntries().add(collectionEntry);

        collectionRepository.save(collection);

        return collectionEntry;
    }
}
