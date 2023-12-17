package com.raftec.palabrita.vocabularyservice.application.exceptions;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RequiredArgsConstructor
@Getter
@FieldDefaults(makeFinal = true)
public class CollectionEntryNotFoundException extends RuntimeException {
    String collectionId;
    String keyword;
}
