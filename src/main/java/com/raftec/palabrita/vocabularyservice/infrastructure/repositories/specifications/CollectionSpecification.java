package com.raftec.palabrita.vocabularyservice.infrastructure.repositories.specifications;

import com.raftec.palabrita.vocabularyservice.domain.model.Collection;
import org.springframework.data.jpa.domain.Specification;

public class CollectionSpecification {
    private CollectionSpecification() {}

    public static Specification<Collection> byUserId(String userId) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("userId"), userId);
    }

    public static Specification<Collection> byUserIdAndCollectionId(String userId, String collectionId) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.and(
                criteriaBuilder.equal(root.get("userId"), userId),
                criteriaBuilder.equal(root.get("collectionId"), collectionId)
        );
    }
}
