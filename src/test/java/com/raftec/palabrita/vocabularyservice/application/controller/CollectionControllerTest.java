package com.raftec.palabrita.vocabularyservice.application.controller;

import com.raftec.palabrita.vocabularyservice.DataProvider;
import com.raftec.palabrita.vocabularyservice.TestConstants;
import com.raftec.palabrita.vocabularyservice.domain.model.Collection;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
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
class CollectionControllerTest extends ControllerBaseTest {
    public static final String RESOURCE_PATH = "/api/v1/collections";

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Create collection, verify validation of the sourceLanguageId field (invalid language code)")
    void TestValidationOfSourceLanguage() throws Exception {
        when(languageRepository.existsById("xx")).thenReturn(false);
        when(languageRepository.existsById("es")).thenReturn(true);

        mockMvc.perform(post(RESOURCE_PATH)
                        .header(HttpHeaders.AUTHORIZATION, accessToken)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content("{\"title\": \"Collection title\", \"collectionId\": \"012345678\", \"sourceLanguageId\": \"xx\", \"targetLanguageId\": \"es\"}"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is("Validation failed")))
                .andExpect(jsonPath("$.errors", hasSize(1)))
                .andExpect(jsonPath("$.errors[0]", is("Field: sourceLanguageId, Message: Language not supported, Value: xx")));

        mockMvc.perform(post(RESOURCE_PATH)
                        .header(HttpHeaders.AUTHORIZATION, accessToken)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content("{\"title\": \"Collection title\", \"collectionId\": \"012345678\", \"targetLanguageId\": \"es\"}"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is("Validation failed")))
                .andExpect(jsonPath("$.errors", hasSize(1)))
                .andExpect(jsonPath("$.errors[0]", is("Field: sourceLanguageId, Message: Language not supported, Value: null")));
    }

    @Test
    @DisplayName("Create collection, verify validation of the targetLanguageId field (invalid language code)")
    void TestValidationOfTargetLanguage() throws Exception {
        when(languageRepository.existsById("xx")).thenReturn(false);
        when(languageRepository.existsById("es")).thenReturn(true);

        mockMvc.perform(post(RESOURCE_PATH)
                        .header(HttpHeaders.AUTHORIZATION, accessToken)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content("{\"title\": \"Collection title\", \"collectionId\": \"012345678\", \"sourceLanguageId\": \"es\", \"targetLanguageId\": \"xx\"}"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is("Validation failed")))
                .andExpect(jsonPath("$.errors", hasSize(1)))
                .andExpect(jsonPath("$.errors[0]", is("Field: targetLanguageId, Message: Language not supported, Value: xx")));

        mockMvc.perform(post(RESOURCE_PATH)
                        .header(HttpHeaders.AUTHORIZATION, accessToken)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content("{\"title\": \"Collection title\", \"collectionId\": \"012345678\", \"sourceLanguageId\": \"es\"}"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is("Validation failed")))
                .andExpect(jsonPath("$.errors", hasSize(1)))
                .andExpect(jsonPath("$.errors[0]", is("Field: targetLanguageId, Message: Language not supported, Value: null")));
    }

    @Test
    @DisplayName("Create collection, success case")
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

        mockMvc.perform(post(RESOURCE_PATH)
                        .header(HttpHeaders.AUTHORIZATION, accessToken)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content("{\"title\": \"" + collection.getTitle() + "\"" +
                                ", \"collectionId\": \"" + collection.getCollectionId() + "\"" +
                                ", \"sourceLanguageId\": \"" + collection.getSourceLanguage().getCode() + "\"" +
                                ", \"targetLanguageId\": \"" + collection.getTargetLanguage().getCode() + "\"}"))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(header().exists(HttpHeaders.LOCATION))
                .andExpect(header().string(HttpHeaders.LOCATION,
                        "http://localhost/api/v1/collections/" + collection.getCollectionId()))
                .andExpect(jsonPath("$.title", is(collection.getTitle())))
                .andExpect(jsonPath("$.collectionId", is(collection.getCollectionId())))
                .andExpect(jsonPath("$.sourceLanguage.code", is(collection.getSourceLanguage().getCode())))
                .andExpect(jsonPath("$.targetLanguage.code", is(collection.getTargetLanguage().getCode())));
    }

    @Test
    @DisplayName("Create collection, verify validation of the title field (no title provided)")
    void TestCreateCollectionNoTitle() throws Exception {
        when(languageRepository.existsById(any(String.class))).thenReturn(true);

        mockMvc.perform(post(RESOURCE_PATH)
                        .header(HttpHeaders.AUTHORIZATION, accessToken)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
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
    @DisplayName("Create collection, verify validation of the collectionId field (collectionId already exists)")
    @SuppressWarnings("unchecked")
    void TestCreateCollectionInvalidCollectionId() throws Exception {
        when(collectionRepository.exists((Specification<Collection>) any())).thenReturn(true);
        when(languageRepository.existsById(anyString())).thenReturn(true);

        mockMvc.perform(post(RESOURCE_PATH)
                        .header(HttpHeaders.AUTHORIZATION, accessToken)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content("{\"title\": \"Collection title\", \"collectionId\": \"012345678\", \"sourceLanguageId\": \"xx\", \"targetLanguageId\": \"es\"}"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.message", is("Validation failed")))
                .andExpect(jsonPath("$.errors", hasSize(1)))
                .andExpect(jsonPath("$.errors[0]", is("Field: collectionId, Message: Collection id must be unique, Value: 012345678")));
    }
}