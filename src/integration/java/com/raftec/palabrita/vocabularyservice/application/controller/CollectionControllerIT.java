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
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
@ActiveProfiles("integration-test")

class CollectionControllerIT {
    private static final String COLLECTIONS_RESOURCE_PATH = "/api/v1/collections";
    private static final String ACCESS_TOKEN = "Bearer " +
            "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJodHRwOi8vc2NoZW1hcy54bWx" +
            "zb2FwLm9yZy93cy8yMDA1LzA1L2lkZW50aXR5L2NsYWltcy9uYW1laWRlbnRpZml" +
            "lciI6InRlc3R8YjhiYTQ2ZGIyOGFiZmE1NmJmYWVkODg3IiwiZXhwIjoxNzMzNTA" +
            "1NDMxLCJpc3MiOiJodHRwczovL3Rlc3QtcGFsYWJyaXRhLm5ldC8iLCJhdWQiOiJ" +
            "odHRwczovL3BhbGFicml0YS5uZXQvYXBpIn0.ahUCdKAtnERW9LJKnQbWEMBmFdM" +
            "jrF8XUfHb18el5QE";
    private static final List<CollectionEntryRequest> COLLECTION_REQUESTS = List.of(
            new CollectionEntryRequest("fiercest", new String[]{"am heftigsten"}),
            new CollectionEntryRequest("deluge", new String[]{"die Flut", "die Überschwemmung"}),
            new CollectionEntryRequest("riotous", new String[]{"turbulent"}),
            new CollectionEntryRequest("to threaten", new String[]{"gefährden", "bedrohen"}),
            new CollectionEntryRequest("fierces", new String[]{"am heftigsten"}),
            new CollectionEntryRequest("although", new String[]{"obwohl", "obgleich"}),
            new CollectionEntryRequest("even though", new String[]{"auch wenn", "selbst wenn"}),
            new CollectionEntryRequest("summoned", new String[]{"einberufen", "vorgeladen"}),
            new CollectionEntryRequest("gloom", new String[]{"die Finsternis"}),
            new CollectionEntryRequest("gloomy", new String[]{"düster", "trübe"}),
            new CollectionEntryRequest("crimson", new String[]{"purpurrot", "blutrot"}),
            new CollectionEntryRequest("to purr", new String[]{"schurren"}),
            new CollectionEntryRequest("resentment", new String[]{"die Feindseligkeit", "die Missgunst"}),
            new CollectionEntryRequest("to accompany", new String[]{"begleiten"}),
            new CollectionEntryRequest("suspense", new String[]{"die Spannung"}),
            new CollectionEntryRequest("taut", new String[]{"angespannt", "straff"}),
            new CollectionEntryRequest("commotion", new String[]{"der Tumult", "die Aufregung"}),
            new CollectionEntryRequest("to choke", new String[]{"ersticken", "etwas drosseln", "jemanden würgen"}),
            new CollectionEntryRequest("to fathom", new String[]{"ergründen", "begreifen"}),
            new CollectionEntryRequest("to ponder", new String[]{"etwas durchdenken"}),
            new CollectionEntryRequest("downpour", new String[]{"der Platzregen"}),
            new CollectionEntryRequest("as though", new String[]{"als ob", "als wenn"}),
            new CollectionEntryRequest("to slump", new String[]{"fallen", "zusammensacken"}),
            new CollectionEntryRequest("stout", new String[]{"kräftig", "stämmig", "korpulent"}),
            new CollectionEntryRequest("diligence", new String[]{"der Fleiß", "die Sorgfalt"}),
            new CollectionEntryRequest("particular", new String[]{"speziell", "besonders"}),
            new CollectionEntryRequest("to wince", new String[]{"zucken", "zusammenzucken"}),
            new CollectionEntryRequest("wealth", new String[]{"der Reichtum", "der Wohlstand"}),
            new CollectionEntryRequest("to relegate", new String[]{"verbannen"}),
            new CollectionEntryRequest("goatee", new String[]{"der Ziegenbart"}),
            new CollectionEntryRequest("to tug", new String[]{"zerren", "ziehen"}),
            new CollectionEntryRequest("distraught", new String[]{"verzweifelt", "aufgelöst", "verstört"}),
            new CollectionEntryRequest("to engross", new String[]{"beanspruchen", "beschlagnehmen"}),
            new CollectionEntryRequest("to neglect", new String[]{"vernachlässigen", "missachten"}),
            new CollectionEntryRequest("admonishment", new String[]{"die Ermahnung"}),
            new CollectionEntryRequest("only too well", new String[]{"nur zu gut"}),
            new CollectionEntryRequest("to flatter", new String[]{"schmeicheln"}),
            new CollectionEntryRequest("to disguise", new String[]{"verbergen", "tarnen", "verkleiden"}),
            new CollectionEntryRequest("rimmed", new String[]{"umrandet"}),
            new CollectionEntryRequest("to pursue", new String[]{"verfolgen", "nachlaufen", "nachjagen"}),
            new CollectionEntryRequest("sleight of hand", new String[]{"das Kunststück", "der Trick"}),
            new CollectionEntryRequest("tripwire", new String[]{"der Stolperdraht"}),
            new CollectionEntryRequest("elaborate", new String[]{"aufwendig", "ausführlich"}),
            new CollectionEntryRequest("to elaborate", new String[]{"sorgfältig ausarbeiten "}),
            new CollectionEntryRequest("tentative", new String[]{"unverbindlich", "vorläufig"}),
            new CollectionEntryRequest("grief", new String[]{"die Trauer"}),
            new CollectionEntryRequest("despite", new String[]{"trotz"}),
            new CollectionEntryRequest("reek", new String[]{"der Gestank", "der Mief"}),
            new CollectionEntryRequest("glimpse", new String[]{"flüchtiger Blick"}),
            new CollectionEntryRequest("sleet", new String[]{"der Schneeregen", "die Graupel"}),
            new CollectionEntryRequest("drizzle", new String[]{"der Nieselregen", "der Sprühnebel"}),
            new CollectionEntryRequest("surge", new String[]{"der Schwall", "die Woge"}),
            new CollectionEntryRequest("to suppose", new String[]{"vermuten", "annehmen"}),
            new CollectionEntryRequest("to commence", new String[]{"anfangen"}),
            new CollectionEntryRequest("to start", new String[]{"beginnen"}),
            new CollectionEntryRequest("summit", new String[]{"der Gipfel", "der Höhepunkt"}),
            new CollectionEntryRequest("scarf", new String[]{"der Schal"}),
            new CollectionEntryRequest("limbs", new String[]{"die Gliedmaßen"}),
            new CollectionEntryRequest("crowbar", new String[]{"die Brechstange"}),
            new CollectionEntryRequest("throughout", new String[]{"durchweg", "im Verlauf"}),
            new CollectionEntryRequest("pristine", new String[]{"makellos", "ursprünglich"}),
            new CollectionEntryRequest("unearthed", new String[]{"ausgegraben"}),
            new CollectionEntryRequest("blurted", new String[]{"herausgeplatzt"}),
            new CollectionEntryRequest("sooty", new String[]{"rußig"}),
            new CollectionEntryRequest("swelter", new String[]{"schmoren"}),
            new CollectionEntryRequest("sluggish", new String[]{"träge", "schleppend", "schwerfällig"}),
            new CollectionEntryRequest("intriguing", new String[]{"faszinierend", "fesselnd"}),
            new CollectionEntryRequest("to nudge", new String[]{"anstupsen", "schubsen"}),
            new CollectionEntryRequest("ransacked", new String[]{"geplündert"}),
            new CollectionEntryRequest("to stir", new String[]{"rühren", "umrühren"}),
            new CollectionEntryRequest("stirred", new String[]{"gerührt"}),
            new CollectionEntryRequest("to scrunch", new String[]{"knirschen"}),
            new CollectionEntryRequest("debris", new String[]{"der Schutt"}),
            new CollectionEntryRequest("thud", new String[]{"dumpfer Schlag"}),
            new CollectionEntryRequest("spouse", new String[]{"der Ehepartner", "die Ehepartnerin"}),
            new CollectionEntryRequest("poacher", new String[]{"der Wilderer"}),
            new CollectionEntryRequest("toddler", new String[]{"das Kleinkind"})
    );

