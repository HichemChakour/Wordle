package wordle;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.animation.TranslateTransition;
import javafx.util.Duration;
import java.util.function.UnaryOperator;
import javafx.scene.Node;


public class app extends Application {
    private Stage primaryStage;
    private int currentRow = 0; // Pour suivre la ligne actuelledan
    private int currentCol = 0; // Pour suivre la colonne actuelle
    private final int nbligne = 6;
    private  int nblettre =Game.gameDifficulty();
    public TextField[][] lettre ;//= new TextField[nbligne][nblettre];
    HBox[] ligne = new HBox[nbligne];
    public boolean resultat ;
    public boolean partie =true;
    Game jeu = new Game();
    String selectedWord = jeu.selectWord(nblettre);
    public boolean dernier=false;
    VBox box = new VBox(10); // Empile les éléments à l'écran avec le parent du nœud root
    VBox Lbox = new VBox(10);

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        primaryStage.setTitle("NEW WORDLE"); // Titre de l'application

        StackPane root = new StackPane();
        root.getStyleClass().add("root");
        Scene scene = new Scene(root, 800, 700);
        scene.getStylesheets().add("file:style.css");


        box.getStyleClass().add("vbox");
        //ajouter en haut a gauche un bouton pour demander un indice
        Button indice = new Button("?");
        indice.getStyleClass().add("btn");
        /*indice.setOnAction(e -> {

        });*/

        Label phrase = new Label("WORDLE");
        phrase.getStyleClass().add("label");
        box.getChildren().add(phrase);

        cases();

        box.getChildren().add(Lbox);
        VBox keyboard = createVirtualKeyboard();

        box.getChildren().add(keyboard);
        Image fond = new Image("file:FOND.jpg");
        ImageView fondView = new ImageView(fond);
        fondView.fitWidthProperty().bind(primaryStage.widthProperty());
        fondView.fitHeightProperty().bind(primaryStage.heightProperty());
        root.getChildren().addAll(box,indice);
        //positioner le bouton indice en haut a gauche
        StackPane.setAlignment(indice, Pos.TOP_LEFT);
        StackPane.setMargin(indice, new javafx.geometry.Insets(10, 10, 10, 10));


