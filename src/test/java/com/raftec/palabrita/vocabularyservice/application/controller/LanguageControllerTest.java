package com.raftec.palabrita.vocabularyservice.application.controller;

import com.raftec.palabrita.vocabularyservice.DataProvider;
import com.raftec.palabrita.vocabularyservice.domain.model.Language;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(LanguageController.class)
class LanguageControllerTest extends ControllerBaseTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Read the first page of languages and check that the first and last languages are correct")
    void TestFirstLanguagePage() throws Exception {
        List<Language> languages = DataProvider.getLanguages().stream()
                .limit(10)
                .collect(Collectors.toList());

        when(languageRepository.count()).thenReturn(104L);
        when(languageRepository.findAll(any(PageRequest.class))).thenReturn(new PageImpl<>(languages));

        mockMvc.perform(get("/api/v1/languages?size=10&page=1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.pageNumber", is(1)))
                .andExpect(jsonPath("$.pageCount", is(10)))
                .andExpect(jsonPath("$.totalPages", is(11)))
                .andExpect(jsonPath("$.totalCount", is(104)))
                .andExpect(jsonPath("$.hasPreviousPage", is(false)))
                .andExpect(jsonPath("$.hasNextPage", is(true)))
                .andExpect(jsonPath("$.items", hasSize(10)))
                .andExpect(jsonPath("$.items[0].code", is("af")))
                .andExpect(jsonPath("$.items[0].name", is("Afrikaans")))
                .andExpect(jsonPath("$.items[0].endonym", is("Afrikaans")))
                .andExpect(jsonPath("$.items[9].code", is("co")))
                .andExpect(jsonPath("$.items[9].name", is("Corsican")))
                .andExpect(jsonPath("$.items[9].endonym", is("Corsu")));
    }

    @Test
    @DisplayName("Read the last page of languages and check that the first and last languages are correct")
    void TestLastLanguagePage() throws Exception {
        List<Language> languages = DataProvider.getLanguages().stream()
                .skip(100)
                .limit(4)
                .collect(Collectors.toList());

        when(languageRepository.count()).thenReturn(104L);
        when(languageRepository.findAll(any(PageRequest.class))).thenReturn(new PageImpl<>(languages));

        mockMvc.perform(get("/api/v1/languages?size=10&page=11"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.pageNumber", is(11)))
                .andExpect(jsonPath("$.pageCount", is(4)))
                .andExpect(jsonPath("$.totalPages", is(11)))
                .andExpect(jsonPath("$.totalCount", is(104)))
                .andExpect(jsonPath("$.hasPreviousPage", is(true)))
                .andExpect(jsonPath("$.hasNextPage", is(false)))
                .andExpect(jsonPath("$.items", hasSize(4)))
                .andExpect(jsonPath("$.items[0].code", is("yi")))
                .andExpect(jsonPath("$.items[0].name", is("Yiddish")))
                .andExpect(jsonPath("$.items[0].endonym", is("ייִדיש")))
                .andExpect(jsonPath("$.items[3].code", is("zu")))
                .andExpect(jsonPath("$.items[3].name", is("Zulu")))
                .andExpect(jsonPath("$.items[3].endonym", is("isiZulu")));
    }

    @Test
    @DisplayName("Read all languages and check that the first and last languages are correct")
    void TestGetAllLanguages() throws Exception {
        when(languageRepository.findAll(any(Sort.class))).thenReturn(DataProvider.getLanguages());

        mockMvc.perform(get("/api/v1/languages"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$", hasSize(104)))
                .andExpect(jsonPath("$[0].code", is("af")))
                .andExpect(jsonPath("$[0].name", is("Afrikaans")))
                .andExpect(jsonPath("$[0].endonym", is("Afrikaans")))
                .andExpect(jsonPath("$[103].code", is("zu")))
                .andExpect(jsonPath("$[103].name", is("Zulu")))
                .andExpect(jsonPath("$[103].endonym", is("isiZulu")));

    }

    @Test
    @DisplayName("Read a language by its code and check that the language is correct")
    void TestGetLanguageByCode() throws Exception {
        when(languageRepository.findById("en")).thenReturn(
                DataProvider.getLanguages().stream().filter(l -> l.getCode().equals("en")).findFirst());

        mockMvc.perform(get("/api/v1/languages/en"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.code", is("en")))
                .andExpect(jsonPath("$.name", is("English")))
                .andExpect(jsonPath("$.endonym", is("English")));
    }

    @Test
    @DisplayName("Read a language by unknown code and check that the response is 404")
    void TestGetLanguageByUnknownCode() throws Exception {
        when(languageRepository.findById("xx")).thenReturn(
                DataProvider.getLanguages().stream().filter(l -> l.getCode().equals("xx")).findFirst());

        mockMvc.perform(get("/api/v1/languages/xx"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @ParameterizedTest
    @DisplayName("Read languages paginated with invalid page and check that the response is 400")
    @ValueSource(ints = {0, 12})
    void TestGetLanguagesPaginatedWithInvalidPage(int page) throws Exception {
        when(languageRepository.count()).thenReturn(104L);

        // At the moment we have two different error messages depending on the page number (0 or greater than the total number of pages)
        // We will consolidate this in the future to have a single error message
        mockMvc.perform(get("/api/v1/languages?size=10&page=" + page))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(page == 0
                        ? jsonPath("$.title", is("Bad Request"))
                        : jsonPath("$.message", is("Page number too big")))
                .andExpect(page == 0
                        ? jsonPath("$.detail", is("Validation failure"))
                        : jsonPath("$.errors[0]", is("The requested page number of 12 is greater than the total number of pages 11")));
    }

    @Test
    @DisplayName("Read languages paginated with invalid size and check that the response is 400")
    void TestGetLanguagesPaginatedWithInvalidSize() throws Exception {
        mockMvc.perform(get("/api/v1/languages?size=0&page=1"))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

}