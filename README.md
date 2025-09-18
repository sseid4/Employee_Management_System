# Employee_Management_System
![App Walkthrough](AppWalkthrough.gif)
# EmpowerPro Employee Management System


EmpowerPro is a JavaFX-based desktop application for managing employee records, including adding, updating, searching, exporting, and reporting employee data. It uses a MySQL database for persistent storage.


## Features
- Add, update, delete, and search employees
- View all employees in a list
- Update salary and emergency contact
- Export employee data to CSV
- Generate reports and view statistics


## Requirements
- Java JDK 17 or later (tested with JDK 24)
- JavaFX SDK (tested with 24.0.2)
- MySQL server
- MySQL Connector/J (included)


## Setup Instructions


### 1. Install Java and JavaFX
- Download and install the [Java JDK](https://adoptopenjdk.net/) (17+ recommended).
- Download the [JavaFX SDK](https://gluonhq.com/products/javafx/) and unzip it.


### 2. Install MySQL
- Download and install [MySQL Community Server](https://dev.mysql.com/downloads/mysql/).
- Start the MySQL server and create a database named `employeeData`.


### 3. Configure Database
- In MySQL, run:
   ```sql
   CREATE DATABASE employeeData;
   USE employeeData;
   CREATE TABLE employees (
      empid INT AUTO_INCREMENT PRIMARY KEY,
      name VARCHAR(100),
      ssn VARCHAR(20),
      job_title VARCHAR(100),
      division VARCHAR(100),
      salary DECIMAL(10,2)
   );
   CREATE TABLE employee (
      empid INT AUTO_INCREMENT PRIMARY KEY,
      name VARCHAR(100),
      ssn VARCHAR(20),
      job_title VARCHAR(100),
      division VARCHAR(100),
      salary DECIMAL(10,2),
      emergencyContact VARCHAR(100)
   );
   ```
- Update the MySQL username and password in `DBConnection.java` if needed.


### 4. Build and Run
- Open a terminal in the project directory.
- Compile and run with:
   ```sh
   javac --module-path "/path/to/javafx-sdk-24.0.2/lib" --add-modules javafx.controls,javafx.fxml -cp ".:mysql-connector-j-9.3.0.jar" *.java
   java --module-path "/path/to/javafx-sdk-24.0.2/lib" --add-modules javafx.controls,javafx.fxml -cp ".:mysql-connector-j-9.3.0.jar" MainFX
   ```
- Or use the provided `.command` script on macOS:
   ```sh
   bash "Run App - Employee Management System.command"
   ```


## Usage
- **Add**: Add a new employee with name, SSN, job title, division, and salary.
- **View All**: List all employees.
- **Update**: Update employee info by Employee ID.
- **Delete**: Remove an employee by ID.
- **CSV**: Export all employees to `employees_export.csv`.
- **Report/Stats**: Generate pay reports and view statistics.


## Notes
- The app must be able to connect to MySQL. If you see connection errors, check your DB credentials and that MySQL is running.
- JavaFX must be on the module path for both compile and run.
- The CSV export is saved in the project directory.


## Authors
- ShaMonte Knight
- Saquib Ahmed
- Siyam Seid
- Sabarno Dutta
- Sansca Raut


---
