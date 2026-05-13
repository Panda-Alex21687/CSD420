package com.baldree.mod10;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import javafx.application.Platform;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class FanControllerTest {

    @BeforeAll
    static void startJavaFx() throws Exception {
        CountDownLatch latch = new CountDownLatch(1);

        try {
            Platform.startup(latch::countDown);
        } catch (IllegalStateException alreadyStarted) {
            latch.countDown();
        }

        assertTrue(latch.await(5, TimeUnit.SECONDS));
    }

    @Test
    void displayRecordLoadsFanIntoInterfaceFields() throws Exception {
        runOnFxThreadAndWait(() -> {
            FakeFanRepository fakeRepository = new FakeFanRepository();
            fakeRepository.save(new Fan(1, "Alex", "Baldree", "Titans"));

            FanController controller = new FanController(fakeRepository);

            controller.getIdField().setText("1");
            controller.displayRecord();

            assertEquals("Alex", controller.getFirstNameField().getText());
            assertEquals("Baldree", controller.getLastNameField().getText());
            assertEquals("Titans", controller.getFavoriteTeamField().getText());
            assertEquals("Record displayed.", controller.getMessageLabel().getText());
        });
    }

    @Test
    void updateRecordSendsChangedDataToRepository() throws Exception {
        runOnFxThreadAndWait(() -> {
            FakeFanRepository fakeRepository = new FakeFanRepository();
            fakeRepository.save(new Fan(1, "Alex", "Baldree", "Titans"));

            FanController controller = new FanController(fakeRepository);

            controller.getIdField().setText("1");
            controller.getFirstNameField().setText("Alex");
            controller.getLastNameField().setText("Baldree");
            controller.getFavoriteTeamField().setText("Grizzlies");

            controller.updateRecord();

            Fan updatedFan = fakeRepository.findById(1).orElseThrow();

            assertEquals("Grizzlies", updatedFan.getFavoriteTeam());
            assertEquals("Record updated.", controller.getMessageLabel().getText());
        });
    }

    @Test
    void invalidIdShowsErrorMessage() throws Exception {
        runOnFxThreadAndWait(() -> {
            FakeFanRepository fakeRepository = new FakeFanRepository();
            FanController controller = new FanController(fakeRepository);

            controller.getIdField().setText("abc");
            controller.displayRecord();

            assertEquals("Please enter a valid numeric ID.", controller.getMessageLabel().getText());
        });
    }

    private static void runOnFxThreadAndWait(ThrowingRunnable action) throws Exception {
        CountDownLatch latch = new CountDownLatch(1);
        AtomicReference<Throwable> error = new AtomicReference<>();

        Platform.runLater(() -> {
            try {
                action.run();
            } catch (Throwable throwable) {
                error.set(throwable);
            } finally {
                latch.countDown();
            }
        });

        assertTrue(latch.await(5, TimeUnit.SECONDS));

        if (error.get() != null) {
            throw new AssertionError(error.get());
        }
    }

    private interface ThrowingRunnable {
        void run() throws Exception;
    }

    private static class FakeFanRepository implements FanRepository {
        private final Map<Integer, Fan> fans = new HashMap<>();

        void save(Fan fan) {
            fans.put(fan.getId(), fan);
        }

        @Override
        public Optional<Fan> findById(int id) throws SQLException {
            return Optional.ofNullable(fans.get(id));
        }

        @Override
        public boolean update(Fan fan) throws SQLException {
            if (!fans.containsKey(fan.getId())) {
                return false;
            }

            fans.put(fan.getId(), fan);
            return true;
        }
    }
}