        primaryStage.setScene(scene); // Afficher la scène
        primaryStage.show(); // Afficher la fenêtre
        primaryStage.centerOnScreen(); // Mettre la fenêtre au centre
    }


    private void cases(){
        nblettre =Game.gameDifficulty();
        lettre = new TextField[nbligne][nblettre];

        for (int i = 0; i < nbligne; i++) {
            ligne[i] = new HBox(10); // Crée un HBox pour chaque ligne
            ligne[i].getStyleClass().add("hbox");
            for (int j = 0; j < nblettre; j++) {
                lettre[i][j] = new TextField(); // Crée un champ de texte
                final int row = i;
                final int col = j;

                lettre[i][j].setTextFormatter(createUppercaseTextFormatter());


                // Appliquer le style CSS aux champs de texte
                lettre[i][j].setEditable(false);
                lettre[currentRow][currentCol].setEditable(true);
                lettre[currentRow][currentCol].getStyleClass().remove("text-field");
                lettre[currentRow][currentCol].getStyleClass().add("text");
                lettre[i][j].textProperty().addListener((obs, oldText, newText) -> {
                    if (newText.length() > 1) {
                        lettre[row][col].setText(newText.substring(0, 1));
                    }
                });



                lettre[i][j].addEventHandler(KeyEvent.KEY_PRESSED, event -> {
                    if (event.getCode() == KeyCode.ENTER) {
                        if (currentCol == nblettre - 1) {
                            game();
                            if (!partie) {
                                if (resultat) {
                                    lettre[currentRow][currentCol].setEditable(false);

                                    party_gagne();




                                } else{
                                    lettre[currentRow][currentCol].setEditable(false);


                                }
                            }
                        }

                    }
                });
                lettre[i][j].addEventHandler(KeyEvent.KEY_RELEASED, event -> {
                    if (event.getCode() == KeyCode.BACK_SPACE & partie) {
                        moveFocusToPreviousTextField();
                    } else if (event.getCode().isLetterKey()) {
                        moveFocusToNextTextField();
                    }else if(event.getCode().isDigitKey()){
                        lettre[currentRow][currentCol].setText("");
                    }
                });





                ligne[i].getChildren().add(lettre[i][j]);
            }
            Lbox.getChildren().add(ligne[i]); // Ajoute la ligne à la boîte
        }

    }

    private void party_gagne() {
        Game.GAME_WON++;
        
        if (primaryStage != null) {
            primaryStage.hide();
        }
        app wordle = new app();
        try {
            wordle.start(new Stage());
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }


    private String fusionText() {
        String mot = "";
        for (int i = 0; i < nblettre; i++) {
            mot = mot + "" + lettre[currentRow][i].getText();
        }
        return mot;
    }

    private void moveFocusToNextTextField() {
        lettre[currentRow][currentCol].setEditable(false);
        lettre[currentRow][currentCol].getStyleClass().add("text-field");
        if (currentCol < nblettre -1) {
            // Avancez au champ de texte suivant dans la même ligne
            currentCol++;
            lettre[currentRow][currentCol].requestFocus();
            lettre[currentRow][currentCol].setEditable(true);
            lettre[currentRow][currentCol].getStyleClass().remove("text-field");
            lettre[currentRow][currentCol].getStyleClass().add("text");
        }else if(currentCol==nblettre -1){
            dernier=true;
        }

    }

    private void moveFocusToPreviousTextField() {
        lettre[currentRow][currentCol].setEditable(false);
        lettre[currentRow][currentCol].getStyleClass().add("text-field");
        if(dernier){
            dernier=false;
            lettre[currentRow][currentCol].getStyleClass().remove("text-field");
        } else if (currentCol > 0) {
            currentCol--;
        }
        lettre[currentRow][currentCol].requestFocus();
        lettre[currentRow][currentCol].setEditable(true);
        lettre[currentRow][currentCol].getStyleClass().remove("text-field");
        lettre[currentRow][currentCol].setText("");
        lettre[currentRow][currentCol].getStyleClass().add("text");
    }

    private void moveFocusToNextLigne() {
        if (currentRow < nbligne - 1) {
            // Passez à la ligne suivante et revenez à la première colonne
            currentRow++;
            currentCol = 0;
        }
        lettre[currentRow][currentCol].requestFocus();

    }

    private TextFormatter<String> createUppercaseTextFormatter() {

        UnaryOperator<TextFormatter.Change> filter = change -> {
            String newText = change.getControlNewText().toUpperCase();
            change.setText(newText);
            return change;
        };
        return new TextFormatter<>(filter);
    }

    //clavier numeric
    private VBox createVirtualKeyboard() {
        VBox keyboard = new VBox(5);

        HBox kligne1 = new HBox();
        HBox kligne2 = new HBox();
        HBox kligne3 = new HBox();

        String text1 = "AZERTYUIOP";
        String text2 = "QSDFGHJKLM";
        String text3 = "WXCVBN";

        for (int i = 0; i < text1.length(); i++) {
            char letter = text1.charAt(i);
            final char finalLetter = letter;
            Button button = new Button(String.valueOf(letter));
            button.getStyleClass().add("clavier-virtuel-button");
            button.setOnAction(e -> {
                lettre[currentRow][currentCol].setText(String.valueOf(finalLetter));

                
                if (lettre[currentRow][currentCol].getText().isEmpty()) {
                } else {
                    moveFocusToNextTextField();
                }
            });

            kligne1.getChildren().add(button);
        }
        for (int i = 0; i < text2.length(); i++) {
            char letter = text2.charAt(i);
            final char finalLetter = letter;
            Button button = new Button(String.valueOf(letter));
            button.getStyleClass().add("clavier-virtuel-button");
            button.setOnAction(e -> {
                lettre[currentRow][currentCol].setText(String.valueOf(finalLetter));

                if (lettre[currentRow][currentCol].getText().isEmpty()) {
                } else {
                    moveFocusToNextTextField();
                }
            });
            kligne2.getChildren().add(button);

        }
        for (int i = 0; i < text3.length(); i++) {
            char letter = text3.charAt(i);
            final char finalLetter = letter;
            Button button = new Button(String.valueOf(letter));
            button.getStyleClass().add("clavier-virtuel-button");
            button.setOnAction(e -> {
                lettre[currentRow][currentCol].setText(String.valueOf(finalLetter));

                if (lettre[currentRow][currentCol].getText().isEmpty()) {
                } else {
                    moveFocusToNextTextField();
                }
            });
            kligne3.getChildren().add(button);

        }
        kligne1.getStyleClass().add("hbox");
        kligne2.getStyleClass().add("hbox");
        kligne3.getStyleClass().add("hbox");
        keyboard.getChildren().addAll(kligne1, kligne2, kligne3);
        //keyboard.getStyleClass().add("clavier-container");
        return keyboard;
    }


    public void game() {
        System.out.println(selectedWord);
        int tmp = 0;

        String inputWord = fusionText();
        if (jeu.wordCheck(inputWord, selectedWord)) {
            lettre[currentRow][currentCol].setEditable(false);
            lettre[currentRow][currentCol].getStyleClass().add("text-field");


            String status[] = jeu.wordStatus(inputWord, selectedWord);
            for (int i = 0; i < nblettre; i++) {
                if (status[i] == "Rouge") {
                    lettre[currentRow][i].getStyleClass().remove("text-field");
                    lettre[currentRow][i].getStyleClass().remove("text");
                    lettre[currentRow][i].getStyleClass().add("text-faux");
                }
                if (status[i] == "Jaune") {
                    lettre[currentRow][i].getStyleClass().remove("text-field");
                    lettre[currentRow][i].getStyleClass().remove("text");
                    lettre[currentRow][i].getStyleClass().add("text-medium");
                }
                if (status[i] == "Vert") {
                    lettre[currentRow][i].getStyleClass().remove("text-field");
                    lettre[currentRow][i].getStyleClass().remove("text");
                    lettre[currentRow][i].getStyleClass().add("text-correct");
                    tmp++;
                }
            }
            if (tmp == nblettre) {
                resultat =true;
                partie =false;
                return;
            } else if (currentRow == nbligne) {
                resultat = false;
                partie =false;
                return;
            }
            moveFocusToNextLigne();
            lettre[currentRow][currentCol].setEditable(true);
            lettre[currentRow][currentCol].getStyleClass().remove("text-field");
            lettre[currentRow][currentCol].getStyleClass().add("text");
        }else{
            TranslateTransition vibration = new TranslateTransition(Duration.seconds(0.05), ligne[currentRow]);
            vibration.setFromY(5);
            vibration.setToY(0);
            vibration.setCycleCount(5);

            vibration.play();

        }
				
				/*win = true;
				for (int j = 0; j < inputWord.length(); j++) {// This part serves to see if the game is won
					if (status[j] == "Rouge" || status[j] == "Jaune") {
						win = false;
					}
				}
				if (win == true) {
					GAME_WON++;
					System.out.println("Vous avez gagné ! Le mot était : " + selectedWord);
					break;
				}
			
			if (win == false) {
				System.out.println("Vous avez perdu... Le mot était : " + selectedWord);
			}
			while (true) {
				System.out.print("Vous avez envie de faire une autre partie ? (y or n) : ");
				String confirm = scanner.nextLine();
				if (confirm.equals("y")) {
					game = true;
					break;
				}
				if (confirm.equals("n")) {
					game = false;
					System.out.println("Merci d'avoir joué !");
					break;
				}
			}*/

    }


    public static void main(String[] args) {
        launch(args);
    }
}


