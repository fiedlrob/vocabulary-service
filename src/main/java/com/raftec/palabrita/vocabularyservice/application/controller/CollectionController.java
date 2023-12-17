package com.raftec.palabrita.vocabularyservice.application.controller;

import com.raftec.palabrita.vocabularyservice.application.dto.CollectionRequest;
import com.raftec.palabrita.vocabularyservice.application.dto.CollectionResponse;
import com.raftec.palabrita.vocabularyservice.application.mapper.CollectionMapper;
import com.raftec.palabrita.vocabularyservice.domain.services.ICollectionService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("api/v1/collections")
@AllArgsConstructor
public class CollectionController {
    private final ICollectionService collectionService;

    /**
     * This method returns a list of collections for the authenticated user.
     *
     * @param principal The principal object that contains the user information.
     * @return A list of collections for the authenticated user.
     */
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CollectionResponse>> getCollections(Principal principal) {
        var collections = collectionService.getCollections(principal.getName());

        var collectionResponses = collections.stream().map(CollectionMapper.INSTANCE::collectionToResponse).toList();
        collectionResponses.forEach(collectionResponse -> addLinksToResponse(principal,
                collectionResponse.getCollectionId(), collectionResponse));

        return ResponseEntity.ok(collectionResponses);
    }

    /**
     * This method returns a collection for the authenticated user.
     *
     * @param principal The principal object that contains the user information.
     * @param collectionId The collection identifier.
     * @return A collection for the authenticated user.
     */
    @GetMapping(path = "/{collectionId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CollectionResponse> getCollection(Principal principal, @PathVariable String collectionId) {
        // Try to read the collection from the database. If the collection is not found, the service will throw a
        // CollectionNotFoundException exception. This exception will be handled by the VocabularyServiceExceptionHandlerHandler and
        // will return a 404 HTTP status code.
        var collection = collectionService.getCollection(principal.getName(), collectionId);

        // Map the collection to a CollectionResponse object and add the links to the response.
        var collectionResponse = CollectionMapper.INSTANCE.collectionToResponse(collection);
        addLinksToResponse(principal, collectionId, collectionResponse);

        return ResponseEntity.ok(collectionResponse);
    }

    /**
     * This method creates a new collection for the authenticated user.
     *
     * @param principal The principal object that contains the user information.
     * @param collectionRequest The collection request object.
     * @return The created collection.
     */
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CollectionResponse> createCollection(
            Principal principal,
            @RequestBody @Valid CollectionRequest collectionRequest) {
        var createdCollection = collectionService.createCollection(principal.getName(), collectionRequest);

        var collectionResponse = CollectionMapper.INSTANCE.collectionToResponse(createdCollection);
        addLinksToResponse(principal, collectionRequest.collectionId(), collectionResponse);

        return ResponseEntity.created(collectionResponse.getLink("self").get().toUri())
                .body(collectionResponse);
    }

    /**
     * This method deletes a collection for the authenticated user.
     *
     * @param principal The principal object that contains the user information.
     * @param collectionId The collection identifier.
     * @return A 204 HTTP status code.
     */
    @DeleteMapping(path = "/{collectionId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> deleteCollection(Principal principal, @PathVariable String collectionId) {
        collectionService.deleteCollection(principal.getName(), collectionId);

        return ResponseEntity.noContent().build();
    }

    /**
     * This method adds the links to the response.
     *
     * @param principal The principal object that contains the user information.
     * @param collectionId The collection identifier.
     * @param collectionResponse The collection response object.
     */
    private static void addLinksToResponse(Principal principal, String collectionId, CollectionResponse collectionResponse) {
        collectionResponse.add(linkTo(methodOn(CollectionController.class).getCollection(
                principal, collectionId)).withSelfRel().withType("GET"));
        collectionResponse.add(linkTo(methodOn(CollectionController.class).deleteCollection(
                principal, collectionId)).withRel("delete").withType("DELETE"));
    }
}
