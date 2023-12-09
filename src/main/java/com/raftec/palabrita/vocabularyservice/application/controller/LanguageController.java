package com.raftec.palabrita.vocabularyservice.application.controller;

import com.raftec.palabrita.vocabularyservice.application.exceptions.InvalidPageException;
import com.raftec.palabrita.vocabularyservice.application.dto.PagedResponse;
import com.raftec.palabrita.vocabularyservice.domain.model.Language;
import com.raftec.palabrita.vocabularyservice.domain.services.ILanguageService;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/languages")
@AllArgsConstructor
public class LanguageController {
    private final ILanguageService languageService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Language>> getLanguages() {
        return ResponseEntity.ok(languageService.getLanguages());
    }

    @GetMapping(params = { "page", "size" }, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PagedResponse<Language>> getLanguagesPaginated(
            @RequestParam("page") @Min(value = 1, message = "{validation.page.greater.zero}") int page,
            @RequestParam("size") @Min(value = 1, message = "{validation.size.greater.zero}") int size) {
        var totalCount = languageService.getLanguageCount();
        var totalPages = (int) (totalCount / size + 1);

        // If the requested page is greater than the total number of pages, we throw an exception
        // This exception will be handled by the CustomResponseEntityExceptionHandlerHandler class
        if (page > totalPages) {
            throw new InvalidPageException(totalPages, page);
        }

        // Here we need to subtract 1 from the page because the frontend starts counting from 1
        var languages = languageService.getLanguages(page - 1, size);

        return ResponseEntity.ok(new PagedResponse<>(languages.size(), page, (int) totalCount, totalPages,
                page > 1, page < totalPages, languages));
    }

    @GetMapping(path = "/{code}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Language> getLanguage(@PathVariable String code) {
        var language = languageService.getLanguage(code);

        return language.map(ResponseEntity::ok).orElseGet(
                () -> ResponseEntity.notFound().build());

    }
}
