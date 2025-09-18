
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Duration;

public class DeleteEmployeeForm {
    private static ObservableList<String> deletedEmployees = FXCollections.observableArrayList();
    private static Runnable onDataChanged;

    public static void display(EmployeeManager manager, Runnable refreshCallback) {
        onDataChanged = refreshCallback;

        Stage window = new Stage();
        window.setTitle("Delete Employee");

        TextField inputField = new TextField();
        inputField.setPromptText("Enter employee number");

        Button deleteBtn = new Button("Delete");
        deleteBtn.setOnAction(e -> {
            String input = inputField.getText().trim();

            if (input.isEmpty()) {
                showAnimatedAlert("Missing Input", "Please enter an employee number.");
                return;
            }

            Alert confirmDialog = new Alert(Alert.AlertType.CONFIRMATION);
            confirmDialog.setTitle("Confirm Deletion");
            confirmDialog.setHeaderText(null);
            confirmDialog.setContentText("Are you sure you want to delete employee: " + input + "?");

            confirmDialog.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    boolean deleted = false;

                    try {
                        int empId = Integer.parseInt(input);
                        deleted = manager.deleteEmployee(empId);
                    } catch (NumberFormatException nfe) {
                        deleted = manager.deleteEmployeeByNameOrSSN(input);
                    }

                    if (deleted) {
                        deletedEmployees.add(input);
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Success");
                        alert.setHeaderText(null);
                        alert.setContentText("Employee deleted successfully.");

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

                        alert.show();
                    }
                }
            });
        });

        Button trashBtn = new Button("View Deleted");
        trashBtn.setOnAction(e -> showTrashBin());

        VBox layout = new VBox(10, inputField, deleteBtn, trashBtn);
        layout.setStyle("-fx-padding: 20;");
        window.setScene(new Scene(layout, 300, 180));
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

    private static void showTrashBin() {
        Stage trashStage = new Stage();
        trashStage.setTitle("🗑️ Recently Deleted Employees");

        javafx.scene.control.ListView<String> deletedList = new javafx.scene.control.ListView<>(deletedEmployees);

        VBox box = new VBox(10, new Label("Recently Removed:"), deletedList);
        box.setStyle("-fx-padding: 15;");

        Scene scene = new Scene(box, 300, 250);
        trashStage.setScene(scene);
        trashStage.show();
    }
}