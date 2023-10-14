package se.pbt.socialalert.integrationtest.repository;

import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import reactor.test.StepVerifier;
import se.pbt.socialalert.exception.AlertNotFoundException;
import se.pbt.socialalert.model.entity.Alert;
import se.pbt.socialalert.repository.AlertRepository;
import se.pbt.socialalert.testobject.TestObjectCreator;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@MicronautTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("AlertRepository Integration Test:")
public class AlertRepositoryTest {

    // Fields
    @Inject
    private AlertRepository alertRepository;
    private final String trigger = "AlertRepository Integration Test";


    // Tests
    @Nested
    @DisplayName("Lifecycle of alerts creation, deletion and retrieval with ok input")
    class CrudOperationsTest {

        @Test
        @Order(1)
        @DisplayName("Database is empty")
        void databaseIsEmpty() {
            resetDatabase();
            assertThat(objectsInDatabase()).isEqualTo(0);
        }

        @Test
        @Order(2)
        @DisplayName("Saves an alert to the database and correctly stores all fields")
        public void savesAlertToDatabase_allFieldsCorrectValues() {
            Alert testAlert = TestObjectCreator.socialAlert(trigger);
            Alert savedAlert = alertRepository.save(testAlert).block();

            int inDatabase = objectsInDatabase();
            assertThat(inDatabase).isEqualTo(1);

            assert savedAlert != null;
            Alert retrievedAlert = alertRepository.findById(savedAlert.getId()).block();

            assert retrievedAlert != null;
            assertFieldsMatch(savedAlert, retrievedAlert);
        }

        @Test
        @Order(3)
        @DisplayName("Deletes the alert from the database and throws exception when database is empty")
        public void deletesAlertFromDatabase_whenMatchingIdIsFound() {
            Alert alertToDelete = findFirst();
            alertRepository.deleteById(alertToDelete.getId()).block();

            int inDatabase = objectsInDatabase();
            assertThat(inDatabase).isZero();

            assertThatThrownBy(() -> alertRepository.findById(alertToDelete.getId()).block())
                    .isInstanceOf(AlertNotFoundException.class);
        }
    }



    @Nested
    @DisplayName("Parameterized Tests:")
    class ParameterizedCrudTest {

        static Stream<Alert> alertProvider() {
            return Stream.of(
                    TestObjectCreator.socialAlert("frihet"),
                    TestObjectCreator.socialAlert("självständighet")
            );
        }


        @ParameterizedTest
        @MethodSource("alertProvider")
        @DisplayName("Saves and retrieves alert by ID with matching fields")
        public void savesAndRetrievesAlertById(Alert alert) {
            Alert savedAlert = alertRepository.save(alert).block();

            StepVerifier.create(alertRepository.findById(savedAlert.getId()))
                    .expectNextMatches(retrievedAlert -> assertFieldsMatch(retrievedAlert, savedAlert))
                    .verifyComplete();
        }

        @ParameterizedTest
        @MethodSource("alertProvider")
        @DisplayName("Saves and deletes alert by ID and throws exception when alert is not found")
        public void savesAndDeletesAlertById(Alert alert) {
            Alert savedAlert = alertRepository.save(alert).block();

            assert savedAlert != null;
            StepVerifier.create(alertRepository.deleteById(savedAlert.getId()))
                    .verifyComplete();

            StepVerifier.create(alertRepository.findById(savedAlert.getId()))
                    .verifyErrorMatches(throwable -> throwable instanceof AlertNotFoundException);
        }

        @ParameterizedTest
        @MethodSource("alertProvider")
        @DisplayName("Saves and retrieves all alerts with matching fields")
        public void savesAlertAndRetrievesAllAlerts(Alert alert) {
            Alert savedAlert = alertRepository.save(alert).block();

            assert savedAlert != null;
            StepVerifier.create(alertRepository.findAll())
                    .expectNextMatches(retrievedAlert -> assertFieldsMatch(retrievedAlert, savedAlert))
                    .verifyComplete();
            resetDatabase();
        }

        @ParameterizedTest
        @MethodSource("alertProvider")
        @DisplayName("Deletes all alerts from the repository and throws exception when retrieving non existing alert")
        public void testDeleteAll(Alert alert) {
            Alert savedAlert = alertRepository.save(alert).block();

            StepVerifier.create(alertRepository.deleteAll())
                    .verifyComplete();

            assert savedAlert != null;
            StepVerifier.create(alertRepository.findById(savedAlert.getId()))
                    .verifyErrorMatches(throwable -> throwable instanceof AlertNotFoundException);
        }
    }


    // Private helper methods

    private void resetDatabase() {
        alertRepository.deleteAll().block();
    }

    private int objectsInDatabase() {
        return alertRepository.findAll().toStream().toList().size();
    }

    private Alert findFirst() {
        return alertRepository.findAll().blockFirst();
    }

    private boolean assertFieldsMatch(Alert a1, Alert a2) {
        try {
            assertThat(a1.getId()).isEqualTo(a2.getId());
            assertThat(a1.getTrigger()).isEqualTo(a2.getTrigger());
            assertThat(a1.getTriggerContext()).isEqualTo(a2.getTriggerContext());
            assertThat(a1.getUserAccount()).isEqualTo(a2.getUserAccount());
            assertThat(a1.getIpAddress()).isEqualTo(a2.getIpAddress());
            assertThat(a1.getSourceReference()).isEqualTo(a2.getSourceReference());
            assertThat(a1.getGeographicLocation()).isEqualTo(a2.getGeographicLocation());
            return true;
        } catch (AssertionError error) {
            return false;
        }
    }

}
