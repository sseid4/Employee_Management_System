// ViewAllEmployeesForm.java
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.sql.*;

public class ViewAllEmployeesForm {
    public static void display(EmployeeManager manager) {
        Stage window = new Stage();
        window.setTitle("All Employees");

        ListView<String> employeeList = new ListView<>();
        TextArea detailArea = new TextArea();
        detailArea.setEditable(false);

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM employees")) {

            while (rs.next()) {
                String displayName = rs.getInt("empid") + ": " + rs.getString("name");
                employeeList.getItems().add(displayName);
            }
        } catch (SQLException e) {
            detailArea.setText("Error loading employees: " + e.getMessage());
        }

        employeeList.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                int id = Integer.parseInt(newVal.split(":")[0].trim());
                try (Connection conn = DBConnection.getConnection();
                     PreparedStatement stmt = conn.prepareStatement("SELECT * FROM employees WHERE empid = ?")) {

                    stmt.setInt(1, id);
                    ResultSet rs = stmt.executeQuery();

                    if (rs.next()) {
                        detailArea.setText("ID: " + rs.getInt("empid") +
                                "\nName: " + rs.getString("name") +
                                "\nSSN: " + rs.getString("ssn") +
                                "\nJob Title: " + rs.getString("job_title") +
                                "\nDivision: " + rs.getString("division") +
                                "\nSalary: $" + rs.getDouble("salary"));
                    }
                } catch (SQLException e) {
                    detailArea.setText("Error retrieving info: " + e.getMessage());
                }
            }
        });

        HBox layout = new HBox(10, employeeList, detailArea);
        layout.setStyle("-fx-padding: 20;");
        window.setScene(new Scene(layout, 600, 400));
        window.show();
    }
}
