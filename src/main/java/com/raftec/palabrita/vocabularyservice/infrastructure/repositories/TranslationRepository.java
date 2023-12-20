package com.raftec.palabrita.vocabularyservice.infrastructure.repositories;

import com.raftec.palabrita.vocabularyservice.domain.model.Translation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface TranslationRepository extends
        JpaRepository<Translation, Long>, JpaSpecificationExecutor<Translation> {
}
