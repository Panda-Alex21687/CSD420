/*
 * Name: Alex Baldree
 * Course: CSD420
 * Assignment: JavaFX Random Cards
 *
 * This program displays four randomly selected card images from a deck of 52 cards.
 */

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Baldree_Mod_1_3_JavaFXCards extends Application {

    private final ImageView[] cardViews = new ImageView[4];
    private final Random random = new Random();

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

        VBox root = new VBox(15);
        root.setAlignment(Pos.CENTER);
        root.getChildren().addAll(cardBox, refreshButton);

        displayRandomCards();

        Scene scene = new Scene(root, 500, 300);
        primaryStage.setTitle("Random Playing Cards");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void displayRandomCards() {
        Set<Integer> selectedCards = new HashSet<>();

        while (selectedCards.size() < 4) {
            int cardNumber = random.nextInt(52) + 1;
            selectedCards.add(cardNumber);
        }

        int index = 0;
        for (int cardNumber : selectedCards) {
            try {
                Image cardImage = new Image(new FileInputStream("cards/" + cardNumber + ".png"));
                cardViews[index].setImage(cardImage);
                index++;
            } catch (FileNotFoundException ex) {
                System.out.println("Could not find file: cards/" + cardNumber + ".png");
            }
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}