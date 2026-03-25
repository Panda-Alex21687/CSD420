package com.baldree;

/*
 * Name: Alex Baldree
 * Course: CSD420
 * Assignment: Random Card Display
 *
 * This JavaFX program displays four randomly selected card images
 * from a deck of 52 cards. The card image files are stored in the
 * resources/cards folder and are named 1.png through 52.png.
 */

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class App extends Application {

    private final ImageView[] cardViews = new ImageView[4];

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        HBox cardBox = new HBox(10);
        cardBox.setAlignment(Pos.CENTER);

        for (int i = 0; i < 4; i++) {
            cardViews[i] = new ImageView();
            cardViews[i].setFitWidth(100);
            cardViews[i].setPreserveRatio(true);
            cardBox.getChildren().add(cardViews[i]);
        }

        Button refreshButton = new Button("Refresh");
        refreshButton.setOnAction(e -> displayRandomCards());

        displayRandomCards();

        VBox root = new VBox(20);
        root.setAlignment(Pos.CENTER);
        root.getChildren().addAll(cardBox, refreshButton);

        Scene scene = new Scene(root, 520, 300);

        primaryStage.setTitle("Random Cards");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void displayRandomCards() {
        ArrayList<Integer> deck = new ArrayList<>();

        for (int i = 1; i <= 52; i++) {
            deck.add(i);
        }

        Collections.shuffle(deck);

        for (int i = 0; i < 4; i++) {
            int cardNumber = deck.get(i);
            String imagePath = "/cards/" + cardNumber + ".png";

            InputStream stream = getClass().getResourceAsStream(imagePath);

            if (stream != null) {
                Image cardImage = new Image(stream);
                cardViews[i].setImage(cardImage);
            } else {
                System.out.println("Could not load image: " + imagePath);
            }
        }
    }
}