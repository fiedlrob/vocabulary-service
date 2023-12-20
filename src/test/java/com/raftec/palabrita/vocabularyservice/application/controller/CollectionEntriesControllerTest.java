package com.raftec.palabrita.vocabularyservice.application.controller;

import com.raftec.palabrita.vocabularyservice.DataProvider;
import com.raftec.palabrita.vocabularyservice.TestConstants;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CollectionEntriesController.class)
class CollectionEntriesControllerTest extends ControllerBaseTest {
    public static final String RESOURCE_PATH = "/api/v1/collections/83fa95c1/entries";

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Read all collection entries")
    @SuppressWarnings("unchecked")
    void TestReadAllCollectionEntries() throws Exception {
        var collection = DataProvider.getCollections().stream().filter(c -> c.getCollectionId().equals(
                TestConstants.CollectionId1)).findFirst().get();
        when(collectionRepository.findOne(any(Specification.class))).thenReturn(Optional.of(collection));
        
        mockMvc.perform(get(RESOURCE_PATH)
                        .header(HttpHeaders.AUTHORIZATION, accessToken)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk());
    }
}