    private final LanguageRepository languageRepository;
    private final CollectionEntryRepository collectionEntryRepository;
    private final CollectionRepository collectionRepository;
    private final TranslationRepository translationRepository;

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

    @AfterEach
    void tearDown()
    {
        // Delete all entities
        translationRepository.deleteAll();
        collectionEntryRepository.deleteAll();
        collectionRepository.deleteAll();
    }

    /**
     * This method is used to verify that the entity counts are correct.
     *
     * @param collectionCount      The expected collection count
     * @param collectionEntryCount The expected collection entry count
     * @param translationCount     The expected translation count
     */
    private void verifyEntityCounts(long collectionCount, long collectionEntryCount, long translationCount) {
        assertThat(languageRepository.count(), is(104L));
        assertThat(collectionRepository.count(), is(collectionCount));
        assertThat(collectionEntryRepository.count(), is(collectionEntryCount));
        assertThat(translationRepository.count(), is(translationCount));
    }

    @Test
    @DisplayName("Basic collection round trip")
    @DirtiesContext
    void testBasicCollectionRoundTrip() {
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
                .header("Location", containsString(COLLECTIONS_RESOURCE_PATH + "/f375cb28"))
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
                        "poacher", new String[]{"der Wilddieb", "der Wilderer"}))
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
                        "stirred", new String[]{"gerührt"}))
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
                        "to threaten", new String[]{"gefährden", "bedrohen"}))
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

    @Test
    @DisplayName("Read collection entries non paged")
    @DirtiesContext
    void testReadCollectionEntries() {
        verifyEntityCounts(0, 0, 0);

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
                .header("Location", containsString(COLLECTIONS_RESOURCE_PATH + "/f375cb28"))
                .body("collectionId", is("f375cb28"));

        verifyEntityCounts(2, 0, 0);

        COLLECTION_REQUESTS.forEach(collectionEntryRequest -> given()
                .header(new Header("Authorization", ACCESS_TOKEN))
                .contentType(ContentType.JSON)
                .body(collectionEntryRequest)
                .when()
                .post(COLLECTIONS_RESOURCE_PATH + "/8f29c1df/entries")
                .then().log().all()
                .statusCode(201));

        verifyEntityCounts(2, 77, 124);

        given()
                .header(new Header("Authorization", ACCESS_TOKEN))
                .when()
                .get(COLLECTIONS_RESOURCE_PATH + "/8f29c1df/entries")
                .then().log().all()
                .statusCode(200)
                .body(".", hasSize(77));
    }

    @Test
    @DisplayName("Read collection entries paged")
    @DirtiesContext
    void testReadCollectionEntriesPaged() {
        verifyEntityCounts(0, 0, 0);

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
                .header("Location", containsString(COLLECTIONS_RESOURCE_PATH + "/f375cb28"))
                .body("collectionId", is("f375cb28"));

        verifyEntityCounts(2, 0, 0);

        // Add 77 collection entries
        COLLECTION_REQUESTS.forEach(collectionEntryRequest -> given()
                .header(new Header("Authorization", ACCESS_TOKEN))
                .contentType(ContentType.JSON)
                .body(collectionEntryRequest)
                .when()
                .post(COLLECTIONS_RESOURCE_PATH + "/8f29c1df/entries")
                .then().log().all()
                .statusCode(201));

        verifyEntityCounts(2, 77, 124);

        // Read the first page and check the results
        given()
                .header(new Header("Authorization", ACCESS_TOKEN))
                .when()
                .get(COLLECTIONS_RESOURCE_PATH + "/8f29c1df/entries?page=1&size=10")
                .then().log().all()
                .statusCode(200)
                .body("pageCount", is(10))
                .body("pageNumber", is(1))
                .body("totalCount", is(77))
                .body("totalPages", is(8))
                .body("hasPreviousPage", is(false))
                .body("hasNextPage", is(true))
                .body("items", hasSize(10))
                .body("items[0].keyword", is("admonishment"));

        // Read the last page and check the results
        given()
                .header(new Header("Authorization", ACCESS_TOKEN))
                .when()
                .get(COLLECTIONS_RESOURCE_PATH + "/8f29c1df/entries?page=8&size=10")
                .then().log().all()
                .statusCode(200)
                .body("pageCount", is(7))
                .body("pageNumber", is(8))
                .body("totalCount", is(77))
                .body("totalPages", is(8))
                .body("hasPreviousPage", is(true))
                .body("hasNextPage", is(false))
                .body("items", hasSize(7))
                .body("items[6].keyword", is("wealth"));
    }
}
