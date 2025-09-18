#!/bin/bash
cd "$(dirname "$0")"

# Compile everything
javac --module-path /Users/taek/Downloads/javafx-sdk-24/lib \
      --add-modules javafx.controls,javafx.fxml \
      -cp ".:mysql-connector-j-9.3.0.jar" *.java

# Run the main app
java --module-path /Users/taek/Downloads/javafx-sdk-24/lib \
     --add-modules javafx.controls,javafx.fxml \
     -cp ".:mysql-connector-j-9.3.0.jar" MainFX
