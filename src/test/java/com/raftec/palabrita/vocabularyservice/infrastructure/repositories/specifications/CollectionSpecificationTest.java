package com.raftec.palabrita.vocabularyservice.infrastructure.repositories.specifications;

import com.raftec.palabrita.vocabularyservice.DataProvider;
import com.raftec.palabrita.vocabularyservice.TestConstants;
import com.raftec.palabrita.vocabularyservice.infrastructure.repositories.CollectionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@DataJpaTest
class CollectionSpecificationTest {
    @Autowired
    private CollectionRepository collectionRepository;

    @BeforeEach
    void setup() {
        collectionRepository.saveAll(DataProvider.getCollections());
    }

    @Test
    @DisplayName("Test the CollectionSpecification.byCollectionId method")
    void testByCollectionId() {
        var collection = collectionRepository.findOne(CollectionSpecification.byCollectionId(TestConstants.CollectionId1)).orElseThrow();
        assertThat(collection.getCollectionId(), is(TestConstants.CollectionId1));
    }

    @Test
    @DisplayName("Test the CollectionSpecification.byUserId method")
    void testByUserId() {
        var collections = collectionRepository.findAll(CollectionSpecification.byUserId(TestConstants.UserId1));
        assertThat(collections, hasSize(2));
    }

    @Test
    @DisplayName("Test the CollectionSpecification.byUserIdAndCollectionId method")
    void testByUserIdAndCollectionId(){
        var collection = collectionRepository.findOne(CollectionSpecification.byUserIdAndCollectionId(
                TestConstants.UserId1, TestConstants.CollectionId1)).orElseThrow();
        assertThat(collection.getCollectionId(), is(TestConstants.CollectionId1));
    }
}