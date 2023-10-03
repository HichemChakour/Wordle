package wordle;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


public class app extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("NEW WORDLE"); // Titre de l'application

        int nbligne = 6;
        int nblettre = 5;
        StackPane root = new StackPane();
        VBox box = new VBox(10); // Empile les éléments à l'écran avec le parent du nœud root
        HBox[] ligne = new HBox[nbligne];
        TextField[][] lettre = new TextField[nbligne][nblettre];

        for (int i = 0; i < nbligne; i++) {
            ligne[i] = new HBox(10); // Crée un HBox pour chaque ligne
            for (int j = 0; j < nblettre; j++) {
                lettre[i][j] = new TextField(); // Crée un champ de texte
                final int row = i;
                final int col = j;

                lettre[i][j].textProperty().addListener((obs, oldText, newText) -> {
                    if (newText.length() > 1) {
                        lettre[row][col].setText(newText.substring(0, 1));
                    }
                });

                ligne[i].getChildren().add(lettre[i][j]);
            }
            box.getChildren().add(ligne[i]); // Ajoute la ligne à la boîte
        }

        Image fond = new Image("file:FOND.jpg");
        ImageView fondView = new ImageView(fond);
        fondView.fitWidthProperty().bind(primaryStage.widthProperty());
        fondView.fitHeightProperty().bind(primaryStage.heightProperty());
        Label phrase = new Label("WORDLE");
        root.getChildren().addAll(fondView, box);

        Scene scene = new Scene(root, 600, 400); // La première scène parent
        primaryStage.setScene(scene); // Afficher la scène
        primaryStage.show(); // Afficher la fenêtre
        primaryStage.centerOnScreen(); // Mettre la fenêtre au centre
    }

    public static void main(String[] args) {
        launch(args);
    }
}
