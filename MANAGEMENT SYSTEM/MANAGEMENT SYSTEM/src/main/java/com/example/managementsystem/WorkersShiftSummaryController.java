package com.example.managementsystem;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

public class WorkersShiftSummaryController {
    @FXML private ComboBox<String> issueComboBox;
    @FXML private TextArea descriptionTextArea;
    @FXML private Label describeIssueLabel;
    @FXML private Button submitButton;

    // Initialize method for debugging
    @FXML
    public void initialize() {
        System.out.println("Shift Summary Loaded!");

        // Example combo box and submit button setup
        issueComboBox.getItems().addAll("Issue 1", "Issue 2", "Issue 3");

        submitButton.setOnAction(event -> {
            System.out.println("Issue Submitted: " + descriptionTextArea.getText());
            // Logic to submit the issue, save data, etc.
        });
    }
}