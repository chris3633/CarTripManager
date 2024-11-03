package org.example.project1gui;

import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;

public class InputValidator {

    // Method to restrict input in a TextField to non-negative numbers only
    public static void setNonNegativeInput(TextField textField) {
        textField.setTextFormatter(new TextFormatter<>(change -> {
            String newText = change.getControlNewText();
            // Allow only numbers and control characters
            if (newText.isEmpty() || newText.matches("\\d*")) {
                return change; // Accept change if it's valid
            }
            return null; // Reject the change if it's not valid
        }));
    }
}
