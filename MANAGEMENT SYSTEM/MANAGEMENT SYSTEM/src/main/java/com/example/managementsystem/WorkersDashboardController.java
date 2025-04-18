package com.example.managementsystem;

import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Timer;
import java.util.TimerTask;

public class WorkersDashboardController {

    @FXML private Label welcomeLabel;
    @FXML private Label clockLabel;
    @FXML private StackPane contentPane;
    @FXML private VBox sidebar;

    private String currentUsername;
    private String currentLoadedFXML = "";

    @FXML
    public void initialize() {
        startClock();
        welcomeLabel.setText("Welcome, " + (currentUsername != null ? currentUsername : "Employee"));
    }

    public void setCurrentUser(String username) {
        this.currentUsername = username;
        if (welcomeLabel != null) {
            welcomeLabel.setText("Welcome, " + username);
        }
    }

    private void startClock() {
        clockLabel = new Label();
        ((HBox) welcomeLabel.getParent()).getChildren().add(clockLabel);

        Timer timer = new Timer(true);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                Platform.runLater(() ->
                        clockLabel.setText(formatter.format(LocalDateTime.now()))
                );
            }
        }, 0, 1000);
    }

    @FXML
    private void showDashboard() {
        loadContent("/com/example/managementsystem/employee_home.fxml");
    }

    @FXML
    private void showNewBooking() {
        loadContent("/com/example/managementsystem/workers_new_booking.fxml");
    }

    @FXML
    private void showManageBookings() {
        loadContent("/com/example/managementsystem/workers_manage_bookings.fxml");
    }

    @FXML
    private void showProcessPayments() {
        loadContent("/com/example/managementsystem/workers_payments.fxml");
    }
    @FXML private void showRentalHistory() {
        loadContent("/com/example/managementsystem/workers_rental_history.fxml");
    }

    @FXML private void showSupportCenter() {
        loadContent("/com/example/managementsystem/workers_support.fxml");
    }

    @FXML private void showShiftSummary() {
        loadContent("/com/example/managementsystem/workers_shift_summary.fxml");
    }

    @FXML
    private void handleLogout() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/com/example/managementsystem/login.fxml"));
            Stage stage = (Stage) contentPane.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Failed to logout", e.getMessage());
        }
    }

    private void loadContent(String fxmlPath) {
        if (fxmlPath.equals(currentLoadedFXML)) return; // Prevent reloading same view

        try {
            Parent content = FXMLLoader.load(getClass().getResource(fxmlPath));
            FadeTransition ft = new FadeTransition(Duration.millis(300), content);
            ft.setFromValue(0);
            ft.setToValue(1);
            ft.play();

            contentPane.getChildren().setAll(content);
            currentLoadedFXML = fxmlPath;
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Failed to load content", e.getMessage());
        }
    }

    private void showAlert(String title, String header, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(message);
        alert.showAndWait();
    }
}