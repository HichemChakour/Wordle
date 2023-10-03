package wordle;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class app extends Application {

    private int currentRow = 0; // Pour suivre la ligne actuelle
    private int currentCol = 0; // Pour suivre la colonne actuelle
    private final int nbligne = 6;
    private final int nblettre = 5;
    private TextField[][] lettre = new TextField[nbligne][nblettre];

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("NEW WORDLE"); // Titre de l'application

        StackPane root = new StackPane();
        root.getStyleClass().add("root");
        Scene scene = new Scene(root, 600, 400);
        scene.getStylesheets().add("file:style.css");


        VBox box = new VBox(10); // Empile les éléments à l'écran avec le parent du nœud root
        box.getStyleClass().add("vbox");
        Label phrase = new Label("WORDLE");
        phrase.getStyleClass().add("label");
        box.getChildren().add(phrase);
        HBox[] ligne = new HBox[nbligne];

        for (int i = 0; i < nbligne; i++) {
            ligne[i] = new HBox(10); // Crée un HBox pour chaque ligne
            ligne[i].getStyleClass().add("hbox");
            for (int j = 0; j < nblettre; j++) {
                lettre[i][j] = new TextField(); // Crée un champ de texte
                final int row = i;
                final int col = j;


                // Appliquer le style CSS aux champs de texte
                lettre[i][j].setEditable(false);
                //lettre[i][j].getStyleClass().add("text-field-unused");
                lettre[currentRow][currentCol].setEditable(true);
                lettre[currentRow][currentCol].getStyleClass().remove("text-field");
                lettre[currentRow][currentCol].getStyleClass().add("text");
                lettre[i][j].textProperty().addListener((obs, oldText, newText) -> {
                    if (newText.length() > 1) {
                        lettre[row][col].setText(newText.substring(0, 1));
                    }
                });
                lettre[i][j].addEventHandler(KeyEvent.KEY_PRESSED, event -> {
                    if (event.getCode() == javafx.scene.input.KeyCode.BACK_SPACE) {
                        // Si l'utilisateur a appuyé sur la touche de suppression
                        if (lettre[currentRow][currentCol].getText().isEmpty()) {
                            // Si le champ de texte actuel est vide, revenez au champ de texte précédent
                        	lettre[currentRow][currentCol].setEditable(false);
                        	lettre[currentRow][currentCol].getStyleClass().add("text-field");
                            moveFocusToPreviousTextField();
                            lettre[currentRow][currentCol].setEditable(true);
                            lettre[currentRow][currentCol].getStyleClass().remove("text-field");
                            lettre[currentRow][currentCol].getStyleClass().add("text");
                        }
                    } else if (event.getCode().isLetterKey()) {
                    	if (lettre[currentRow][currentCol].getText().isEmpty()) {}
                    	else {
                            // Si le champ de texte actuel n'est pas vide, avancez au champ de texte suivant
                    		lettre[currentRow][currentCol].setEditable(false);
                    		lettre[currentRow][currentCol].getStyleClass().add("text-field");
                    		moveFocusToNextTextField();
                    		lettre[currentRow][currentCol].setEditable(true);
                    		lettre[currentRow][currentCol].getStyleClass().remove("text-field");
                    		lettre[currentRow][currentCol].getStyleClass().add("text");
                        } 
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
        root.getChildren().addAll( box);

        primaryStage.setScene(scene); // Afficher la scène
        primaryStage.show(); // Afficher la fenêtre
        primaryStage.centerOnScreen(); // Mettre la fenêtre au centre
    }

    private void moveFocusToNextTextField() {
        if (currentCol < nblettre - 1) {
            // Avancez au champ de texte suivant dans la même ligne
            currentCol++;
        } else if (currentRow < nbligne - 1) {
            // Passez à la ligne suivante et revenez à la première colonne
            currentRow++;
            currentCol = 0;
        }
        lettre[currentRow][currentCol].requestFocus();
    }

    private void moveFocusToPreviousTextField() {
        if (currentCol > 0) {
            // Revenez au champ de texte précédent dans la même ligne
            currentCol--;
        } else if (currentRow > 0) {
            // Revenez à la ligne précédente et passez à la dernière colonne
            currentRow--;
            currentCol = nblettre - 1;
        }
        lettre[currentRow][currentCol].requestFocus();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
