/*
 * Name: Alex Baldree
 * Course: CSD420
 * Assignment: JavaFX Random Cards
 *
 * This program displays four randomly selected card images from a deck of 52 cards.
 */

/*
 * Name: Alex Baldree
 * Course: CSD420
 * Assignment: Module 2 - JavaFX Random Cards
 *
 * This program displays four randomly selected card images from a deck of 52 cards.
 * The card image files are stored in a subdirectory named "cards" and are named
 * 1.png through 52.png.
 *
 * How the program works:
 * 1. The program extends the JavaFX Application class.
 * 2. When the application starts, it creates four ImageView objects to hold card images.
 * 3. A method named displayRandomCards() randomly selects four different card numbers.
 * 4. Each selected number is used to load the corresponding image from the cards folder.
 * 5. The images are displayed in an HBox across the window.
 * 6. A Refresh button is placed below the cards.
 * 7. A lambda expression is used so that when the button is clicked, four new cards appear.
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

public class Baldree_Mod1_3_CSD420 extends Application {

    private final ImageView[] cardViews = new ImageView[4];
    private final Random random = new Random();

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

        // Lambda expression
        refreshButton.setOnAction(e -> displayRandomCards());

        VBox root = new VBox(15);
        root.setAlignment(Pos.CENTER);
        root.getChildren().addAll(cardBox, refreshButton);

        displayRandomCards();

        Scene scene = new Scene(root, 500, 250);
        primaryStage.setTitle("Random Cards");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void displayRandomCards() {
        Set<Integer> selectedCards = new HashSet<>();

        while (selectedCards.size() < 4) {
            selectedCards.add(random.nextInt(52) + 1);
        }

        int index = 0;
        for (int cardNumber : selectedCards) {
            try {
                Image image = new Image(new FileInputStream("cards/" + cardNumber + ".png"));
                cardViews[index].setImage(image);
                index++;
            } catch (FileNotFoundException e) {
                System.out.println("Image not found: cards/" + cardNumber + ".png");
            }
        }
    }
}