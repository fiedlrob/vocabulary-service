package com.raftec.palabrita.vocabularyservice.application.constraints.validators;

import com.raftec.palabrita.vocabularyservice.infrastructure.repositories.LanguageRepository;
import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LanguageValidatorTest {
    @Mock
    private LanguageRepository languageRepository;

    @InjectMocks
    private LanguageValidator languageValidator;
    private ConstraintValidatorContext context;

    @BeforeEach
    public void setUp() {
        context = mock(ConstraintValidatorContext.class);
    }

    @Test
    void testValidValue() {
        when(languageRepository.existsById("de")).thenReturn(true);
        assertTrue(languageValidator.isValid("de", context));
    }

    @Test
    void testInvalidValue() {
        when(languageRepository.existsById("xx")).thenReturn(false);
        assertFalse(languageValidator.isValid("xx", context));
    }
}