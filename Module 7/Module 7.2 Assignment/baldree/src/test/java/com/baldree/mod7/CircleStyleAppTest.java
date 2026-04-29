package com.baldree.mod7;

import java.net.URL;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Circle;

/**
 * Test class for CircleStyleApp.
 * These tests verify that the JavaFX program creates four circles
 * and applies the correct CSS class and IDs.
 *
 * Author: Alex Baldree
 * Assignment: Module 7 Programming Assignment
 */
public class CircleStyleAppTest {

    /**
     * Tests that the circle pane contains exactly four circles.
     */
    @Test
    public void testCreateCirclePaneHasFourCircles() {
        HBox pane = CircleStyleApp.createCirclePane();

        assertEquals(4, pane.getChildren().size(),
                "The pane should contain exactly four circles.");
    }

    /**
     * Tests that each item inside the pane is a Circle object.
     */
    @Test
    public void testAllNodesAreCircles() {
        HBox pane = CircleStyleApp.createCirclePane();

        for (Node node : pane.getChildren()) {
            assertTrue(node instanceof Circle,
                    "Each node in the pane should be a Circle.");
        }
    }

    /**
     * Tests that each circle has the correct shared style class.
     */
    @Test
    public void testCirclesUseStyleClass() {
        HBox pane = CircleStyleApp.createCirclePane();

        for (Node node : pane.getChildren()) {
            assertTrue(node.getStyleClass().contains(CircleStyleApp.CIRCLE_STYLE_CLASS),
                    "Each circle should use the plainCircle style class.");
        }
    }

    /**
     * Tests that the third and fourth circles use the correct CSS IDs.
     */
    @Test
    public void testRedAndGreenCircleIds() {
        HBox pane = CircleStyleApp.createCirclePane();

        Node redCircle = pane.getChildren().get(2);
        Node greenCircle = pane.getChildren().get(3);

        assertEquals(CircleStyleApp.RED_CIRCLE_ID, redCircle.getId(),
                "The third circle should use the redCircle ID.");

        assertEquals(CircleStyleApp.GREEN_CIRCLE_ID, greenCircle.getId(),
                "The fourth circle should use the greenCircle ID.");
    }

    /**
     * Tests that the external CSS file can be found.
     */
    @Test
    public void testCssFileExists() {
        URL cssFile = CircleStyleApp.class.getResource(CircleStyleApp.CSS_FILE);

        assertNotNull(cssFile,
                "The mystyle.css file should exist in the resources folder.");
    }
}