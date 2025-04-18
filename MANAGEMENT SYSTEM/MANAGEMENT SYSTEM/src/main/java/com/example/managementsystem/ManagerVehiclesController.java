package com.example.managementsystem;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import java.io.IOException;
import java.sql.*;

public class ManagerVehiclesController {

    @FXML private TableView<Vehicle> vehicleTable;
    @FXML private TableColumn<Vehicle, Integer> idColumn;
    @FXML private TableColumn<Vehicle, String> brandColumn;
    @FXML private TableColumn<Vehicle, String> modelColumn;
    @FXML private TableColumn<Vehicle, String> categoryColumn;
    @FXML private TableColumn<Vehicle, Integer> yearColumn;
    @FXML private TableColumn<Vehicle, Double> priceColumn;
    @FXML private TableColumn<Vehicle, String> statusColumn;
    @FXML private TextField searchField;

    private ObservableList<Vehicle> vehicleData = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        idColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
        brandColumn.setCellValueFactory(cellData -> cellData.getValue().brandProperty());
        modelColumn.setCellValueFactory(cellData -> cellData.getValue().modelProperty());
        categoryColumn.setCellValueFactory(cellData -> cellData.getValue().categoryProperty());
        yearColumn.setCellValueFactory(cellData -> cellData.getValue().yearProperty().asObject());
        priceColumn.setCellValueFactory(cellData -> cellData.getValue().pricePerDayProperty().asObject());
        statusColumn.setCellValueFactory(cellData -> cellData.getValue().statusProperty());

        loadVehicleData();
    }

    private void loadVehicleData() {
        vehicleData.clear();
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "SELECT * FROM vehicles";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                vehicleData.add(new Vehicle(
                        rs.getInt("vehicle_id"),
                        rs.getString("brand"),
                        rs.getString("model"),
                        rs.getString("category"),
                        rs.getInt("year"),
                        rs.getDouble("price_per_day"),
                        rs.getString("status")
                ));
            }
            vehicleTable.setItems(vehicleData);
        } catch (SQLException e) {
            showAlert("Database Error", "Could not load vehicle data", e.getMessage());
        }
    }

    @FXML
    private void handleAddVehicle() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("vehicle_dialog.fxml"));
            GridPane page = loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Add New Vehicle");
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            VehicleDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setVehicle(null);

            dialogStage.showAndWait();

            if (controller.isSaveClicked()) {
                Vehicle newVehicle = controller.getVehicle();
                saveVehicleToDatabase(newVehicle);
                vehicleData.add(newVehicle);
            }
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Could not load dialog", e.getMessage());
        }
    }

    private void saveVehicleToDatabase(Vehicle vehicle) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "INSERT INTO vehicles (brand, model, category, year, price_per_day, status) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, vehicle.getBrand());
            stmt.setString(2, vehicle.getModel());
            stmt.setString(3, vehicle.getCategory());
            stmt.setInt(4, vehicle.getYear());
            stmt.setDouble(5, vehicle.getPricePerDay());
            stmt.setString(6, vehicle.getStatus());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        vehicle.setId(generatedKeys.getInt(1));
                    }
                }
            }
        } catch (SQLException e) {
            showAlert("Database Error", "Could not save vehicle", e.getMessage());
        }
    }

    @FXML
    private void handleEditVehicle() {
        Vehicle selected = vehicleTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("vehicle_dialog.fxml"));
                GridPane page = loader.load();

                Stage dialogStage = new Stage();
                dialogStage.setTitle("Edit Vehicle");
                Scene scene = new Scene(page);
                dialogStage.setScene(scene);

                VehicleDialogController controller = loader.getController();
                controller.setDialogStage(dialogStage);
                controller.setVehicle(selected);

                dialogStage.showAndWait();

                if (controller.isSaveClicked()) {
                    updateVehicleInDatabase(selected);
                    vehicleTable.refresh();
                }
            } catch (IOException e) {
                e.printStackTrace();
                showAlert("Error", "Could not load dialog", e.getMessage());
            }
        } else {
            showAlert("Error", "No vehicle selected", "Please select a vehicle to edit.");
        }
    }

    private void updateVehicleInDatabase(Vehicle vehicle) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "UPDATE vehicles SET brand = ?, model = ?, category = ?, year = ?, price_per_day = ?, status = ? WHERE vehicle_id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, vehicle.getBrand());
            stmt.setString(2, vehicle.getModel());
            stmt.setString(3, vehicle.getCategory());
            stmt.setInt(4, vehicle.getYear());
            stmt.setDouble(5, vehicle.getPricePerDay());
            stmt.setString(6, vehicle.getStatus());
            stmt.setInt(7, vehicle.getId());

            stmt.executeUpdate();
        } catch (SQLException e) {
            showAlert("Database Error", "Could not update vehicle", e.getMessage());
        }
    }

    @FXML
    private void handleDeleteVehicle() {
        Vehicle selected = vehicleTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            try (Connection conn = DatabaseConnection.getConnection()) {
                String sql = "DELETE FROM vehicles WHERE vehicle_id = ?";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setInt(1, selected.getId());

                int rowsAffected = stmt.executeUpdate();
                if (rowsAffected > 0) {
                    vehicleData.remove(selected);
                    showAlert("Success", null, "Vehicle deleted successfully.");
                }
            } catch (SQLException e) {
                showAlert("Database Error", "Could not delete vehicle", e.getMessage());
            }
        } else {
            showAlert("Error", "No vehicle selected", "Please select a vehicle to delete.");
        }
    }

    @FXML
    private void handleSearch() {
        String searchTerm = searchField.getText().toLowerCase();
        if (searchTerm.isEmpty()) {
            vehicleTable.setItems(vehicleData);
            return;
        }

        ObservableList<Vehicle> filteredData = FXCollections.observableArrayList();
        for (Vehicle vehicle : vehicleData) {
            if (vehicle.getBrand().toLowerCase().contains(searchTerm) ||
                    vehicle.getModel().toLowerCase().contains(searchTerm) ||
                    vehicle.getCategory().toLowerCase().contains(searchTerm)) {
                filteredData.add(vehicle);
            }
        }
        vehicleTable.setItems(filteredData);
    }

    private void showAlert(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
