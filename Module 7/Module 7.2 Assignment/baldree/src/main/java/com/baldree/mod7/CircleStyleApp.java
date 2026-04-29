package com.baldree.mod7;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

/**
 * The CircleStyleApp class displays four circles using an external CSS file.
 * Two circles use a style class for white fill and black stroke.
 * The other circles use CSS IDs to display red and green circles.
 *
 * Alexander Baldree
 * Module 7 Programming Assignment
 */
public class CircleStyleApp extends Application {

    public static final String CSS_FILE = "mystyle.css";
    public static final String CIRCLE_STYLE_CLASS = "plainCircle";
    public static final String RED_CIRCLE_ID = "redCircle";
    public static final String GREEN_CIRCLE_ID = "greenCircle";
    public static final double CIRCLE_RADIUS = 50.0;

    /**
     * Starts the JavaFX application and displays the scene.
     *
     * @param primaryStage the main window for the application
     */
    @Override
    public void start(Stage primaryStage) {
        HBox circlePane = createCirclePane();

        Scene scene = new Scene(circlePane, 500, 160);

        String cssPath = getClass().getResource(CSS_FILE).toExternalForm();
        scene.getStylesheets().add(cssPath);

        primaryStage.setTitle("Module 7 CSS Circles");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Creates the pane that holds the four circles.
     *
     * @return an HBox containing four styled circles
     */
    public static HBox createCirclePane() {
        HBox pane = new HBox(20);
        pane.setPadding(new Insets(25));

        Circle circleOne = createStyledCircle(null);
        Circle circleTwo = createStyledCircle(null);
        Circle circleThree = createStyledCircle(RED_CIRCLE_ID);
        Circle circleFour = createStyledCircle(GREEN_CIRCLE_ID);

        pane.getChildren().addAll(circleOne, circleTwo, circleThree, circleFour);

        return pane;
    }

    /**
     * Creates one circle and applies the shared CSS style class.
     * If an ID is provided, the ID is also applied to the circle.
     *
     * @param id the CSS ID to apply, or null if no ID is needed
     * @return a styled Circle object
     */
    public static Circle createStyledCircle(String id) {
        Circle circle = new Circle(50, 50, CIRCLE_RADIUS);
        circle.getStyleClass().add(CIRCLE_STYLE_CLASS);

        if (id != null && !id.isBlank()) {
            circle.setId(id);
        }

        return circle;
    }

    /**
     * Launches the JavaFX application.
     *
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}