import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.*;

public class StatsDashboard {
    public static void display() {
        Stage window = new Stage();
        window.setTitle("Employee Statistics");

        TextArea statsArea = new TextArea();
        statsArea.setEditable(false);

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement()) {

            ResultSet total = stmt.executeQuery("SELECT COUNT(*) AS total FROM employees");
            total.next();
            int count = total.getInt("total");

            ResultSet avg = stmt.executeQuery("SELECT AVG(salary) AS avg_salary FROM employees");
            avg.next();
            double average = avg.getDouble("avg_salary");

            ResultSet max = stmt.executeQuery("SELECT name, salary FROM employees ORDER BY salary DESC LIMIT 1");
            max.next();
            String highestPaid = max.getString("name");
            double maxSalary = max.getDouble("salary");

            statsArea.setText("Total Employees: " + count + "\n" +
                              "Average Salary: $" + String.format("%.2f", average) + "\n" +
                              "Highest Paid: " + highestPaid + " ($" + maxSalary + ")");

        } catch (SQLException e) {
            statsArea.setText("Error: " + e.getMessage());
        }

        VBox layout = new VBox(statsArea);
        layout.setStyle("-fx-padding: 20;");
        window.setScene(new Scene(layout, 400, 200));
        window.show();
    }
}
