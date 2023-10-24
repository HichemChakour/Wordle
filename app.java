package wordle;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.util.function.UnaryOperator;
import javafx.scene.control.Button;

public class app extends Application {

    private int currentRow = 0; // Pour suivre la ligne actuelle
    private int currentCol = 0; // Pour suivre la colonne actuelle
    private final int nbligne = 6;
    private final int nblettre = Game.gameDifficulty();
    public TextField[][] lettre = new TextField[nbligne][nblettre];
    Game jeu=new Game();
	String selectedWord = jeu.selectWord(nblettre); 
	

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
                
                lettre[i][j].setTextFormatter(createUppercaseTextFormatter());


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
                            lettre[currentRow][currentCol].setText("");
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
                    } else if(event.getCode() == javafx.scene.input.KeyCode.ENTER) {
                    	if (currentCol == nblettre -1) {
	                		game();
                    	}
                    	
                    }
                });

                ligne[i].getChildren().add(lettre[i][j]);
            }
            box.getChildren().add(ligne[i]); // Ajoute la ligne à la boîte
        }
        
        VBox keyboard = createVirtualKeyboard();

        Image fond = new Image("file:FOND.jpg");
        ImageView fondView = new ImageView(fond);
        fondView.fitWidthProperty().bind(primaryStage.widthProperty());
        fondView.fitHeightProperty().bind(primaryStage.heightProperty());
        root.getChildren().addAll( box, keyboard);

        primaryStage.setScene(scene); // Afficher la scène
        primaryStage.show(); // Afficher la fenêtre
        primaryStage.centerOnScreen(); // Mettre la fenêtre au centre
    }
    
    private String fusionText() {
    	String mot="";
    	for (int i=0;i<nblettre;i++) {
    		mot=mot+""+lettre[currentRow][i].getText();
    	}
    	return mot;
    }

    private void moveFocusToNextTextField() {
        if (currentCol < nblettre - 1) {
            // Avancez au champ de texte suivant dans la même ligne
            currentCol++;
        } 
        lettre[currentRow][currentCol].requestFocus();
    }

    private void moveFocusToPreviousTextField() {
        if (currentCol > 0) {
            // Revenez au champ de texte précédent dans la même ligne
            currentCol--;
        } 
        lettre[currentRow][currentCol].requestFocus();
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
        
        HBox kligne1=new HBox();
        HBox kligne2=new HBox();
        HBox kligne3=new HBox();

        String text1 = "AZERTYUIOP";
        String text2 = "QSDFGHJKLM";
        String text3 = "WXCVBN";
        
        for (int i = 0; i < text1.length(); i++) {
        	char letter = text1.charAt(i);
            final char finalLetter = letter; // Make it effectively final
            Button button = new Button(String.valueOf(letter));
            button.setOnAction(e -> {
                // Lorsqu'un bouton du clavier est cliqué, ajoutez la lettre à la case actuelle
                lettre[currentRow][currentCol].setText(String.valueOf(finalLetter));
                moveFocusToNextTextField();
            });
            
            kligne1.getChildren().add(button);
        }
        for (int i = 0; i < text2.length(); i++) {
        	char letter = text2.charAt(i);
            final char finalLetter = letter; // Make it effectively final
            Button button = new Button(String.valueOf(letter));
            button.setOnAction(e -> {
                // Lorsqu'un bouton du clavier est cliqué, ajoutez la lettre à la case actuelle
                lettre[currentRow][currentCol].setText(String.valueOf(finalLetter));
                moveFocusToNextTextField();
            });
            kligne2.getChildren().add(button);
            
        }
        for (int i = 0; i < text3.length(); i++) {
        	char letter = text3.charAt(i);
            final char finalLetter = letter; // Make it effectively final
            Button button = new Button(String.valueOf(letter));
            button.setOnAction(e -> {
                // Lorsqu'un bouton du clavier est cliqué, ajoutez la lettre à la case actuelle
                lettre[currentRow][currentCol].setText(String.valueOf(finalLetter));
                moveFocusToNextTextField();
            });
            kligne3.getChildren().add(button);
        }
        keyboard.getChildren().addAll(kligne1,kligne2,kligne3);
        return keyboard;
    }
    
    
    
    
    
    public void game() {
		System.out.println(selectedWord);
			
				String inputWord = fusionText();
				if (jeu.wordCheck(inputWord, selectedWord) == true) {
					lettre[currentRow][currentCol].setEditable(false);
            		lettre[currentRow][currentCol].getStyleClass().add("text-field");
				

					String status[] = jeu.wordStatus(inputWord, selectedWord);
					for(int i=0;i<nblettre;i++){
						if(status[i]=="Rouge") {
							lettre[currentRow][i].getStyleClass().remove("text-field");
			                lettre[currentRow][i].getStyleClass().remove("text");
			                lettre[currentRow][i].getStyleClass().add("text-faux");
						}
						if(status[i]=="Jaune") {
							lettre[currentRow][i].getStyleClass().remove("text-field");
			                lettre[currentRow][i].getStyleClass().remove("text");
			                lettre[currentRow][i].getStyleClass().add("text-medium");
						}
						if(status[i]=="Vert") {
							lettre[currentRow][i].getStyleClass().remove("text-field");
			                lettre[currentRow][i].getStyleClass().remove("text");
			                lettre[currentRow][i].getStyleClass().add("text-correct");
						}
					}
					moveFocusToNextLigne();
            		lettre[currentRow][currentCol].setEditable(true);
            		lettre[currentRow][currentCol].getStyleClass().remove("text-field");
            		lettre[currentRow][currentCol].getStyleClass().add("text");
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


