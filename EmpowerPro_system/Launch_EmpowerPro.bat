@echo off
cd /d %~dp0

echo Compiling JavaFX app...
javac --module-path "C:\Users\saqui\Desktop\SoftwareDevelopment Project\javafx-sdk-24.0.1\lib" --add-modules javafx.controls,javafx.fxml -cp ".;mysql-connector-j-9.3.0.jar" *.java

echo Launching EmpowerPro...
java --module-path "C:\Users\saqui\Desktop\SoftwareDevelopment Project\javafx-sdk-24.0.1\lib" --add-modules javafx.controls,javafx.fxml -cp ".;mysql-connector-j-9.3.0.jar" MainFX
pause