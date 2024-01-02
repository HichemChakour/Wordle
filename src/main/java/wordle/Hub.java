package wordle;

import wordle.app;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class Hub extends Application{


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Hub Principal");
        primaryStage.setFullScreen(true);

       // Stage wordleStage = new Stage();

        BorderPane root = new BorderPane();


        HBox buttonBox = new HBox(200);
        buttonBox.setAlignment(Pos.CENTER);

        Label label = new Label("HUB DE JEUX");
        label.setFont(Font.font("Georgia", FontWeight.BOLD, 60));
        label.setTextFill(Color.BLACK);

        Button wordleButton = new Button();
        Button jeu2Button = new Button("Prochainement...");
        Button jeu3Button = new Button("Prochainement...");

        Image wordleImage = new Image("file:wordle.png");
        BackgroundImage backgroundImage = new BackgroundImage(
                wordleImage,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT
        );

        double imageWidth = wordleImage.getWidth();
        double imageHeight = wordleImage.getHeight();

        Background background = new Background(backgroundImage);
        wordleButton.setBackground(background);

        wordleButton.setMinSize(imageWidth, imageHeight);
        wordleButton.setMaxSize(imageWidth, imageHeight);
        jeu2Button.setMinSize(imageWidth, imageHeight);
        jeu2Button.setMaxSize(imageWidth, imageHeight);
        jeu3Button.setMinSize(imageWidth, imageHeight);
        jeu3Button.setMaxSize(imageWidth, imageHeight);

        wordleButton.setStyle("-fx-border-color: lightgray; -fx-border-width: 2px;");

        wordleButton.setTextFill(Color.LIGHTGRAY);

        DropShadow dropShadow = new DropShadow();
        wordleButton.setOnMouseEntered(e -> wordleButton.setEffect(dropShadow));
        wordleButton.setOnMouseExited(e -> wordleButton.setEffect(null));

        Font buttonFont = Font.font("Georgia", FontWeight.BOLD, 30);
        jeu2Button.setFont(buttonFont);
        jeu3Button.setFont(buttonFont);
        Font wordleFont = Font.font("Georgia", FontWeight.BOLD, 60);
        wordleButton.setFont(wordleFont);

        wordleButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
                System.out.println("Lancement de Wordle");
                primaryStage.hide();
                app wordle = new app();
                try {
                    wordle.start(new Stage());
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }

            }
        });

        jeu2Button.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
                System.out.println("Lancement du second jeu");
            }
        });

        jeu3Button.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
                System.out.println("Lancement du troisième jeu");
            }
        });

        Button quitter = new Button("Quitter");
        quitter.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
                System.out.println("Fermeture du Hub");
                primaryStage.hide();
            }
        });

        quitter.setMinSize(200, 50);
        quitter.setMaxSize(200, 50);

        Font quitterFont = Font.font("Georgia", FontWeight.BOLD, 20);
        quitter.setFont(quitterFont);

        buttonBox.getChildren().addAll(wordleButton, jeu2Button, jeu3Button);

        root.setTop(label);
        root.setCenter(buttonBox);
        root.setBottom(quitter);
        root.setPadding(new Insets(20, 0, 0, 0));

        BorderPane.setAlignment(quitter, Pos.BOTTOM_CENTER);
        BorderPane.setAlignment(label, Pos.BOTTOM_CENTER);

        BorderPane.setMargin(quitter, new Insets(30));
        BorderPane.setMargin(label, new Insets(30));
        HBox.setHgrow(quitter, Priority.ALWAYS);

        root.setStyle("-fx-background-color: #778899;");

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }



}
