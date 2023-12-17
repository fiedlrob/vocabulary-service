package com.raftec.palabrita.vocabularyservice.infrastructure.repositories;

import com.raftec.palabrita.vocabularyservice.domain.model.Language;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LanguageRepository extends JpaRepository<Language, String> {
}