package com.raftec.palabrita.vocabularyservice.application.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import java.util.List;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"path", "message", "errors"})
public class ErrorDetails {
    @NonNull String path;
    @NonNull String message;
    List<String> errors;
}
