package com.raftec.palabrita.vocabularyservice.domain.services;

import com.raftec.palabrita.vocabularyservice.domain.model.Language;

import java.util.List;
import java.util.Optional;

public interface ILanguageService {
    List<Language> getLanguages();
    List<Language> getLanguages(int page, int size);
    Optional<Language> getLanguage(String code);

    long getLanguageCount();
}
