import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;

public class CSVExporter {
    public static void export() {
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM employees");
             FileWriter writer = new FileWriter("employees_export.csv")) {

            writer.write("empid,name,ssn,job_title,division,salary\n");

            while (rs.next()) {
                writer.write(rs.getInt("empid") + "," +
                             rs.getString("name") + "," +
                             rs.getString("ssn") + "," +
                             rs.getString("job_title") + "," +
                             rs.getString("division") + "," +
                             rs.getDouble("salary") + "\n");
            }

            System.out.println("CSV export completed.");
        } catch (SQLException | IOException e) {
            System.err.println("Export failed: " + e.getMessage());
        }
    }
}
