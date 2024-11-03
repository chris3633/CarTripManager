package org.example.project1gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.util.ArrayList;

import javafx.geometry.Pos;
import javafx.geometry.Insets;


public class MainApp extends Application {

    private Vehicle vehicle;
    private ArrayList<Location> locations;

    public static void main(String[] args) {
        launch(args);
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @Override
    public void start(Stage primaryStage) {

        // Initialize locations
        locations = new ArrayList<>();
        locations.add(new Location("Cleveland", 0, 0, true));
        locations.add(new Location("North Pole", 500, -100, true));
        locations.add(new Location("Campbell Hill", -50, -50, false));
        locations.add(new Location("Tokyo", -100, 1000, true));
        locations.add(new Location("Chicago", -200, 75, true));
        locations.add(new Location("Erie PA", 150, 0, true));
        locations.add(new Location("Allegheny Nation Forest", 300, -75, false));

        // VBox Layout for UI components
        VBox layout = new VBox(10);
        layout.setAlignment(Pos.TOP_CENTER);
        layout.setPadding(new Insets(20));

        // Create a label for the Main Title
        Label titleLabel = new Label("Car Trip Manager");
        titleLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 24px; -fx-text-fill: gray;");
        // Add the title label to the top of the layout
        layout.getChildren().add(0, titleLabel);

        // Tank size input
        TextField fuelCapacityField = new TextField();
        fuelCapacityField.setPromptText("Enter max fuel capacity");
        InputValidator.setNonNegativeInput(fuelCapacityField);

        // MPG input
        TextField mpgField = new TextField();
        mpgField.setPromptText("Enter miles per gallon");
        InputValidator.setNonNegativeInput(mpgField);

        // statusLabel and fuelLabel
        Label statusLabel = new Label();
        Label fuelLabel = new Label("Remaining Fuel: N/A gallons");


        // Button to initialize the vehicle (to confirm Tank Size and MPG)
        Button initVehicleButton = new Button("Set Values");
        initVehicleButton.setOnAction(e -> {
            try {
                double maxFuelCapacity = Double.parseDouble(fuelCapacityField.getText());
                double milesPerGallon = Double.parseDouble(mpgField.getText());
                Location initialLocation = new Location("Cleveland", 0, 0, true);

                // Initialize the vehicle
                vehicle = new Vehicle(initialLocation, maxFuelCapacity, maxFuelCapacity, milesPerGallon);

                // Set the miles per gallon using the setter method
                vehicle.setMilesPerGallon(milesPerGallon);

                // Call the setMaxFuelCapacity method to set the fuel capacity
                vehicle.setMaxFuelCapacity(maxFuelCapacity);

                // Set the current fuel to the maximum fuel capacity
                vehicle.setCurrentFuel(maxFuelCapacity);

                // Update the statusLabel and fuelLabel
                statusLabel.setText("Vehicle initialized in " + initialLocation.getName() + ".\nCurrent fuel: " + String.format("%.2f", maxFuelCapacity) + " gallons.");
                fuelLabel.setText("Remaining Fuel: " + String.format("%.2f", vehicle.getCurrentFuel()) + " gallons");
            } catch (NumberFormatException ex) {
                showError("Please enter valid numeric values for fuel capacity and miles per gallon.");
            }
        });

        // Create an HBox for locationDropdown and flyButton
        HBox locationFlyBox = new HBox(10);
        locationFlyBox.setAlignment(Pos.CENTER);

        // Dropdown for locations
        ComboBox<String> locationDropdown = new ComboBox<>();
        for (Location loc : locations) {
            locationDropdown.getItems().add(loc.getName());
        }
        locationDropdown.setPromptText("Choose a destination");

        // Button to fly to a selected location
        Button flyButton = new Button("Fly to Location!");
        locationFlyBox.getChildren().addAll(locationDropdown, flyButton);

        flyButton.setOnAction(e -> {
            if (vehicle == null) {
                statusLabel.setText("Please initialize the vehicle first.");
                return; // Exit if vehicle is not initialized
            }

            String selectedLocationName = locationDropdown.getValue();
            if (selectedLocationName != null) {
                try {
                    for (Location loc : locations) {
                        if (loc.getName().equals(selectedLocationName)) {
                            // Call the flyTo method, which handles the logic
                            String message = vehicle.flyTo(loc);
                            statusLabel.setText(message);
                            // Update the fuel label only after the flyTo method is called
                            fuelLabel.setText("Remaining Fuel: " + String.format("%.2f", vehicle.getCurrentFuel()) + " gallons");
                            return;
                        }
                    }
                    statusLabel.setText("Location not found.");
                } catch (Exception ex) {
                    statusLabel.setText("An error occurred while flying to the location: " + ex.getMessage());
                }
            } else {
                statusLabel.setText("Please select a destination.");
            }
        });


        // Create an HBox for refuelField and refuelButton
        HBox refuelBox = new HBox(10);
        refuelBox.setAlignment(Pos.CENTER);

        // Button to refuel
        TextField refuelField = new TextField();
        InputValidator.setNonNegativeInput(refuelField);
        refuelField.setPromptText("Enter fuel to add");
        Button refuelButton = new Button("Refuel");
        refuelButton.setOnAction(e -> {
            if (vehicle.getCurrentLocation().hasGasStation()) {
                try {
                    double fuelToAdd = Double.parseDouble(refuelField.getText());
                    String message = vehicle.refuel(fuelToAdd);
                    statusLabel.setText(message);
                    statusLabel.setText(message);
                    // Update the fuel label after refueling
                    fuelLabel.setText("Remaining Fuel: " + String.format("%.2f", vehicle.getCurrentFuel()) + " gallons");
                } catch (NumberFormatException ex) {
                    statusLabel.setText("Please enter a valid number for fuel to add.");
                } catch (IllegalArgumentException ex) {
                    statusLabel.setText(ex.getMessage());
                } catch (Exception ex) {
                    statusLabel.setText("An unexpected error occurred while refueling: " + ex.getMessage());
                }
            } else {
                statusLabel.setText("No gas station available at " + vehicle.getCurrentLocation().getName() + ".");
            }
        });

        // Add both components to the HBox
        refuelBox.getChildren().addAll(refuelField, refuelButton);


        // Button to open the new scene (to add a New Location)
        Button opennewLocationSceneButton = new Button("Add New Location");
        locationFlyBox.getChildren().add(opennewLocationSceneButton);


        // Exit Button
        Button exitButton = new Button("Exit");
        exitButton.setOnAction(e -> {
            statusLabel.setText("Exiting...");
            primaryStage.close();
        });

        // Create an HBox for the exit button and set it to align to the right
        HBox exitButtonBox = new HBox(exitButton);
        exitButtonBox.setAlignment(Pos.CENTER_RIGHT);

        // SPACERS

        // Spacer after set value button
        Region setValueSpacer = new Region();
        setValueSpacer.setMinHeight(20); // Set minimum height for spacing

        // Spacer after status label
        Region statusLabelSpacer = new Region();
        statusLabelSpacer.setMinHeight(40); // Set minimum height for spacing

        // Spacer to push the exit button to the bottom
        Region bottomSpacer = new Region();
        VBox.setVgrow(bottomSpacer, Priority.ALWAYS);


        // Add all elements to layout
        layout.getChildren().addAll(fuelCapacityField, mpgField, initVehicleButton, setValueSpacer, statusLabel, statusLabelSpacer, locationFlyBox, refuelBox, fuelLabel, bottomSpacer, exitButtonBox

        );

        // Set up the Main Scene
        Scene scene = new Scene(layout, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Car Trip Manager");
        primaryStage.show();


        // Create layout for the add location scene
        VBox newLocationSceneLayout = new VBox(10);
        newLocationSceneLayout.setAlignment(Pos.CENTER);
        newLocationSceneLayout.setPadding(new Insets(20));

        // Label for the new scene
        Label newLocationSceneLabel = new Label("Add New Location");
        newLocationSceneLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: darkgray;");
        newLocationSceneLayout.getChildren().add(newLocationSceneLabel);

        TextField newLocationNameField = new TextField();
        newLocationNameField.setPromptText("Enter location name");

        TextField newLatitudeField = new TextField();
        newLatitudeField.setPromptText("Enter latitude");

        TextField newLongitudeField = new TextField();
        newLongitudeField.setPromptText("Enter longitude");

        CheckBox hasGasStationCheckBox = new CheckBox("Gas Station Available?");

        newLocationSceneLayout.getChildren().addAll(newLocationNameField, newLatitudeField, newLongitudeField, hasGasStationCheckBox);

        // Save Button for the new location
        Button saveLocationButton = new Button("Save Location");
        newLocationSceneLayout.getChildren().add(saveLocationButton);


        // Logic for the Save New Location Button
        saveLocationButton.setOnAction(e -> {
            try {
                String name = newLocationNameField.getText();
                double latitude = Double.parseDouble(newLatitudeField.getText());
                double longitude = Double.parseDouble(newLongitudeField.getText());
                boolean hasGasStation = hasGasStationCheckBox.isSelected();

                // Validate input (e.g., name must not be empty)
                if (name.trim().isEmpty()) {
                    throw new IllegalArgumentException("Location name cannot be empty.");
                }

                // Create a new Location and add it to the list
                Location newLocation = new Location(name, latitude, longitude, hasGasStation);
                locations.add(newLocation);

                // Update the dropdown in the original scene
                locationDropdown.getItems().add(name);

                // Clear fields after saving
                newLocationNameField.clear();
                newLatitudeField.clear();
                newLongitudeField.clear();
                hasGasStationCheckBox.setSelected(false);

                // Automatically switch back to the original scene after saving
                primaryStage.setScene(scene);
            } catch (NumberFormatException ex) {
                showError("Please enter valid numeric values for latitude and longitude.");
            } catch (IllegalArgumentException ex) {
                showError(ex.getMessage());
            } catch (Exception ex) {
                showError("An unexpected error occurred: " + ex.getMessage());
            }
        });


        // Create a back button
        Button backButton = new Button("Back");
        newLocationSceneLayout.getChildren().add(backButton);

        // Set up the new scene
        Scene newLocationScene = new Scene(newLocationSceneLayout, 800, 600);

        // Action to switch to the new scene (New Location)
        opennewLocationSceneButton.setOnAction(e -> {
            primaryStage.setScene(newLocationScene);
        });

        // Action to switch back to the original Main scene
        backButton.setOnAction(e -> {
            primaryStage.setScene(scene);
        });


    }


}