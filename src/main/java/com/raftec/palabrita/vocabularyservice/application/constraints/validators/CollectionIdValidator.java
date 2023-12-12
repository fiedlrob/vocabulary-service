package com.raftec.palabrita.vocabularyservice.application.constraints.validators;

import com.raftec.palabrita.vocabularyservice.application.constraints.IsUniqueCollectionId;
import com.raftec.palabrita.vocabularyservice.infrastructure.repositories.CollectionRepository;
import com.raftec.palabrita.vocabularyservice.infrastructure.repositories.specifications.CollectionSpecification;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RequiredArgsConstructor
@FieldDefaults(makeFinal = true)
public class CollectionIdValidator implements ConstraintValidator<IsUniqueCollectionId, String> {
    CollectionRepository collectionRepository;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return !collectionRepository.exists(CollectionSpecification.byCollectionId(value));
    }
}
