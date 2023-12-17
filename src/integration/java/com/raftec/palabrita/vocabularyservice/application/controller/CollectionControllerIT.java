package com.raftec.palabrita.vocabularyservice.application.controller;

import com.raftec.palabrita.vocabularyservice.application.dto.CollectionEntryRequest;
import com.raftec.palabrita.vocabularyservice.application.dto.CollectionRequest;
import com.raftec.palabrita.vocabularyservice.infrastructure.repositories.CollectionEntryRepository;
import com.raftec.palabrita.vocabularyservice.infrastructure.repositories.CollectionRepository;
import com.raftec.palabrita.vocabularyservice.infrastructure.repositories.LanguageRepository;
import com.raftec.palabrita.vocabularyservice.infrastructure.repositories.TranslationRepository;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
@ActiveProfiles("integration-test")
class CollectionControllerIT {
    public static final String COLLECTIONS_RESOURCE_PATH = "/api/v1/collections";
    private static final String ACCESS_TOKEN = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJodHRwOi8vc2NoZW1hcy54bWxzb2FwLm9yZy93cy8yMDA1LzA1L2lkZW50aXR5L2NsYWltcy9uYW1laWRlbnRpZmllciI6InRlc3R8YjhiYTQ2ZGIyOGFiZmE1NmJmYWVkODg3IiwiZXhwIjoxNzMzNTA1NDMxLCJpc3MiOiJodHRwczovL3Rlc3QtcGFsYWJyaXRhLm5ldC8iLCJhdWQiOiJodHRwczovL3BhbGFicml0YS5uZXQvYXBpIn0.ahUCdKAtnERW9LJKnQbWEMBmFdMjrF8XUfHb18el5QE";
    LanguageRepository languageRepository;
    CollectionEntryRepository collectionEntryRepository;
    CollectionRepository collectionRepository;
    TranslationRepository translationRepository;

    @Container
    static PostgreSQLContainer<?> postgreSQL = new PostgreSQLContainer<>("postgres:latest");

    @LocalServerPort
    private Integer port;

