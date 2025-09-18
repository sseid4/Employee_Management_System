// SearchEmployeeForm.java (Updated to display result in-app)
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.*;

public class SearchEmployeeForm {
    public static void display(EmployeeManager manager) {
        Stage window = new Stage();
        window.setTitle("Search Employee");

        ComboBox<String> fieldChoice = new ComboBox<>();
        fieldChoice.getItems().addAll("name", "ssn", "empid");
        fieldChoice.setValue("name");

        TextField inputField = new TextField();
        inputField.setPromptText("Enter value");

        TextArea resultArea = new TextArea();
        resultArea.setEditable(false);

        Button searchBtn = new Button("Search");
        searchBtn.setOnAction(e -> {
            String field = fieldChoice.getValue();
            String value = inputField.getText();
            resultArea.clear();
            try (Connection conn = DBConnection.getConnection();
                 PreparedStatement stmt = conn.prepareStatement("SELECT * FROM employees WHERE " + field + " = ?")) {

                stmt.setString(1, value);
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    resultArea.setText("ID: " + rs.getInt("empid") +
                            "\nName: " + rs.getString("name") +
                            "\nSSN: " + rs.getString("ssn") +
                            "\nJob Title: " + rs.getString("job_title") +
                            "\nDivision: " + rs.getString("division") +
                            "\nSalary: $" + rs.getDouble("salary"));
                } else {
                    resultArea.setText("No employee found with that info.");
                }
            } catch (SQLException ex) {
                resultArea.setText("Error: " + ex.getMessage());
            }
        });

        VBox layout = new VBox(10, new Label("Search by:"), fieldChoice, inputField, searchBtn, resultArea);
        layout.setStyle("-fx-padding: 20;");
        window.setScene(new Scene(layout, 350, 350));
        window.show();
    }
}
