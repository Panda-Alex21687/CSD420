package com.baldree.mod3;

import java.util.ArrayList;
import java.util.Random;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Alexander Baldree
 * CSD420
 * Module 3.2 Assignment
 * 04/07/2026
 *
 * Description:
 * This JavaFX program demonstrates the use of a generic static method that
 * removes duplicate values from an ArrayList. The program creates an original
 * ArrayList of 50 random integers between 1 and 20, displays the original list,
 * and then displays a new list containing the same values without duplicates.
 */

public class RemoveDuplicatesJavaFX extends Application {

    private final TextArea originalArea = new TextArea();
    private final TextArea uniqueArea = new TextArea();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Label titleLabel = new Label("Remove Duplicates from an ArrayList");
        titleLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        Label originalLabel = new Label("Original List (50 random values from 1 to 20):");
        Label uniqueLabel = new Label("List After Removing Duplicates:");

        originalArea.setEditable(false);
        originalArea.setWrapText(true);
        originalArea.setPrefHeight(140);

        uniqueArea.setEditable(false);
        uniqueArea.setWrapText(true);
        uniqueArea.setPrefHeight(140);

        Button generateButton = new Button("Generate New Lists");
        generateButton.setOnAction(e -> generateLists());

        VBox root = new VBox(10);
        root.setPadding(new Insets(15));
        root.getChildren().addAll(
                titleLabel,
                generateButton,
                originalLabel,
                originalArea,
                uniqueLabel,
                uniqueArea);

        generateLists();

        Scene scene = new Scene(root, 650, 420);
        primaryStage.setTitle("Baldree - Module 3.2 CSD420");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void generateLists() {
        ArrayList<Integer> originalList = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < 50; i++) {
            originalList.add(random.nextInt(20) + 1);
        }

        ArrayList<Integer> uniqueList = removeDuplicates(originalList);

        originalArea.setText(originalList.toString());
        uniqueArea.setText(uniqueList.toString());
    }

    public static <E> ArrayList<E> removeDuplicates(ArrayList<E> list) {
        ArrayList<E> uniqueList = new ArrayList<>();

        for (E element : list) {
            if (!uniqueList.contains(element)) {
                uniqueList.add(element);
            }
        }

        return uniqueList;
    }
}