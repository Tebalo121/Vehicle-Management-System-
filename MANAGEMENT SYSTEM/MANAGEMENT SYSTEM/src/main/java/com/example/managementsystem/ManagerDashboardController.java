package com.example.managementsystem;

import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Timer;
import java.util.TimerTask;

public class ManagerDashboardController {

    @FXML private Label welcomeLabel;
    @FXML private Label dateTimeLabel;
    @FXML private StackPane contentPane;
    @FXML private VBox sidebar;
    @FXML private Button toggleSidebarBtn;

    private String currentUsername;
    private boolean darkMode = false;

    public void initialize() {
        startClock();
        welcomeLabel.setText("Welcome, " + (currentUsername != null ? currentUsername : "Admin"));
    }

    public void setCurrentUser(String username) {
        this.currentUsername = username;
        if (welcomeLabel != null) {
            welcomeLabel.setText("Welcome, " + username);
        }
    }

    private void startClock() {
        Timer timer = new Timer(true);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                Platform.runLater(() -> dateTimeLabel.setText(LocalDateTime.now().format(formatter)));
            }
        }, 0, 1000);
    }

    @FXML private void showDashboard() {
        loadContent("/com/example/managementsystem/admin_home.fxml");
    }
    @FXML
    private void showNotifications() {
        loadContent("/com/example/managementsystem/manager_notification.fxml");
    }
    @FXML private void showVehicleManagement() {
        loadContent("/com/example/managementsystem/manager_vehicles.fxml");
    }

    @FXML private void showCustomerManagement() {
        loadContent("/com/example/managementsystem/manager_customers.fxml");
    }

    @FXML private void showUserManagement() {
        loadContent("/com/example/managementsystem/manager_users.fxml");
    }

    @FXML private void showReports() {
        loadContent("/com/example/managementsystem/manager_reports.fxml");
    }

    @FXML private void showPayments() {
        loadContent("/com/example/managementsystem/manager_payments.fxml");
    }

    @FXML private void handleLogout() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/com/example/managementsystem/login.fxml"));
            contentPane.getScene().setRoot(root);
        } catch (IOException e) {
            showError("Failed to logout.");
            e.printStackTrace();
        }
    }

    private void loadContent(String fxmlPath) {
        try {
            Parent content = FXMLLoader.load(getClass().getResource(fxmlPath));
            FadeTransition fade = new FadeTransition(Duration.millis(300), content);
            fade.setFromValue(0.0);
            fade.setToValue(1.0);
            contentPane.getChildren().setAll(content);
            fade.play();
        } catch (IOException e) {
            showError("Could not load page: " + fxmlPath);
            e.printStackTrace();
        }
    }

    @FXML private void toggleSidebar() {
        sidebar.setVisible(!sidebar.isVisible());
    }

    @FXML private void toggleDarkMode() {
        darkMode = !darkMode;
        String style = darkMode
                ? "-fx-background-color: #1e1e1e; -fx-text-fill: white;"
                : "-fx-background-color: purple; -fx-text-fill: white;";
        sidebar.setStyle(style);
    }


    private void showError(String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}