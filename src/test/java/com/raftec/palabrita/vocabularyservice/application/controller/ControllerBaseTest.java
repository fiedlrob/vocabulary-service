package com.raftec.palabrita.vocabularyservice.application.controller;

import com.raftec.palabrita.vocabularyservice.infrastructure.repositories.CollectionEntryRepository;
import com.raftec.palabrita.vocabularyservice.infrastructure.repositories.CollectionRepository;
import com.raftec.palabrita.vocabularyservice.infrastructure.repositories.LanguageRepository;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = {
        "com.raftec.palabrita.vocabularyservice"
})
public class ControllerBaseTest {
    @MockBean
    protected CollectionRepository collectionRepository;
    @MockBean
    protected LanguageRepository languageRepository;
    @MockBean
    protected CollectionEntryRepository collectionEntryRepository;

    protected static final String accessToken = "Bearer " +
            "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJodHRwOi8vc2NoZW1hcy54bWx" +
            "zb2FwLm9yZy93cy8yMDA1LzA1L2lkZW50aXR5L2NsYWltcy9uYW1laWRlbnRpZml" +
            "lciI6InRlc3R8YjhiYTQ2ZGIyOGFiZmE1NmJmYWVkODg3IiwiZXhwIjoxNzMzNTA" +
            "1NDMxLCJpc3MiOiJodHRwczovL3Rlc3QtcGFsYWJyaXRhLm5ldC8iLCJhdWQiOiJ" +
            "odHRwczovL3BhbGFicml0YS5uZXQvYXBpIn0.ahUCdKAtnERW9LJKnQbWEMBmFdM" +
            "jrF8XUfHb18el5QE";

}