    public CollectionControllerIT(@Autowired LanguageRepository languageRepository,
                                  @Autowired CollectionEntryRepository collectionEntryRepository,
                                  @Autowired CollectionRepository collectionRepository,
                                  @Autowired TranslationRepository translationRepository) {
        this.languageRepository = languageRepository;
        this.collectionEntryRepository = collectionEntryRepository;
        this.collectionRepository = collectionRepository;
        this.translationRepository = translationRepository;
    }

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgreSQL::getJdbcUrl);
        registry.add("spring.datasource.username", postgreSQL::getUsername);
        registry.add("spring.datasource.password", postgreSQL::getPassword);
    }

    @BeforeAll
    static void beforeAll() {
        postgreSQL.start();
    }

    @AfterAll
    static void afterAll() {
        postgreSQL.stop();
    }

    @BeforeEach
    void setUp() {
        RestAssured.baseURI = "http://localhost:" + port;
    }

    /**
     * This method is used to verify that the entity counts are correct.
     *
     * @param collectionCount    The expected collection count
     * @param collectionEntryCount The expected collection entry count
     * @param translationCount    The expected translation count
     */
    private void verifyEntityCounts(long collectionCount, long collectionEntryCount, long translationCount) {
        assertThat(languageRepository.count(), is(104L));
        assertThat(collectionRepository.count(), is(collectionCount));
        assertThat(collectionEntryRepository.count(), is(collectionEntryCount));
        assertThat(translationRepository.count(), is(   translationCount));
    }

    @Test
    @DisplayName("Basic collection round trip")
    void createCollections() {
        // Check preconditions
        verifyEntityCounts(0L, 0L, 0L);

        // ------------------------------------------------------------
        // Use the collection API to create and query collections
        // ------------------------------------------------------------

        // Check that there are no collections
        given()
                .header(new Header("Authorization", ACCESS_TOKEN))
                .contentType(ContentType.JSON)
                .when()
                .get(COLLECTIONS_RESOURCE_PATH)
                .then().log().all()
                .statusCode(200)
                .body(".", is(empty()));

        // Create two collections and check that they are created
        given()
                .header(new Header("Authorization", ACCESS_TOKEN))
                .contentType(ContentType.JSON)
                .body(new CollectionRequest(
                        "English - German dictionary", "8f29c1df", "en", "de"))
                .when()
                .post(COLLECTIONS_RESOURCE_PATH)
                .then().log().all()
                .statusCode(201)
                .header("Location", containsString(COLLECTIONS_RESOURCE_PATH + "/8f29c1df"))
                .body("collectionId", is("8f29c1df"))
                .body("title", is("English - German dictionary"))
                .body("sourceLanguage.code", is("en"))
                .body("targetLanguage.code", is("de"));

        given()
                .header(new Header("Authorization", ACCESS_TOKEN))
                .contentType(ContentType.JSON)
                .body(new CollectionRequest(
                        "Spanish - German dictionary", "f375cb28", "es", "de"))
                .when()
                .post(COLLECTIONS_RESOURCE_PATH)
                .then().log().all()
                .statusCode(201)
                .header("Location", containsString(COLLECTIONS_RESOURCE_PATH +"/f375cb28"))
                .body("collectionId", is("f375cb28"));

        // Now check that there are two collections
        given()
                .header(new Header("Authorization", ACCESS_TOKEN))
                .contentType(ContentType.JSON)
                .when()
                .get(COLLECTIONS_RESOURCE_PATH)
                .then().log().all()
                .statusCode(200)
                .body(".", is(hasSize(2)))
                .body("collectionId", containsInAnyOrder("8f29c1df", "f375cb28"));

        given()
                .header(new Header("Authorization", ACCESS_TOKEN))
                .contentType(ContentType.JSON)
                .when()
                .get(COLLECTIONS_RESOURCE_PATH + "/8f29c1df")
                .then().log().all()
                .statusCode(200)
                .body("collectionId", is("8f29c1df"))
                .body("title", is("English - German dictionary"))
                .body("sourceLanguage.code", is("en"))
                .body("targetLanguage.code", is("de"))
                .body("_links.self.href", containsString(COLLECTIONS_RESOURCE_PATH + "/8f29c1df"))
                .body("_links.delete.href", containsString(COLLECTIONS_RESOURCE_PATH + "/8f29c1df"));

        given()
                .header(new Header("Authorization", ACCESS_TOKEN))
                .contentType(ContentType.JSON)
                .when()
                .get(COLLECTIONS_RESOURCE_PATH + "/f375cb28")
                .then().log().all()
                .statusCode(200)
                .body("collectionId", is("f375cb28"))
                .body("title", is("Spanish - German dictionary"))
                .body("sourceLanguage.code", is("es"))
                .body("targetLanguage.code", is("de"))
                .body("_links.self.href", containsString(COLLECTIONS_RESOURCE_PATH + "/f375cb28"))
                .body("_links.delete.href", containsString(COLLECTIONS_RESOURCE_PATH + "/f375cb28"));

        verifyEntityCounts(2L, 0L, 0L);

        // ------------------------------------------------------------
        // Use the collection API to create collection entries
        // ------------------------------------------------------------

        given()
                .header(new Header("Authorization", ACCESS_TOKEN))
                .contentType(ContentType.JSON)
                .body(new CollectionEntryRequest(
                        "poacher", new String[] { "der Wilddieb", "der Wilderer" }))
                .when()
                .post(COLLECTIONS_RESOURCE_PATH + "/8f29c1df/entries")
                .then().log().all()
                .statusCode(201)
                .body("keyword", is("poacher"))
                .body("translations", hasSize(2))
                .body("translations", containsInAnyOrder("der Wilddieb", "der Wilderer"))
                .body("_links.self.href", containsString(COLLECTIONS_RESOURCE_PATH + "/8f29c1df/entries/poacher"))
                .body("_links.delete.href", containsString(COLLECTIONS_RESOURCE_PATH + "/8f29c1df/entries/poacher"))
                .body("_links.collection.href", containsString(COLLECTIONS_RESOURCE_PATH + "/8f29c1df"));

        given()
                .header(new Header("Authorization", ACCESS_TOKEN))
                .contentType(ContentType.JSON)
                .body(new CollectionEntryRequest(
                        "stirred", new String[] { "gerührt" }))
                .when()
                .post(COLLECTIONS_RESOURCE_PATH + "/8f29c1df/entries")
                .then().log().all()
                .statusCode(201)
                .body("keyword", is("stirred"))
                .body("translations", hasSize(1))
                .body("translations", containsInAnyOrder("gerührt"))
                .body("_links.self.href", containsString(COLLECTIONS_RESOURCE_PATH + "/8f29c1df/entries/stirred"))
                .body("_links.delete.href", containsString(COLLECTIONS_RESOURCE_PATH + "/8f29c1df/entries/stirred"))
                .body("_links.collection.href", containsString(COLLECTIONS_RESOURCE_PATH + "/8f29c1df"));

        given()
                .header(new Header("Authorization", ACCESS_TOKEN))
                .contentType(ContentType.JSON)
                .body(new CollectionEntryRequest(
                        "to threaten", new String[] { "gefährden", "bedrohen" }))
                .when()
                .post(COLLECTIONS_RESOURCE_PATH + "/8f29c1df/entries")
                .then().log().all()
                .statusCode(201)
                .body("keyword", is("to threaten"))
                .body("translations", hasSize(2))
                .body("translations", containsInAnyOrder("gefährden", "bedrohen"))
                .body("_links.self.href", containsString(COLLECTIONS_RESOURCE_PATH + "/8f29c1df/entries/to%20threaten"))
                .body("_links.delete.href", containsString(COLLECTIONS_RESOURCE_PATH + "/8f29c1df/entries/to%20threaten"))
                .body("_links.collection.href", containsString(COLLECTIONS_RESOURCE_PATH + "/8f29c1df"));

        verifyEntityCounts(2L, 3L, 5L);

        // ------------------------------------------------------------
        // Use the collection API to delete collection entries
        // ------------------------------------------------------------

        given()
                .header(new Header("Authorization", ACCESS_TOKEN))
                .contentType(ContentType.JSON)
                .when()
                .delete(COLLECTIONS_RESOURCE_PATH + "/8f29c1df/entries/stirred")
                .then().log().all()
                .statusCode(204);

        verifyEntityCounts(2L, 2L, 4L);

        // ------------------------------------------------------------
        // Use the collection API to delete collections
        // ------------------------------------------------------------

        given()
                .header(new Header("Authorization", ACCESS_TOKEN))
                .contentType(ContentType.JSON)
                .when()
                .delete(COLLECTIONS_RESOURCE_PATH + "/8f29c1df")
                .then().log().all()
                .statusCode(204);

        verifyEntityCounts(1L, 0L, 0L);
    }
}
