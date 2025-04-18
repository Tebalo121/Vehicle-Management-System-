package com.example.managementsystem;

import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.Alert;

public class ManagerNotificationsController {

    @FXML private TabPane tabpane;

    @FXML
    public void handleShow() {
        Tab criticalTab = new Tab("Critical Alerts");
        Tab infoTab = new Tab("Info Alerts");

        criticalTab.setContent(new javafx.scene.control.Label("Overdue Bookings, Low Stock, etc."));
        infoTab.setContent(new javafx.scene.control.Label("Customer Registered, Report Ready, etc."));

        tabpane.getTabs().setAll(criticalTab, infoTab);
    }

    @FXML
    public void handleMarkRead() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Notifications");
        alert.setHeaderText(null);
        alert.setContentText("All notifications marked as read.");
        alert.showAndWait();
    }
}