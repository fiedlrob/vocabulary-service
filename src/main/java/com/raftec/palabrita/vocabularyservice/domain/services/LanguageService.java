package com.raftec.palabrita.vocabularyservice.domain.services;

import com.raftec.palabrita.vocabularyservice.domain.model.Language;
import com.raftec.palabrita.vocabularyservice.infrastructure.repositories.LanguageRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LanguageService implements ILanguageService {
    private final LanguageRepository languageRepository;

    public LanguageService(LanguageRepository languageRepository) {
        this.languageRepository = languageRepository;
    }

    @Override
    public List<Language> getLanguages() {
        return languageRepository.findAll(Language.DEFAULT_SORT);
    }

    @Override
    public List<Language> getLanguages(int page, int size) {
        return languageRepository.findAll(PageRequest.of(page, size, Language.DEFAULT_SORT)).toList();
    }

    @Override
    public Optional<Language> getLanguage(String code) {
        return languageRepository.findById(code);
    }

    @Override
    public long getLanguageCount() {
        return languageRepository.count();
    }
}
