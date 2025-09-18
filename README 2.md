# JavaFX Employee Management System

This is a complete JavaFX GUI application for managing employees connected to a MySQL database.

## ✅ Features
- Add new employee
- Search for employee by name, SSN, or ID
- Update employee info (name, title, division)
- Raise salary by percentage within a salary range
- Delete employee by ID

## 🚀 How to Run

1. Download and install the **MySQL Connector/J .jar file**:
   https://dev.mysql.com/downloads/connector/j/

2. Place the `.jar` file in the project directory.

3. Compile and run using:

```bash
javac -cp ".:mysql-connector-j-9.3.0.jar" *.java
java -cp ".:mysql-connector-j-9.3.0.jar" MainFX
```

(Use `;` instead of `:` on Windows)

## ⚙️ Requirements
- Java 11 or higher
- JavaFX SDK installed and configured
- MySQL running and `employeeData` database created with the following table:

```sql
CREATE TABLE employees (
    empid INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100),
    ssn VARCHAR(9),
    job_title VARCHAR(100),
    division VARCHAR(100),
    salary DOUBLE
);
```

## 💡 Tip
Use `MainFX.java` to launch the full GUI system.
