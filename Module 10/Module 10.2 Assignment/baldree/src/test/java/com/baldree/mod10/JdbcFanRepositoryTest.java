package com.baldree.mod10;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class JdbcFanRepositoryTest {

    private JdbcFanRepository repository;

    @BeforeEach
    void setUp() throws Exception {
        String databaseUrl = "jdbc:h2:mem:fansdb" + System.nanoTime() + ";MODE=MySQL;DB_CLOSE_DELAY=-1";

        try (Connection connection = DriverManager.getConnection(databaseUrl, "sa", "");
                Statement statement = connection.createStatement()) {

            statement.execute("""
                    CREATE TABLE fans (
                        ID INT PRIMARY KEY,
                        firstname VARCHAR(25),
                        lastname VARCHAR(25),
                        favoriteteam VARCHAR(25)
                    )
                    """);

            statement.execute("""
                    INSERT INTO fans (ID, firstname, lastname, favoriteteam)
                    VALUES
                    (1, 'Alex', 'Baldree', 'Titans'),
                    (2, 'John', 'Smith', 'Grizzlies')
                    """);
        }

        repository = new JdbcFanRepository(databaseUrl, "sa", "");
    }

    @Test
    void findByIdReturnsFanWhenRecordExists() throws Exception {
        Optional<Fan> fan = repository.findById(1);

        assertTrue(fan.isPresent());
        assertEquals(1, fan.get().getId());
        assertEquals("Alex", fan.get().getFirstName());
        assertEquals("Baldree", fan.get().getLastName());
        assertEquals("Titans", fan.get().getFavoriteTeam());
    }

    @Test
    void findByIdReturnsEmptyWhenRecordDoesNotExist() throws Exception {
        Optional<Fan> fan = repository.findById(99);

        assertFalse(fan.isPresent());
    }

    @Test
    void updateChangesExistingFanRecord() throws Exception {
        Fan updatedFan = new Fan(1, "Alex", "Baldree", "Memphis Tigers");

        boolean updated = repository.update(updatedFan);

        assertTrue(updated);

        Optional<Fan> fan = repository.findById(1);

        assertTrue(fan.isPresent());
        assertEquals("Memphis Tigers", fan.get().getFavoriteTeam());
    }

    @Test
    void updateReturnsFalseWhenFanDoesNotExist() throws Exception {
        Fan missingFan = new Fan(99, "Missing", "Person", "No Team");

        boolean updated = repository.update(missingFan);

        assertFalse(updated);
    }
}