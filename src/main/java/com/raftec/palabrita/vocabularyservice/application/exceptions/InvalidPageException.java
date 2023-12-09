package com.raftec.palabrita.vocabularyservice.application.exceptions;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RequiredArgsConstructor
@Getter
@FieldDefaults(makeFinal = true)
public class InvalidPageException extends RuntimeException {
    int pageCount;
    int requestedPage;
}
