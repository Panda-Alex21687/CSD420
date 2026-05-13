package com.baldree.mod10;

import java.sql.SQLException;
import java.util.Optional;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

public class FanController {

    private final FanRepository fanRepository;

    private final BorderPane root = new BorderPane();

    private final TextField idField = new TextField();
    private final TextField firstNameField = new TextField();
    private final TextField lastNameField = new TextField();
    private final TextField favoriteTeamField = new TextField();

    private final Label messageLabel = new Label();

    public FanController(FanRepository fanRepository) {
        this.fanRepository = fanRepository;
        buildInterface();
    }

    private void buildInterface() {
        Label titleLabel = new Label("Fan Information Database");
        titleLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        GridPane form = new GridPane();
        form.setHgap(10);
        form.setVgap(12);
        form.setPadding(new Insets(20));
        form.setAlignment(Pos.CENTER);

        form.add(new Label("Fan ID:"), 0, 0);
        form.add(idField, 1, 0);

        form.add(new Label("First Name:"), 0, 1);
        form.add(firstNameField, 1, 1);

        form.add(new Label("Last Name:"), 0, 2);
        form.add(lastNameField, 1, 2);

        form.add(new Label("Favorite Team:"), 0, 3);
        form.add(favoriteTeamField, 1, 3);

        Button displayButton = new Button("Display");
        Button updateButton = new Button("Update");

        displayButton.setOnAction(event -> displayRecord());
        updateButton.setOnAction(event -> updateRecord());

        HBox buttonBox = new HBox(10, displayButton, updateButton);
        buttonBox.setAlignment(Pos.CENTER);

        form.add(buttonBox, 1, 4);
        form.add(messageLabel, 1, 5);

        BorderPane.setAlignment(titleLabel, Pos.CENTER);
        BorderPane.setMargin(titleLabel, new Insets(15));

        root.setTop(titleLabel);
        root.setCenter(form);
    }

    public Parent getView() {
        return root;
    }

    public void displayRecord() {
        try {
            int id = readIdFromField();

            Optional<Fan> fan = fanRepository.findById(id);

            if (fan.isPresent()) {
                loadFanIntoFields(fan.get());
                messageLabel.setText("Record displayed.");
            } else {
                clearFanFields();
                messageLabel.setText("No fan found with ID " + id + ".");
            }

        } catch (NumberFormatException exception) {
            messageLabel.setText("Please enter a valid numeric ID.");
        } catch (SQLException exception) {
            messageLabel.setText("Database error: " + exception.getMessage());
        }
    }

    public void updateRecord() {
        try {
            int id = readIdFromField();

            Fan fan = new Fan(
                    id,
                    firstNameField.getText().trim(),
                    lastNameField.getText().trim(),
                    favoriteTeamField.getText().trim());

            boolean updated = fanRepository.update(fan);

            if (updated) {
                messageLabel.setText("Record updated.");
            } else {
                messageLabel.setText("No record was updated. Check the ID.");
            }

        } catch (NumberFormatException exception) {
            messageLabel.setText("Please enter a valid numeric ID.");
        } catch (SQLException exception) {
            messageLabel.setText("Database error: " + exception.getMessage());
        }
    }

    private int readIdFromField() {
        String idText = idField.getText().trim();

        if (idText.isEmpty()) {
            throw new NumberFormatException("ID is empty.");
        }

        return Integer.parseInt(idText);
    }

    private void loadFanIntoFields(Fan fan) {
        idField.setText(String.valueOf(fan.getId()));
        firstNameField.setText(fan.getFirstName());
        lastNameField.setText(fan.getLastName());
        favoriteTeamField.setText(fan.getFavoriteTeam());
    }

    private void clearFanFields() {
        firstNameField.clear();
        lastNameField.clear();
        favoriteTeamField.clear();
    }

    public TextField getIdField() {
        return idField;
    }

    public TextField getFirstNameField() {
        return firstNameField;
    }

    public TextField getLastNameField() {
        return lastNameField;
    }

    public TextField getFavoriteTeamField() {
        return favoriteTeamField;
    }

    public Label getMessageLabel() {
        return messageLabel;
    }
}