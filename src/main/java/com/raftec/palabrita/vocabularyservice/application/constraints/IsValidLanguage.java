package com.raftec.palabrita.vocabularyservice.application.constraints;

import com.raftec.palabrita.vocabularyservice.application.constraints.validators.LanguageValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

/**
 * This annotation is used to validate if a language is supported by the application
 * The language is specified by an ISO 639-1 code two letter language code.
 *
 * @version 0.0.1
 */
@Documented
@Constraint(validatedBy = LanguageValidator.class)
@Target( { ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface IsValidLanguage {
    String message() default "{validation.language.not.supported}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
