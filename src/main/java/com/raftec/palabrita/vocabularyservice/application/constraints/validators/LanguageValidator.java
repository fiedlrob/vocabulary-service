package com.raftec.palabrita.vocabularyservice.application.constraints.validators;

import com.raftec.palabrita.vocabularyservice.application.constraints.IsValidLanguage;
import com.raftec.palabrita.vocabularyservice.infrastructure.repositories.LanguageRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * This class is used to validate if a language is supported by the application.
 *
 * @since 0.0.1
 */
@Component
@RequiredArgsConstructor
public class LanguageValidator implements ConstraintValidator<IsValidLanguage, String> {
    private final LanguageRepository languageRepository;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return false;
        }

        if (value.length() != 2) {
            return false;
        }

        return languageRepository.existsById(value);
    }
}
