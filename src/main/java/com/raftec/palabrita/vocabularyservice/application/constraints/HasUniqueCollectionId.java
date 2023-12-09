package com.raftec.palabrita.vocabularyservice.application.constraints;

import com.raftec.palabrita.vocabularyservice.application.constraints.validators.CollectionIdValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

/**
 * Validates that the given collection id is unique for the authenticated user.
 */
@Documented
@Constraint(validatedBy = CollectionIdValidator.class)
@Target( { ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface HasUniqueCollectionId {
    String message() default "{validation.collectionid.not.unique}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
