#!/bin/bash

# Navigate to the directory this script is in
cd "$(dirname "$0")"

# Compile all Java files with JavaFX and MySQL connector
javac --module-path /Users/taek/Downloads/javafx-sdk-24/lib \
      --add-modules javafx.controls,javafx.fxml \
      -cp ".:mysql-connector-j-9.3.0.jar" *.java

# Run the JavaFX application
java --module-path /Users/taek/Downloads/javafx-sdk-24/lib \
     --add-modules javafx.controls,javafx.fxml \
     -cp ".:mysql-connector-j-9.3.0.jar" MainFX
