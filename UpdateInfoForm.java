
// UpdateInfoForm.java — Updated with SSN validation and emergency contact
import javafx.animation.FadeTransition;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

public class UpdateInfoForm {
    public static void display(EmployeeManager manager, Runnable onDataChanged) {
        Stage window = new Stage();
        window.setTitle("Update Employee Info");

        TextField empId = new TextField();
        empId.setPromptText("Employee ID");
        empId.textProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal.matches("\\d+")) {
                empId.setStyle("-fx-border-color: green;");
            } else {
                empId.setStyle("-fx-border-color: red;");
            }
        });

        TextField name = new TextField();
        name.setPromptText("New Name");

        TextField ssn = new TextField();
        ssn.setPromptText("New SSN (9 digits)");

        TextField title = new TextField();
        title.setPromptText("New Job Title");

        TextField division = new TextField();
        division.setPromptText("New Division");

        TextField emergencyContact = new TextField();
        emergencyContact.setPromptText("New Emergency Contact");

        Button updateBtn = new Button("Update");
        updateBtn.setOnAction(e -> {
            if (!ssn.getText().trim().matches("\\d{9}")) {
                showAnimatedAlert("Invalid SSN", "Wrong format: SSN must be exactly 9 digits.");
                return;
            }
            try {
                int id = Integer.parseInt(empId.getText());
                manager.updateEmployee(id, name.getText(), ssn.getText(), title.getText());
                manager.updateDivision(id, division.getText());
                manager.updateEmergencyContactByID(id, emergencyContact.getText());
                if (onDataChanged != null) onDataChanged.run();
                showAnimatedAlert("Success", "Employee information updated.");
                window.close();
            } catch (NumberFormatException ex) {
                showAnimatedAlert("Error", "Please enter a valid numeric Employee ID.");
            } catch (Exception ex) {
                showAnimatedAlert("Error", "Could not update employee.");
                ex.printStackTrace();
            }
        });

        VBox layout = new VBox(10, empId, name, ssn, title, division, emergencyContact, updateBtn);
        layout.setStyle("-fx-padding: 20;");
        window.setScene(new Scene(layout, 300, 400));
        window.show();
    }

    private static void showAnimatedAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        Stage alertStage = (Stage) alert.getDialogPane().getScene().getWindow();
        FadeTransition fadeIn = new FadeTransition(Duration.millis(400), alert.getDialogPane());
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);
        fadeIn.play();
        alertStage.setOpacity(0);
        alertStage.show();
        FadeTransition fadeStage = new FadeTransition(Duration.millis(400), alertStage.getScene().getRoot());
        fadeStage.setFromValue(0);
        fadeStage.setToValue(1);
        fadeStage.play();
    }
}