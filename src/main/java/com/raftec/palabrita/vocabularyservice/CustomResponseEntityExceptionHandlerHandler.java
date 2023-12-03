package com.raftec.palabrita.vocabularyservice;

import com.raftec.palabrita.vocabularyservice.application.controller.exceptions.InvalidPageException;
import com.raftec.palabrita.vocabularyservice.application.controller.responses.ErrorDetails;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomResponseEntityExceptionHandlerHandler extends ResponseEntityExceptionHandler {
    private final MessageSource messageSource;

    public CustomResponseEntityExceptionHandlerHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler(InvalidPageException.class)
    public final ResponseEntity<ErrorDetails> handleUserNotFoundException(InvalidPageException ex, WebRequest request) {
        return new ResponseEntity<>(new ErrorDetails(
                messageSource.getMessage("validation.page.number.to.big.message", null, request.getLocale()),
                messageSource.getMessage("validation.page.number.to.big.details", new Object[] {
                        ex.getRequestedPage(), ex.getPageCount() }, request.getLocale())), HttpStatus.BAD_REQUEST);
    }
}
