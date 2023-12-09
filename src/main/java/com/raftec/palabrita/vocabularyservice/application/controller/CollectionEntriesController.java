package com.raftec.palabrita.vocabularyservice.application.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/collections/{collectionId}/entries")
@AllArgsConstructor
public class CollectionEntriesController {
}
