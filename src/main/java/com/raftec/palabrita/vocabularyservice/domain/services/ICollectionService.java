package com.raftec.palabrita.vocabularyservice.domain.services;

import com.raftec.palabrita.vocabularyservice.application.dto.CollectionRequest;
import com.raftec.palabrita.vocabularyservice.domain.model.Collection;

import java.util.List;

public interface ICollectionService {
    Collection getCollection(String userId, String collectionId);
    List<Collection> getCollections(String userId);
    List<Collection> getCollections(String userId, int page, int size);
    long getCollectionCount(String userId);
    Collection createCollection(String userId, CollectionRequest collectionRequest);
    void deleteCollection(String userId, String collectionId);
}
