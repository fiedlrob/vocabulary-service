package com.raftec.palabrita.vocabularyservice.application.constraints.validators;

import com.raftec.palabrita.vocabularyservice.domain.model.Collection;
import com.raftec.palabrita.vocabularyservice.infrastructure.repositories.CollectionRepository;
import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.domain.Specification;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CollectionIdValidatorTest {
    @Mock
    private CollectionRepository collectionRepository;
    @InjectMocks
    private CollectionIdValidator collectionIdValidator;
    private ConstraintValidatorContext context;

    @BeforeEach
    public void setUp() {
        context = mock(ConstraintValidatorContext.class);
    }

    @Test
    @SuppressWarnings("unchecked")
    void testValidValue() {
        when(collectionRepository.exists((Specification<Collection>) any())).thenReturn(false);
        assertTrue(collectionIdValidator.isValid("012345678", context));
    }

    @Test
    @SuppressWarnings("unchecked")
    void testInvalidValue() {
        when(collectionRepository.exists((Specification<Collection>) any())).thenReturn(true);
        assertFalse(collectionIdValidator.isValid("012345678", context));
    }
}