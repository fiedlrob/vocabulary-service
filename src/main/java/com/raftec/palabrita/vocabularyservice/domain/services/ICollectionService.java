package com.raftec.palabrita.vocabularyservice.domain.services;

import com.raftec.palabrita.vocabularyservice.application.dto.CollectionEntryRequest;
import com.raftec.palabrita.vocabularyservice.application.dto.CollectionRequest;
import com.raftec.palabrita.vocabularyservice.domain.model.Collection;
import com.raftec.palabrita.vocabularyservice.domain.model.CollectionEntry;

import java.util.List;

public interface ICollectionService {
    // region Collection related methods
    /**
     * This method returns a collection by its id. The collection must belong to the user identified by userId.
     *
     * @param userId The user id.
     * @param collectionId The collection id.
     * @return The collection.
     */
    Collection getCollection(String userId, String collectionId);
    List<Collection> getCollections(String userId);
    List<Collection> getCollections(String userId, int page, int size);
    long getCollectionCount(String userId);
    Collection createCollection(String userId, CollectionRequest collectionRequest);
    void deleteCollection(String userId, String collectionId);
    // endregion

    // region Collection Entries related methods
    void deleteCollectionEntry(String userId, String collectionId, String keyword);
    CollectionEntry createCollectionEntry(String userId, String collectionId, CollectionEntryRequest collectionEntryRequest);
    // endregion
}
