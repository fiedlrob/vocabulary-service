package com.raftec.palabrita.vocabularyservice.infrastructure.repositories.specifications;

import com.raftec.palabrita.vocabularyservice.domain.model.CollectionEntry;
import org.springframework.data.jpa.domain.Specification;

public class CollectionEntrySpecification {
    private CollectionEntrySpecification() {}

    public static Specification<CollectionEntry> byParentIdAndKeyword(Long parentId, String keyword) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.and(
                criteriaBuilder.equal(root.get("parentId"), parentId),
                criteriaBuilder.equal(root.get("keyword"), keyword)
        );
    }
}
