// AddEmployeeForm.java — Final freeze fix using alert.showAndWait() and Modality import
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Duration;

public class AddEmployeeForm {
    public static void display(EmployeeManager manager, Runnable onDataChanged) {
        Stage window = new Stage();
        window.setTitle("Add Employee");

        TextField name = new TextField("John Doe");
        name.setPromptText("Name");

        TextField ssn = new TextField("123456789");
        ssn.setPromptText("SSN");

        TextField emergencyContact = new TextField();
        emergencyContact.setPromptText("Emergency Contact");

        ComboBox<String> title = new ComboBox<>(FXCollections.observableArrayList(
                "Engineer", "Analyst", "Manager", "Designer", "Technician"
        ));
        title.setPromptText("Job Title");
        title.setEditable(true);
        title.getEditor().textProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal.trim().isEmpty()) {
                title.setStyle("-fx-border-color: green;");
            } else {
                title.setStyle("-fx-border-color: red;");
            }
        });

        ComboBox<String> division = new ComboBox<>(FXCollections.observableArrayList(
                "IT", "HR", "Finance", "Operations", "Marketing"
        ));
        division.setPromptText("Division");
        division.setEditable(true);
        division.getEditor().textProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal.trim().isEmpty()) {
                division.setStyle("-fx-border-color: green;");
            } else {
                division.setStyle("-fx-border-color: red;");
            }
        });

        TextField salary = new TextField();
        salary.setPromptText("Salary");
        salary.textProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal.matches("\\d+(\\.\\d+)?")) {
                salary.setStyle("-fx-border-color: green;");
            } else {
                salary.setStyle("-fx-border-color: red;");
            }
        });

        Button submit = new Button("Submit");
        submit.setOnAction(e -> {
            String ssnInput = ssn.getText().trim();

            if (!ssnInput.matches("\\d{9}")) {
                showAnimatedAlert("Invalid SSN", "Wrong format: SSN must be exactly 9 digits (no dashes).\nExample: 123456789");
                return;
            }

            String salaryInput = salary.getText().trim();
            if (!salaryInput.matches("\\d+(\\.\\d+)?")) {
                showAnimatedAlert("Error", "Please enter a valid number for salary.");
                return;
            }

            try {
                // Log current DB URL at runtime for confirmation
                System.out.println("[DEBUG] Connecting to DB: " + DBConnection.class.getDeclaredField("URL").get(null));
            } catch (Exception ex) {
                System.out.println("[DEBUG] Could not access DB URL");
            }

            try {
                double sal = Double.parseDouble(salaryInput);
                manager.addEmployee(name.getText(), ssnInput, title.getEditor().getText(), division.getEditor().getText(), sal);
                manager.updateEmergencyContactBySSN(ssnInput, emergencyContact.getText());

                if (onDataChanged != null) onDataChanged.run();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setHeaderText(null);
                alert.setContentText("Employee added successfully!");
                alert.initModality(Modality.APPLICATION_MODAL);
                alert.setOnHidden(ev -> {
                    if (onDataChanged != null) onDataChanged.run();
                    window.close();
                    Platform.runLater(() -> {
                        for (Window w : Stage.getWindows()) {
                            if (w.isShowing()) {
                                w.requestFocus();
                                break;
                            }
                        }
                    });
                });
                alert.showAndWait();
                Platform.runLater(() -> {
                    Stage primaryStage = (Stage) Stage.getWindows().filtered(Window::isShowing).get(0);
                    primaryStage.requestFocus();
                });
            } catch (Exception ex) {
                showAnimatedAlert("Error", "Something went wrong while adding employee.");
                ex.printStackTrace();
            }
        });

        VBox layout = new VBox(10, name, ssn, emergencyContact, title, division, salary, submit);
        layout.setStyle("-fx-padding: 20;");
        window.setScene(new Scene(layout, 300, 400));
        window.show();
    }

    private static void showAnimatedAlert(String title, String message) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(title);
            alert.setHeaderText(null);
            alert.setContentText(message);
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.showAndWait();
        });
    }
}
