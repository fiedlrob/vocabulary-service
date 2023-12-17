package com.raftec.palabrita.vocabularyservice.application.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CollectionEntryResponse extends RepresentationModel<CollectionEntryResponse> {
    private final String keyword;
    private List<String> translations;
}
