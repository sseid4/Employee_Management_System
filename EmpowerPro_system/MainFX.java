// MainFX.java — EmpowerPro with Elite Scroll, Expanded Results, Animated Search Focus ⚡️🖤
import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.sql.*;

public class MainFX extends Application {
    EmployeeManager manager = new EmployeeManager();
    ObservableList<String> searchResults = FXCollections.observableArrayList();
    private static final String ICON_DIR = "file:./icons/";
    ListView<String> resultList = new ListView<>(searchResults);

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        stage.setTitle("🚀 EmpowerPro | Smart Employee Console");

        Label header = new Label("EmpowerPro");
        header.setFont(Font.font("System", FontWeight.BOLD, 32));
        header.setTextFill(Color.WHITE);

        Label subtitle = new Label("Smart Employee Console");
        subtitle.setFont(Font.font("System", FontWeight.NORMAL, 16));
        subtitle.setTextFill(Color.web("#B0B0B0"));

        Button exitBtn = new Button("❌");
        exitBtn.setFont(Font.font(12));
        exitBtn.setStyle("-fx-background-color: transparent; -fx-text-fill: red;");
        exitBtn.setOnAction(e -> Platform.exit());
        HBox exitBox = new HBox(exitBtn);
        exitBox.setAlignment(Pos.TOP_LEFT);

        TextField searchBar = new TextField();
        searchBar.setPromptText("Search by name, SSN, or ID");
        searchBar.setStyle("-fx-background-radius: 15; -fx-border-radius: 15; -fx-background-color: #1E1E1E; -fx-text-fill: #eee; -fx-prompt-text-fill: #777;");
        searchBar.setMaxWidth(380);

        Button searchBtn = new Button();
        ImageView searchIcon = new ImageView(new Image("file:./icons/search.png"));
        searchIcon.setFitWidth(20);
        searchIcon.setFitHeight(20);
        searchBtn.setGraphic(searchIcon);
        searchBtn.setStyle("-fx-background-radius: 20; -fx-background-color: #3A3A3A; -fx-padding: 10; -fx-cursor: hand;");
        searchBtn.setOnMouseEntered(e -> searchBtn.setStyle("-fx-background-color: #555; -fx-background-radius: 20;"));
        searchBtn.setOnMouseExited(e -> searchBtn.setStyle("-fx-background-color: #3A3A3A; -fx-background-radius: 20;"));

        resultList.setStyle("-fx-background-color: #0d0d0d; -fx-control-inner-background: #0d0d0d; -fx-text-fill: white; -fx-background-radius: 12; -fx-border-color: black; -fx-border-width: 2; -fx-border-radius: 12; -fx-padding: 10; -fx-effect: dropshadow(two-pass-box, rgba(0,0,0,0.3), 10, 0.1, 0, 5);");
        resultList.setPrefHeight(280);
        resultList.setVisible(false);

        ScrollPane resultScroll = new ScrollPane(resultList);
        resultScroll.setFitToWidth(true);
        resultScroll.setPrefHeight(290);
        resultScroll.setStyle("-fx-background: transparent; -fx-background-color: transparent; -fx-padding: 0 50 0 50; -fx-background-insets: 0; -fx-border-color: transparent; -fx-vbar-policy: always; -fx-vbar-width: 4;");

        searchBar.setOnMouseClicked(e -> {
            ScaleTransition scale = new ScaleTransition(Duration.millis(300), searchBar);
            scale.setFromX(1);
            scale.setToX(1.5);
            scale.setFromY(1);
            scale.setToY(1.2);
            scale.setAutoReverse(true);
            scale.setCycleCount(2);
            scale.play();
        });

        Runnable searchAction = () -> {
            String keyword = searchBar.getText();
            if (keyword == null || keyword.trim().isEmpty()) {
                SearchEmployeeForm.display(manager);
            } else {
                performSearch(keyword);
            }
        };

        searchBtn.setOnAction(e -> searchAction.run());
        searchBar.setOnAction(e -> searchAction.run());

        HBox searchRow = new HBox(10, searchBar, searchBtn);
        searchRow.setAlignment(Pos.CENTER);

        VBox buttonGroup = new VBox(30);
        buttonGroup.setAlignment(Pos.CENTER);
        String[][] labels = {{"Add", "View All"}, {"Update", "CSV"}, {"Delete", "Report"}, {"Raise Pay", "Stats"}};
        String[][] icons = {{"add.png", "view-all.png"}, {"update.png", "csv.png"}, {"delete.png", "report.png"}, {"raise.png", "stats.png"}};

        for (int i = 0; i < labels.length; i++) {
            final String left = labels[i][0];
            final String right = labels[i][1];
            HBox row = new HBox(40,
                navBoxWithIcon(left, ICON_DIR + icons[i][0], () -> handleAction(left)),
                navBoxWithIcon(right, ICON_DIR + icons[i][1], () -> handleAction(right))
            );
            row.setAlignment(Pos.CENTER);
            buttonGroup.getChildren().add(row);
        }

        ScrollPane buttonScroll = new ScrollPane(buttonGroup);
        buttonScroll.setFitToWidth(true);
        buttonScroll.setStyle("-fx-background: transparent; -fx-background-color: transparent; -fx-border-color: transparent; -fx-vbar-policy: always; -fx-vbar-width: 6;");
        buttonScroll.setPrefHeight(330);

