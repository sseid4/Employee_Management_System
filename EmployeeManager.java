import java.sql.*;

public class EmployeeManager {

    public void searchEmployee(String field, String value) {
        String query = "SELECT * FROM employees WHERE " + field + " = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, value);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("empid") + ", Name: " + rs.getString("name")
                        + ", SSN: " + rs.getString("ssn") + ", Salary: " + rs.getDouble("salary"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateEmployee(int empId, String name, String title, String division) {
        String sql = "UPDATE employees SET name = ?, job_title = ?, division = ? WHERE empid = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, name);
            stmt.setString(2, title);
            stmt.setString(3, division);
            stmt.setInt(4, empId);

            int rows = stmt.executeUpdate();
            System.out.println(rows > 0 ? "Update successful." : "No employee found.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateSalaryByPercentage(double percent, double min, double max) {
        String sql = "UPDATE employees SET salary = salary * (1 + ? / 100) WHERE salary BETWEEN ? AND ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDouble(1, percent);
            stmt.setDouble(2, min);
            stmt.setDouble(3, max);

            int rows = stmt.executeUpdate();
            System.out.println(rows + " salaries updated.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addEmployee(String name, String ssn, String title, String division, double salary) {
        String sql = "INSERT INTO employees (name, ssn, job_title, division, salary) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, name);
            stmt.setString(2, ssn);
            stmt.setString(3, title);
            stmt.setString(4, division);
            stmt.setDouble(5, salary);

            stmt.executeUpdate();
            System.out.println("Employee added.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean deleteEmployee(int empId) {
        String sql = "DELETE FROM employees WHERE empid = ?";
    
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
    
            stmt.setInt(1, empId);
            int rows = stmt.executeUpdate();
            System.out.println(rows > 0 ? "Employee deleted." : "No employee found.");
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean deleteEmployeeByNameOrSSN(String input) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "DELETE FROM employees WHERE name = ? OR ssn = ?")) {
            stmt.setString(1, input);
            stmt.setString(2, input);
            int rows = stmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public void updateEmergencyContactBySSN(String ssn, String contact) throws SQLException {
        Connection conn = DBConnection.getConnection();
        PreparedStatement stmt = conn.prepareStatement("UPDATE employee SET emergencyContact=? WHERE ssn=?");
        stmt.setString(1, contact);
        stmt.setString(2, ssn);
        stmt.executeUpdate();
        conn.close();
    }
    
    public void updateEmergencyContactByID(int id, String contact) throws SQLException {
        Connection conn = DBConnection.getConnection();
        PreparedStatement stmt = conn.prepareStatement("UPDATE employee SET emergencyContact=? WHERE empid=?");
        stmt.setString(1, contact);
        stmt.setInt(2, id);
        stmt.executeUpdate();
        conn.close();
    }
    public void updateDivision(int id, String division) throws SQLException {
        Connection conn = DBConnection.getConnection();
        PreparedStatement stmt = conn.prepareStatement("UPDATE employee SET division=? WHERE empid=?");
        stmt.setString(1, division);
        stmt.setInt(2, id);
        stmt.executeUpdate();
        conn.close();
    }
        
    
}