package com.raftec.palabrita.vocabularyservice.application.controller;

import com.raftec.palabrita.vocabularyservice.DataProvider;
import com.raftec.palabrita.vocabularyservice.TestConstants;
import com.raftec.palabrita.vocabularyservice.domain.model.Collection;
import com.raftec.palabrita.vocabularyservice.infrastructure.repositories.CollectionRepository;
import com.raftec.palabrita.vocabularyservice.infrastructure.repositories.LanguageRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CollectionController.class)
@ComponentScan(basePackages = "com.raftec.palabrita.vocabularyservice")
@ActiveProfiles("test")
class CollectionControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CollectionRepository collectionRepository;

    @MockBean
    private LanguageRepository languageRepository;


    private static final String accessToken = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJodHRwOi8vc2NoZW1hcy54bWxzb2FwLm9yZy93cy8yMDA1LzA1L2lkZW50aXR5L2NsYWltcy9uYW1laWRlbnRpZmllciI6InRlc3R8YjhiYTQ2ZGIyOGFiZmE1NmJmYWVkODg3IiwiZXhwIjoxNzMzNTA1NDMxLCJpc3MiOiJodHRwczovL3Rlc3QtcGFsYWJyaXRhLm5ldC8iLCJhdWQiOiJodHRwczovL3BhbGFicml0YS5uZXQvYXBpIn0.ahUCdKAtnERW9LJKnQbWEMBmFdMjrF8XUfHb18el5QE";

    @Test
    @DisplayName("Verify validation of the sourceLanguageId field")
    void TestValidationOfSourceLanguage() throws Exception {
        when(languageRepository.existsById("xx")).thenReturn(false);
        when(languageRepository.existsById("es")).thenReturn(true);

        mockMvc.perform(post("/api/v1/collections")
                        .header(HttpHeaders.AUTHORIZATION, accessToken)
                        .contentType("application/json;charset=UTF-8")
                        .content("{\"title\": \"Collection title\", \"collectionId\": \"012345678\", \"sourceLanguageId\": \"xx\", \"targetLanguageId\": \"es\"}"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is("Validation failed")))
                .andExpect(jsonPath("$.errors", hasSize(1)))
                .andExpect(jsonPath("$.errors[0]", is("Field: sourceLanguageId, Message: Language not supported, Value: xx")));

        mockMvc.perform(post("/api/v1/collections")
                        .header(HttpHeaders.AUTHORIZATION, accessToken)
                        .contentType("application/json;charset=UTF-8")
                        .content("{\"title\": \"Collection title\", \"collectionId\": \"012345678\", \"targetLanguageId\": \"es\"}"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is("Validation failed")))
                .andExpect(jsonPath("$.errors", hasSize(1)))
                .andExpect(jsonPath("$.errors[0]", is("Field: sourceLanguageId, Message: Language not supported, Value: null")));
    }

    @Test
    @DisplayName("Verify validation of the targetLanguageId field")
    void TestValidationOfTargetLanguage() throws Exception {
        when(languageRepository.existsById("xx")).thenReturn(false);
        when(languageRepository.existsById("es")).thenReturn(true);

        mockMvc.perform(post("/api/v1/collections")
                        .header(HttpHeaders.AUTHORIZATION, accessToken)
                        .contentType("application/json;charset=UTF-8")
                        .content("{\"title\": \"Collection title\", \"collectionId\": \"012345678\", \"sourceLanguageId\": \"es\", \"targetLanguageId\": \"xx\"}"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is("Validation failed")))
                .andExpect(jsonPath("$.errors", hasSize(1)))
                .andExpect(jsonPath("$.errors[0]", is("Field: targetLanguageId, Message: Language not supported, Value: xx")));

        mockMvc.perform(post("/api/v1/collections")
                        .header(HttpHeaders.AUTHORIZATION, accessToken)
                        .contentType("application/json;charset=UTF-8")
                        .content("{\"title\": \"Collection title\", \"collectionId\": \"012345678\", \"sourceLanguageId\": \"es\"}"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is("Validation failed")))
                .andExpect(jsonPath("$.errors", hasSize(1)))
                .andExpect(jsonPath("$.errors[0]", is("Field: targetLanguageId, Message: Language not supported, Value: null")));
    }

    @Test
    @DisplayName("Create a collection with a valid request")
    void TestCreateCollection() throws Exception {
        var collection = DataProvider.getCollections().stream().filter(c -> c.getCollectionId().equals(
                TestConstants.CollectionId1)).findFirst().get();

        when(collectionRepository.save(any())).thenReturn(collection);

        when(languageRepository.existsById(collection.getSourceLanguage().getCode())).thenReturn(true);
        when(languageRepository.findById(collection.getSourceLanguage().getCode())).thenReturn(
                DataProvider.getLanguages().stream().filter(l -> l.getCode().equals(collection.getSourceLanguage().getCode())).findFirst());

        when(languageRepository.existsById(collection.getTargetLanguage().getCode())).thenReturn(true);
        when(languageRepository.findById(collection.getTargetLanguage().getCode())).thenReturn(
                DataProvider.getLanguages().stream().filter(l -> l.getCode().equals(collection.getTargetLanguage().getCode())).findFirst());

        mockMvc.perform(post("/api/v1/collections")
                        .header(HttpHeaders.AUTHORIZATION, accessToken)
                        .contentType("application/json;charset=UTF-8")
                        .content("{\"title\": \"" + collection.getTitle() + "\"" +
                                ", \"collectionId\": \"" + collection.getCollectionId() + "\"" +
                                ", \"sourceLanguageId\": \"" + collection.getSourceLanguage().getCode() + "\"" +
                                ", \"targetLanguageId\": \"" + collection.getTargetLanguage().getCode() + "\"}"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.title", is(collection.getTitle())))
                .andExpect(jsonPath("$.collectionId", is(collection.getCollectionId())))
                .andExpect(jsonPath("$.sourceLanguage.code", is(collection.getSourceLanguage().getCode())))
                .andExpect(jsonPath("$.targetLanguage.code", is(collection.getTargetLanguage().getCode())));
    }

    @Test
    @DisplayName("Create a collection with a invalid request (no title provided)")
    void TestCreateCollectionNoTitle() throws Exception {
        when(languageRepository.existsById(any(String.class))).thenReturn(true);

        mockMvc.perform(post("/api/v1/collections")
                        .header(HttpHeaders.AUTHORIZATION, accessToken)
                        .contentType("application/json;charset=UTF-8")
                        .content("{\"collectionId\": \"012345678\"" +
                                ", \"sourceLanguageId\": \"de\"" +
                                ", \"targetLanguageId\": \"en\"}"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is("Validation failed")))
                .andExpect(jsonPath("$.errors", hasSize(1)))
                .andExpect(jsonPath("$.errors[0]", is("Field: title, Message: must not be blank, Value: null")))
                .andExpect(jsonPath("$.path", is("/api/v1/collections")));
    }

    @Test
    @DisplayName("Create a collection with a valid request")
    @SuppressWarnings("unchecked")
    void TestCreateCollectionInvalidCollectionId() throws Exception {
        when(collectionRepository.exists((Specification<Collection>) any())).thenReturn(true);
        when(languageRepository.existsById(anyString())).thenReturn(true);

        mockMvc.perform(post("/api/v1/collections")
                        .header(HttpHeaders.AUTHORIZATION, accessToken)
                        .contentType("application/json;charset=UTF-8")
                        .content("{\"title\": \"Collection title\", \"collectionId\": \"012345678\", \"sourceLanguageId\": \"xx\", \"targetLanguageId\": \"es\"}"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.message", is("Validation failed")))
                .andExpect(jsonPath("$.errors", hasSize(1)))
                .andExpect(jsonPath("$.errors[0]", is("Field: collectionId, Message: Collection id must be unique, Value: 012345678")));
    }
}