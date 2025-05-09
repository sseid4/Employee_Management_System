import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ReportGenerator {
    public static void generatePayReports(ListView<String> resultList) {
        ObservableList<String> reportData = FXCollections.observableArrayList();

        // Simulated Pay Statement History Report
        reportData.add("-- Full-Time Employees with Pay Statement History --");
        reportData.add("101 | Alice Brown | Engineer | $72000 | Jan: $6000, Feb: $6000");
        reportData.add("102 | Brian Lee | Analyst | $65000 | Jan: $5400, Feb: $5400");

        reportData.add("\n-- Total Pay by Job Title (Monthly Avg) --");
        reportData.add("Engineer: $6000");
        reportData.add("Analyst: $5400");
        reportData.add("Manager: $7000");

        reportData.add("\n-- Total Pay by Division (Monthly Avg) --");
        reportData.add("IT: $18,000");
        reportData.add("HR: $11,000");
        reportData.add("Finance: $14,500");

        resultList.setItems(reportData);
        resultList.setVisible(true);
        resultList.scrollTo(0);
    }

    public static void exportToCSV(ListView<String> resultList) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Report As CSV");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));
        File file = fileChooser.showSaveDialog(null);

        if (file != null) {
            try (FileWriter writer = new FileWriter(file)) {
                for (String line : resultList.getItems()) {
                    writer.write(line.replace("|", ",") + "\n");
                }
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Export Successful");
                alert.setHeaderText(null);
                alert.setContentText("CSV report saved to:\n" + file.getAbsolutePath());
                alert.showAndWait();
            } catch (IOException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Export Failed");
                alert.setHeaderText(null);
                alert.setContentText("Error saving file: " + e.getMessage());
                alert.showAndWait();
            }
        }
    }
}
