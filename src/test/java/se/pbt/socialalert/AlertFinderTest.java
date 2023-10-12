package se.pbt.socialalert;

import io.micronaut.runtime.EmbeddedApplication;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import jakarta.inject.Inject;

@MicronautTest
@DisplayName("Application runs!")
class AlertFinderTest {

    @Inject
    EmbeddedApplication<?> application;

    @Test
    @DisplayName("Application is running.")
    void testItWorks() {
        Assertions.assertTrue(application.isRunning());
    }

}
