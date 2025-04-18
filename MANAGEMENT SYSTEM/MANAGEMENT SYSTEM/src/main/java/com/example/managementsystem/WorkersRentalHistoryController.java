package com.example.managementsystem;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class WorkersRentalHistoryController {
    @FXML private TableView<?> view;  // The TableView for rental history
    @FXML private TableColumn<?, ?> bookingIdColumn;
    @FXML private TableColumn<?, ?> customerNameColumn;
    @FXML private TableColumn<?, ?> vehicleInfoColumn;
    @FXML private TableColumn<?, ?> startDateColumn;
    @FXML private TableColumn<?, ?> endDateColumn;

    // Initialize method for debugging
    @FXML
    public void initialize() {
        System.out.println("Rental History Loaded!");

        // Example initialization (can be replaced with actual data binding or populating logic)
        // Set up columns, data source, etc.
    }
}