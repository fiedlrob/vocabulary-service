package com.raftec.palabrita.vocabularyservice;

import com.raftec.palabrita.vocabularyservice.application.dto.ErrorDetails;
import com.raftec.palabrita.vocabularyservice.application.exceptions.CollectionNotFoundException;
import com.raftec.palabrita.vocabularyservice.application.exceptions.InvalidPageException;
import jakarta.validation.constraints.NotNull;
import lombok.NonNull;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class VocabularyServiceExceptionHandlerHandler extends ResponseEntityExceptionHandler {
    private final MessageSource messageSource;

    public VocabularyServiceExceptionHandlerHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ResponseBody
    @ExceptionHandler(InvalidPageException.class)
    public final ResponseEntity<ErrorDetails> handleUserNotFoundException(@NonNull InvalidPageException ex,
                                                                          @NonNull WebRequest request) {
        return new ResponseEntity<>(ErrorDetails.builder()
                .message(messageSource.getMessage("validation.page.number.to.big.message", null, request.getLocale()))
                .errors(List.of(messageSource.getMessage("validation.page.number.to.big.details", new Object[]{
                        ex.getRequestedPage(), ex.getPageCount()}, request.getLocale())))
                .path(((ServletWebRequest)request).getRequest().getRequestURI().toLowerCase()).build(),
                HttpStatus.BAD_REQUEST);
    }

    @ResponseBody
    @ExceptionHandler(CollectionNotFoundException.class)
    public final ResponseEntity<ErrorDetails> handleCollectionNotFoundException(@NonNull CollectionNotFoundException ex,
                                                                                @NotNull WebRequest request) {
        return new ResponseEntity<>(ErrorDetails.builder()
                .message(messageSource.getMessage("validation.collection.not.found.message", null, request.getLocale()))
                .errors(List.of(messageSource.getMessage("validation.collection.not.found.details", new Object[]{
                        ex.getCollectionId()}, request.getLocale())))
                .path(((ServletWebRequest)request).getRequest().getRequestURI().toLowerCase()).build(),
                HttpStatus.NOT_FOUND);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  @NonNull HttpHeaders headers,
                                                                  @NonNull HttpStatusCode status,
                                                                  @NonNull WebRequest request) {
        var errors = new ArrayList<String>();

        ex.getBindingResult().getFieldErrors().forEach(fieldError -> errors.add(String.format("Field: %s, Message: %s, Value: %s",
                fieldError.getField(),
                fieldError.getDefaultMessage(),
                fieldError.getRejectedValue())));

        ex.getBindingResult().getGlobalErrors().forEach(objectError -> errors.add(String.format("Object: %s, Message: %s",
                objectError.getObjectName(),
                objectError.getDefaultMessage())));

        return super.handleExceptionInternal(ex,
                ErrorDetails.builder()
                        .message("Validation failed")
                        .errors(errors)
                        .path(((ServletWebRequest)request).getRequest().getRequestURI().toLowerCase()).build(), headers, status, request);
    }
}
