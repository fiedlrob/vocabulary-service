package com.raftec.palabrita.vocabularyservice.application.dto;

import com.raftec.palabrita.vocabularyservice.domain.model.Language;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.hateoas.RepresentationModel;

import java.util.Date;

@Getter
@Setter
@FieldDefaults(makeFinal = true)
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CollectionResponse extends RepresentationModel<CollectionResponse> {
    String collectionId;
    String title;
    Language sourceLanguage;
    Language targetLanguage;
    Date created;
}
