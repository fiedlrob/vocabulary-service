package com.raftec.palabrita.vocabularyservice.application.controller.responses;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
@JsonPropertyOrder({"pageNumber", "pageCount",  "totalPages", "totalCount", "hasPreviousPage", "hasNextPage", "value"})
public class PagedResponse<T> {
    private List<T> items;
    private int pageCount;
    private int pageNumber;
    private int totalCount;
    private int totalPages;
    private boolean hasPreviousPage;
    private boolean hasNextPage;
}
