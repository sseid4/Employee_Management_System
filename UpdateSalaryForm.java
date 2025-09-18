// UpdateSalaryForm.java
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class UpdateSalaryForm {
    public static void display(EmployeeManager manager) {
        Stage window = new Stage();
        window.setTitle("Update Salary by Percentage");

        TextField percentField = new TextField();
        percentField.setPromptText("Percentage Increase");

        TextField minSalary = new TextField();
        minSalary.setPromptText("Min Salary");

        TextField maxSalary = new TextField();
        maxSalary.setPromptText("Max Salary");

        Button updateBtn = new Button("Apply Raise");
        updateBtn.setOnAction(e -> {
            try {
                double percent = Double.parseDouble(percentField.getText());
                double min = Double.parseDouble(minSalary.getText());
                double max = Double.parseDouble(maxSalary.getText());
                manager.updateSalaryByPercentage(percent, min, max);
                window.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        VBox layout = new VBox(10, percentField, minSalary, maxSalary, updateBtn);
        layout.setStyle("-fx-padding: 20;");
        window.setScene(new Scene(layout, 300, 250));
        window.show();
    }
}