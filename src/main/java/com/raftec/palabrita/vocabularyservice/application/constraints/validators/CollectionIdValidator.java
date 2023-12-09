package com.raftec.palabrita.vocabularyservice.application.constraints.validators;

import com.raftec.palabrita.vocabularyservice.application.constraints.HasUniqueCollectionId;
import com.raftec.palabrita.vocabularyservice.application.dto.CollectionRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CollectionIdValidator implements ConstraintValidator<HasUniqueCollectionId, CollectionRequest> {
    @Override
    public boolean isValid(CollectionRequest value, ConstraintValidatorContext context) {

        return true;
    }
}
