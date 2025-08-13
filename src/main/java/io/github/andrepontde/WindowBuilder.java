package io.github.andrepontde;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class WindowBuilder extends Application {
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("My Awesome Window");

        Pane pane = new Pane();
        pane.setPrefSize(688, 337);
        pane.setStyle("-fx-background-color: #1e1e1e;");

        Button btnAddProgram = new Button("Add");
        btnAddProgram.setLayoutX(88.04);
        btnAddProgram.setLayoutY(120.98);
        btnAddProgram.setPrefWidth(105.81);
        btnAddProgram.setPrefHeight(28.00);
        btnAddProgram.setDisable(false);
        btnAddProgram.setFont(Font.font("Arial", 14.0));
        btnAddProgram.setStyle("-fx-background-color: #2e2e2e; -fx-text-fill: #D9D9D9; -fx-border-color: #979797; -fx-border-radius: 4px; -fx-background-radius: 4px; -fx-border-width: 1px;");
        btnAddProgram.addEventFilter(MouseEvent.MOUSE_PRESSED, e -> { btnAddProgram.setBackground(new Background(new BackgroundFill(Color.web("#232323"), new CornerRadii(4.00), null))); });
        btnAddProgram.addEventFilter(MouseEvent.MOUSE_RELEASED, e -> { btnAddProgram.setBackground(new Background(new BackgroundFill(Color.web("#2e2e2e"), new CornerRadii(4.00), null))); });
        pane.getChildren().add(btnAddProgram);

        TextArea txtArea = new TextArea("");
        txtArea.setLayoutX(325.00);
        txtArea.setLayoutY(65.00);
        txtArea.setPrefWidth(244.00);
        txtArea.setPrefHeight(154.00);
        txtArea.setPromptText("");
        txtArea.setFont(Font.font("Arial", 14.0));
        txtArea.setStyle("-fx-control-inner-background: #B2B2B2; -fx-background-color: #B2B2B2; -fx-text-fill: #353535; -fx-border-color: #979797; -fx-border-width: 0px; -fx-border-radius: 2px; -fx-prompt-text-fill: #656565;");
        pane.getChildren().add(txtArea);

        TextField ifPrograms = new TextField("");
        ifPrograms.setLayoutX(89.04);
        ifPrograms.setLayoutY(94.48);
        ifPrograms.setPrefWidth(105.81);
        ifPrograms.setPrefHeight(20.67);
        ifPrograms.setPromptText("Program id");
        ifPrograms.setFont(Font.font("Arial", 14.0));
        ifPrograms.setStyle("-fx-background-color: #B2B2B2; -fx-text-fill: #353535; -fx-border-color: #979797; -fx-border-width: 0px; -fx-border-radius: 2px; -fx-prompt-text-fill: #656565;");
        pane.getChildren().add(ifPrograms);

        TextField ifTime = new TextField("");
        ifTime.setLayoutX(88.04);
        ifTime.setLayoutY(220.33);
        ifTime.setPrefWidth(105.81);
        ifTime.setPrefHeight(20.67);
        ifTime.setPromptText("Time");
        ifTime.setFont(Font.font("Arial", 14.0));
        ifTime.setStyle("-fx-background-color: #B2B2B2; -fx-text-fill: #353535; -fx-border-color: #979797; -fx-border-width: 0px; -fx-border-radius: 2px; -fx-prompt-text-fill: #656565;");
        pane.getChildren().add(ifTime);

        Button btnStart = new Button("Start Session");
        btnStart.setLayoutX(86.04);
        btnStart.setLayoutY(248.98);
        btnStart.setPrefWidth(105.81);
        btnStart.setPrefHeight(28.00);
        btnStart.setDisable(false);
        btnStart.setFont(Font.font("Arial", 14.0));
        btnStart.setStyle("-fx-background-color: #2e2e2e; -fx-text-fill: #D9D9D9; -fx-border-color: #979797; -fx-border-radius: 4px; -fx-background-radius: 4px; -fx-border-width: 1px;");
        btnStart.addEventFilter(MouseEvent.MOUSE_PRESSED, e -> { btnStart.setBackground(new Background(new BackgroundFill(Color.web("#232323"), new CornerRadii(4.00), null))); });
        btnStart.addEventFilter(MouseEvent.MOUSE_RELEASED, e -> { btnStart.setBackground(new Background(new BackgroundFill(Color.web("#2e2e2e"), new CornerRadii(4.00), null))); });
        pane.getChildren().add(btnStart);

        Button btnLoadProfile = new Button("Load Profile");
        btnLoadProfile.setLayoutX(399.00);
        btnLoadProfile.setLayoutY(269.00);
        btnLoadProfile.setPrefWidth(105.81);
        btnLoadProfile.setPrefHeight(28.00);
        btnLoadProfile.setDisable(false);
        btnLoadProfile.setFont(Font.font("Arial", 14.0));
        btnLoadProfile.setStyle("-fx-background-color: #2e2e2e; -fx-text-fill: #D9D9D9; -fx-border-color: #979797; -fx-border-radius: 4px; -fx-background-radius: 4px; -fx-border-width: 1px;");
        btnLoadProfile.addEventFilter(MouseEvent.MOUSE_PRESSED, e -> { btnLoadProfile.setBackground(new Background(new BackgroundFill(Color.web("#232323"), new CornerRadii(4.00), null))); });
        btnLoadProfile.addEventFilter(MouseEvent.MOUSE_RELEASED, e -> { btnLoadProfile.setBackground(new Background(new BackgroundFill(Color.web("#2e2e2e"), new CornerRadii(4.00), null))); });
        pane.getChildren().add(btnLoadProfile);

        Button btnCreateProfile = new Button("Create Profile");
        btnCreateProfile.setLayoutX(87.04);
        btnCreateProfile.setLayoutY(167.98);
        btnCreateProfile.setPrefWidth(105.81);
        btnCreateProfile.setPrefHeight(28.00);
        btnCreateProfile.setDisable(false);
        btnCreateProfile.setFont(Font.font("Arial", 14.0));
        btnCreateProfile.setStyle("-fx-background-color: #2e2e2e; -fx-text-fill: #D9D9D9; -fx-border-color: #979797; -fx-border-radius: 4px; -fx-background-radius: 4px; -fx-border-width: 1px;");
        btnCreateProfile.addEventFilter(MouseEvent.MOUSE_PRESSED, e -> { btnCreateProfile.setBackground(new Background(new BackgroundFill(Color.web("#232323"), new CornerRadii(4.00), null))); });
        btnCreateProfile.addEventFilter(MouseEvent.MOUSE_RELEASED, e -> { btnCreateProfile.setBackground(new Background(new BackgroundFill(Color.web("#2e2e2e"), new CornerRadii(4.00), null))); });
        pane.getChildren().add(btnCreateProfile);

        TextField ifPickProfile = new TextField("");
        ifPickProfile.setLayoutX(401.00);
        ifPickProfile.setLayoutY(241.48);
        ifPickProfile.setPrefWidth(105.81);
        ifPickProfile.setPrefHeight(20.67);
        ifPickProfile.setPromptText("ChoseProfile");
        ifPickProfile.setFont(Font.font("Arial", 14.0));
        ifPickProfile.setStyle("-fx-background-color: #B2B2B2; -fx-text-fill: #353535; -fx-border-color: #979797; -fx-border-width: 0px; -fx-border-radius: 2px; -fx-prompt-text-fill: #656565;");
        pane.getChildren().add(ifPickProfile);

        Scene scene = new Scene(pane, 688, 337);
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}