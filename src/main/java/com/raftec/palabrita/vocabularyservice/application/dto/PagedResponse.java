package com.raftec.palabrita.vocabularyservice.application.dto;

import java.util.List;

public record PagedResponse<T>(
    int pageCount,
    int pageNumber,
    int totalCount,
    int totalPages,
    boolean hasPreviousPage,
    boolean hasNextPage,
    List<T> items)
{}
