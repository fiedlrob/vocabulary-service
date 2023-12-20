package com.raftec.palabrita.vocabularyservice.application.controller;

import com.raftec.palabrita.vocabularyservice.application.dto.CollectionEntryRequest;
import com.raftec.palabrita.vocabularyservice.application.dto.CollectionEntryResponse;
import com.raftec.palabrita.vocabularyservice.application.dto.PagedResponse;
import com.raftec.palabrita.vocabularyservice.application.exceptions.InvalidPageException;
import com.raftec.palabrita.vocabularyservice.application.mapper.CollectionEntryMapper;
import com.raftec.palabrita.vocabularyservice.domain.services.ICollectionService;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@RestController
@RequestMapping("api/v1/collections/{collectionId}/entries")
@AllArgsConstructor
public class CollectionEntriesController {
    private final ICollectionService collectionService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CollectionEntryResponse>> getCollectionEntries(
            Principal principal,
            @PathVariable String collectionId) {
        var collection = collectionService.getCollection(principal.getName(), collectionId);

        var collectionEntries = collection.getCollectionEntries().stream()
                .map(CollectionEntryMapper.INSTANCE::collectionEntryToResponse).toList();
        collectionEntries.forEach(collectionEntryResponse -> addLinksToResponse(
                principal, collectionId, collectionEntryResponse));

        return ResponseEntity.ok(collectionEntries);
    }

    @GetMapping(params = { "page", "size" }, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PagedResponse<CollectionEntryResponse>> getCollectionEntriesPaginated(
            Principal principal,
            @PathVariable String collectionId,
            @RequestParam("page") @Min(value = 1, message = "{validation.page.greater.zero}") int page,
            @RequestParam("size") @Min(value = 1, message = "{validation.size.greater.zero}") int size) {
        // If the collection does not exist, return a 404 HTTP status code.
        // If the user is not the owner of the collection, return a 404 HTTP status code.
        if (!collectionService.isOwner(principal.getName(), collectionId)) {
            return ResponseEntity.notFound().build();
        }

        var totalCount = collectionService.getCollectionEntriesCount(principal.getName(), collectionId);
        var totalPages = (int) (totalCount / size + 1);

        // If the requested page is greater than the total number of pages, we throw an exception
        // This exception will be handled by the CustomResponseEntityExceptionHandlerHandler class
        if (page > totalPages) {
            throw new InvalidPageException(totalPages, page);
        }

        var collectionEntries = collectionService.getCollectionEntries(principal.getName(), collectionId, page - 1, size)
                .stream().map(CollectionEntryMapper.INSTANCE::collectionEntryToResponse).toList();
        collectionEntries.forEach(collectionEntryResponse -> addLinksToResponse(
                principal, collectionId, collectionEntryResponse));

        return ResponseEntity.ok(new PagedResponse<>(collectionEntries.size(), page, (int) totalCount, totalPages,
                page > 1, page < totalPages, collectionEntries));
    }

    @GetMapping(path = "/{keyword}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CollectionEntryResponse> getEntry(
            Principal principal,
            @PathVariable String collectionId,
            @PathVariable String keyword) {
        var collection = collectionService.getCollection(principal.getName(), collectionId);

        var collectionEntry = collection.getCollectionEntries().stream()
                .filter(entry -> entry.getKeyword().equals(keyword))
                .findFirst()
                .orElseThrow();

        var collectionEntryResponse = CollectionEntryMapper.INSTANCE.collectionEntryToResponse(collectionEntry);
        addLinksToResponse(principal, collectionId, collectionEntryResponse);

        return ResponseEntity.ok(collectionEntryResponse);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CollectionEntryResponse> createEntry(
            Principal principal,
            @PathVariable String collectionId,
            @RequestBody CollectionEntryRequest collectionEntryRequest) {
        var collectionEntry = collectionService.createCollectionEntry(principal.getName(), collectionId,
                collectionEntryRequest);

        var collectionEntryResponse = CollectionEntryMapper.INSTANCE.collectionEntryToResponse(collectionEntry);
        addLinksToResponse(principal, collectionId, collectionEntryResponse);

        return ResponseEntity.created(collectionEntryResponse.getLink("self").get().toUri())
                .body(collectionEntryResponse);
    }

    @DeleteMapping(path = "/{keyword}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> deleteEntry(
            Principal principal,
            @PathVariable String collectionId,
            @PathVariable String keyword) {
        collectionService.deleteCollectionEntry(principal.getName(), collectionId, keyword);

        return ResponseEntity.noContent().build();
    }

    private static void addLinksToResponse(Principal principal, String collectionId, CollectionEntryResponse collectionEntryResponse) {
        collectionEntryResponse.add(linkTo(methodOn(CollectionEntriesController.class).getEntry(
                principal, collectionId, collectionEntryResponse.getKeyword())).withSelfRel().withType("GET"));
        collectionEntryResponse.add(linkTo(methodOn(CollectionEntriesController.class).deleteEntry(
                principal, collectionId, collectionEntryResponse.getKeyword())).withRel("delete").withType("DELETE"));
        collectionEntryResponse.add(linkTo(methodOn(CollectionController.class).getCollection(
                principal, collectionId)).withRel("collection").withType("GET"));
    }
}
