package com.baldree.mod8;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AlexThreeThreads extends Application {

    public static final int CHARACTERS_PER_THREAD = 10_000;

    private static final char[] SPECIAL_CHARACTERS = {
            '!', '@', '#', '$', '%', '&', '*', '^'
    };

    @Override
    public void start(Stage primaryStage) {
        TextArea textArea = new TextArea();
        textArea.setWrapText(true);
        textArea.setEditable(false);

        Button startButton = new Button("Start Threads");

        startButton.setOnAction(event -> {
            textArea.clear();
            startButton.setDisable(true);

            startThreeThreads(textArea, () -> startButton.setDisable(false));
        });

        VBox topBox = new VBox(startButton);

        BorderPane root = new BorderPane();
        root.setTop(topBox);
        root.setCenter(textArea);

        Scene scene = new Scene(root, 700, 500);

        primaryStage.setTitle("Alex ThreeThreads");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void startThreeThreads(TextArea textArea, Runnable finishAction) {
        AtomicInteger completedThreads = new AtomicInteger(0);

        Thread letterThread = new Thread(
                () -> generateCharacters(textArea, AlexThreeThreads::randomLetter, completedThreads, finishAction),
                "Letter Thread");

        Thread numberThread = new Thread(
                () -> generateCharacters(textArea, AlexThreeThreads::randomDigit, completedThreads, finishAction),
                "Number Thread");

        Thread specialThread = new Thread(
                () -> generateCharacters(textArea, AlexThreeThreads::randomSpecialCharacter, completedThreads,
                        finishAction),
                "Special Character Thread");

        letterThread.start();
        numberThread.start();
        specialThread.start();
    }

    private static void generateCharacters(
            TextArea textArea,
            Function<Random, Character> characterGenerator,
            AtomicInteger completedThreads,
            Runnable finishAction) {
        Random random = new Random();

        for (int i = 0; i < CHARACTERS_PER_THREAD; i++) {
            char character = characterGenerator.apply(random);
            String text = Character.toString(character);

            Platform.runLater(() -> textArea.appendText(text));
        }

        if (completedThreads.incrementAndGet() == 3) {
            Platform.runLater(finishAction);
        }
    }

    public static char randomLetter(Random random) {
        return (char) ('a' + random.nextInt(26));
    }

    public static char randomDigit(Random random) {
        return (char) ('0' + random.nextInt(10));
    }

    public static char randomSpecialCharacter(Random random) {
        return SPECIAL_CHARACTERS[random.nextInt(SPECIAL_CHARACTERS.length)];
    }

    public static boolean isValidLetter(char character) {
        return character >= 'a' && character <= 'z';
    }

    public static boolean isValidDigit(char character) {
        return character >= '0' && character <= '9';
    }

    public static boolean isValidSpecialCharacter(char character) {
        for (char special : SPECIAL_CHARACTERS) {
            if (character == special) {
                return true;
            }
        }

        return false;
    }

    public static void main(String[] args) {
        launch(args);
    }
}