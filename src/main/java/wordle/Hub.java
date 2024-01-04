package wordle;

import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.stage.StageStyle;
import javafx.util.Duration;
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


        primaryStage.initStyle(StageStyle.UNDECORATED);


        primaryStage.setTitle("Hub Principal");
        primaryStage.setMinWidth(1700);
        primaryStage.setMinHeight(800);
        //primaryStage.setFullScreen(true);

       // Stage wordleStage = new Stage();

        BorderPane root = new BorderPane();


        HBox buttonBox = new HBox(200);
        buttonBox.setAlignment(Pos.CENTER);

        Label label = new Label("G5 GAMES");
        label.setFont(Font.font("", FontWeight.BOLD, 60));
        label.setTextFill(Color.WHITESMOKE);

        Button wordleButton = new Button();
        Button jeu2Button = new Button("Prochainement...");
        Button jeu3Button = new Button("Prochainement...");

        jeu2Button.setStyle("-fx-background-radius: 10px; -fx-background-color: #000000;-fx-border-color: lightgray; -fx-border-width: 2px; -fx-border-radius: 10px;");
        jeu3Button.setStyle("-fx-background-radius: 10px; -fx-background-color: #000000;-fx-border-color: lightgray; -fx-border-width: 2px; -fx-border-radius: 10px;");



        // Initialisation des éléments non visibles
        label.setOpacity(0);
        wordleButton.setOpacity(0);
        jeu2Button.setOpacity(0);
        jeu3Button.setOpacity(0);

        // Création de la FadeTransition
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(1), label);
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1);

        FadeTransition fadeTransitionButton = new FadeTransition(Duration.seconds(1), wordleButton);
        fadeTransitionButton.setFromValue(0);
        fadeTransitionButton.setToValue(1);

        FadeTransition fadeTransitionButton2 = new FadeTransition(Duration.seconds(1), jeu2Button);
        fadeTransitionButton2.setFromValue(0);
        fadeTransitionButton2.setToValue(1);

        FadeTransition fadeTransitionButton3 = new FadeTransition(Duration.seconds(1), jeu3Button);
        fadeTransitionButton3.setFromValue(0);
        fadeTransitionButton3.setToValue(1);

        // PauseTransition pour le délai
        PauseTransition pauseTransition = new PauseTransition(Duration.seconds(0.5));
        PauseTransition pauseTransition2 = new PauseTransition(Duration.seconds(1.5));
        PauseTransition pauseTransition3 = new PauseTransition(Duration.seconds(2));
        PauseTransition pauseTransition4 = new PauseTransition(Duration.seconds(2.5));

        // Démarrer la FadeTransition après la PauseTransition

            pauseTransition.setOnFinished(event -> {
                fadeTransition.play();

            });

        pauseTransition2.setOnFinished(event -> {

            fadeTransitionButton.play();

        });

        pauseTransition3.setOnFinished(event -> {


            fadeTransitionButton2.play();

        });

        pauseTransition4.setOnFinished(event -> {


            fadeTransitionButton3.play();
        });
            pauseTransition4.play();
            pauseTransition3.play();
            pauseTransition2.play();
            pauseTransition.play();


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

        wordleButton.setStyle("-fx-border-color: lightgray; -fx-border-width: 2px;-fx-border-radius: 10px;");

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

        Button quitter = new Button();
        Image Iquiter= new Image("file:annuler.png");
        ImageView quitView = new ImageView(Iquiter);

        //quitView.setFitHeight(30);
       // quitView.setFitWidth(30);


        quitter.setGraphic(quitView);
        quitter.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
                System.out.println("Fermeture du Hub");
                primaryStage.hide();
            }
        });

        quitter.setMinSize(200, 50);
        quitter.setMaxSize(200, 50);

        //Font quitterFont = Font.font("Georgia", FontWeight.BOLD,0);
        //quitter.setFont(quitterFont);
        quitter.setStyle("-fx-background-color: lightgray;-fx-border-color: lightgray; -fx-border-width: 2px;-fx-border-radius: 50px; -fx-background-radius: 50px;");

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

        root.setStyle("-fx-background-color: #000000;");

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }



}
