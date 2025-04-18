package com.example.managementsystem;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class WorkersSupportController {
    @FXML private Label bookingsProcessedLabel;
    @FXML private Label paymentsHandledLabel;
    @FXML private Label hoursWorkedLabel;
    @FXML private Button exportSummaryButton;
    @FXML private TextField bookingsProcessedField;
    @FXML private TextField paymentsHandledField;
    @FXML private TextField hoursWorkedField;

    // Initialize method for debugging
    @FXML
    public void initialize() {
        System.out.println("Support Center Loaded!");

        // Example functionality (e.g., export logic, field data handling)
        exportSummaryButton.setOnAction(event -> {
            System.out.println("Export Summary Clicked!");
            // Logic to export or process summary
        });
    }
}