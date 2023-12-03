package com.raftec.palabrita.vocabularyservice.application.exceptions;

import lombok.Getter;

@Getter
public class InvalidPageException extends RuntimeException {
    private final int pageCount;
    private final int requestedPage;

    public InvalidPageException(int pageCount, int requestedPage) {
        super();

        this.pageCount = pageCount;
        this.requestedPage = requestedPage;
    }
}