        Label footer = new Label("👥 Built by: ShaMonte Knight • Saquib Ahmed • Siyam Seid • Sabarno Dutta • Sansca Raut ");
        footer.setFont(Font.font("System", FontWeight.NORMAL, 12));
        footer.setTextFill(Color.GRAY);
        footer.setAlignment(Pos.CENTER);

        VBox layout = new VBox(30, exitBox, header, subtitle, searchRow, resultScroll, buttonScroll, footer);
        layout.setAlignment(Pos.TOP_CENTER);
        layout.setPadding(new Insets(40, 30, 40, 30));
        layout.setSpacing(20);
        layout.setStyle("-fx-background-color: linear-gradient(to bottom, #000000, #101010);");

        FadeTransition fade = new FadeTransition(Duration.millis(1200), layout);
        fade.setFromValue(0.0);
        fade.setToValue(1.0);
        fade.play();

        Scene scene = new Scene(layout, 860, 940);
        scene.addEventFilter(KeyEvent.KEY_PRESSED, e -> {
            if (e.isControlDown() && e.getCode() == KeyCode.SPACE) {
                showCommandPalette(stage);
            }
        });

        stage.setScene(scene);
        stage.show();
    }

    private void performSearch(String keyword) {
        searchResults.clear();
        keyword = "%" + keyword.trim() + "%";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "SELECT * FROM employees WHERE name LIKE ? OR ssn LIKE ? OR empid LIKE ?")) {
            stmt.setString(1, keyword);
            stmt.setString(2, keyword);
            stmt.setString(3, keyword);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String line = rs.getInt("empid") + " | " + rs.getString("name") +
                        " | " + rs.getString("job_title") + " | $" + rs.getDouble("salary");
                searchResults.add(line);
            }
        } catch (SQLException e) {
            searchResults.add("Error: " + e.getMessage());
        }
        resultList.setVisible(true);
    }

    private void handleAction(String label) {
        switch (label.toLowerCase()) {
            case "add" -> AddEmployeeForm.display(manager, () -> {
                performSearch("");
                resultList.getSelectionModel().clearSelection();
                resultList.requestFocus();
            });
            case "update" -> UpdateInfoForm.display(manager, () -> {
                performSearch("");
                resultList.getSelectionModel().clearSelection();
                resultList.requestFocus();
            });
            case "delete" -> DeleteEmployeeForm.display(manager, () -> {
                performSearch("");
                resultList.getSelectionModel().clearSelection();
                resultList.requestFocus();
            });
            case "raise pay" -> UpdateSalaryForm.display(manager);
            case "view all" -> performSearch("");
            case "csv" -> CSVExporter.export();
            case "report" -> ReportGenerator.generatePayReports(resultList);
            case "stats" -> StatsDashboard.display();
        }
    }

    private VBox navBoxWithIcon(String label, String imagePath, Runnable action) {
        ImageView icon = new ImageView(new Image(imagePath));
        icon.setFitWidth(36);
        icon.setFitHeight(36);

        Label text = new Label(label);
        text.setFont(Font.font("System", FontWeight.MEDIUM, 16));
        text.setTextFill(Color.WHITE);

        VBox box = new VBox(10, icon, text);
        box.setAlignment(Pos.CENTER);
        box.setPadding(new Insets(22));
        box.setMinWidth(220);
        box.setStyle("-fx-background-color: #1a1a1a; -fx-background-radius: 14; -fx-cursor: hand;");

        DropShadow glow = new DropShadow();
        glow.setColor(Color.web("#ffffff15"));
        glow.setRadius(12);
        box.setEffect(glow);

        box.setOnMouseEntered(e -> {
            text.setFont(Font.font("System", FontWeight.BOLD, 20));
            box.setStyle("-fx-background-color: #2a2a2a; -fx-background-radius: 14;");
        });
        box.setOnMouseExited(e -> {
            text.setFont(Font.font("System", FontWeight.MEDIUM, 16));
            box.setStyle("-fx-background-color: #1a1a1a; -fx-background-radius: 14;");
        });
        box.setOnMouseClicked(e -> action.run());
        return box;
    }

    private void showCommandPalette(Stage owner) {
        Stage modal = new Stage();
        modal.initOwner(owner);
        modal.initModality(Modality.WINDOW_MODAL);
        modal.setTitle("Quick Command Palette");

        TextField commandInput = new TextField();
        commandInput.setPromptText("Type an action (Add, Update, View All...)");
        commandInput.setStyle("-fx-background-radius: 12; -fx-background-color: #1a1a1a; -fx-text-fill: white; -fx-prompt-text-fill: #777;");

        Button execute = new Button("Execute");
        execute.setDefaultButton(true);
        execute.setOnAction(e -> {
            String cmd = commandInput.getText().trim();
            handleAction(cmd);
            modal.close();
        });

        VBox box = new VBox(15, commandInput, execute);
        box.setAlignment(Pos.CENTER);
        box.setPadding(new Insets(30));
        box.setStyle("-fx-background-color: black;");

        Scene scene = new Scene(box, 360, 150);
        modal.setScene(scene);
        modal.show();
    }
}
