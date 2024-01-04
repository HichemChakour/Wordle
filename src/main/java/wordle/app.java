package wordle;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.function.UnaryOperator;
import javafx.scene.Node;


public class app extends Application {
    private Stage primaryStage;
    private int currentRow ; // Pour suivre la ligne actuelledan
    private int currentCol ; // Pour suivre la colonne actuelle
    private final int nbligne = 6; // Nombre de lignes
    private  int nblettre ; // Nombre de lettres du mot
    private TextField[][] lettre ; // Tableau de champs de texte
    private HBox[] ligne ; // Tableau de HBox
    private boolean resultat ; // Pour savoir si on a gagner ou perdu
    private boolean partie ; // Pour savoir si la partie est finie
    private Game jeu ; // Pour utiliser les méthodes de la classe Game
    public static String selectedWord ; // Le mot à trouver
    private boolean dernier; // Pour savoir si on est sur la dernière colonne
    private VBox box ; // Empile les éléments à l'écran avec le parent du nœud root
    private VBox Lbox ;
    private StackPane root ;
    long Time ;// Pour calculer le temps écoulé

    private Label timerLabel;
    private int seconds;
    private Timeline timeline;


    @Override
    public void start(Stage primaryStage) throws Exception {

        this.primaryStage = primaryStage;
        primaryStage.setTitle("NEW WORDLE"); // Titre de l'application


        primaryStage.setScene(createScene()); // Afficher la scène
        primaryStage.show(); // Afficher la fenêtre
        primaryStage.centerOnScreen(); // Mettre la fenêtre au centre
    }

    private Scene createScene() {

        Image icon = new Image("file:wordle.png"); // Remplacez avec le chemin de votre icône
        primaryStage.getIcons().add(icon);

        // Initialiser les variables de la partie
          currentRow = 0;
          currentCol = 0;
          nblettre =Game.gameDifficulty();
          ligne = new HBox[nbligne];;
          partie =true;
          jeu = new Game();
          selectedWord = jeu.selectWord(nblettre);
          dernier=false;
          box = new VBox(10);
          Lbox = new VBox(10);
          root = new StackPane();
          Time =System.currentTimeMillis();

        primaryStage.setMinWidth(1000);
        primaryStage.setMinHeight(800);


        // Créer la scène
        root.getStyleClass().add("root");
        Scene scene = new Scene(root, 1000, 800);
        scene.getStylesheets().add("file:style.css");


        box.getStyleClass().add("vbox");


        Image Iquestion = new Image("file:question.png");

        ImageView questionView = new ImageView(Iquestion);

        questionView.setFitHeight(30);
        questionView.setFitWidth(30);



        Button question = new Button("");
        question.setGraphic(questionView);
        question.getStyleClass().add("btn");
        question.setOnAction(event -> {

            StackPane root5 = new StackPane();
            StackPane root4 = new StackPane();
            root5.setMaxSize(500, 550);
            Button ok = new Button("OK");
            ok.getStyleClass().add("rejouer");
            ok.setOnAction(event1 -> {
                root.getChildren().remove(root4);
                root.getChildren().remove(root5);
            });
            Label phrase = new Label("Comment jouer : \n"+"\n"+"Chaque partie, un mot est choisi aléatoirement. \n Vous devez le deviner en 6 essais.\n" + "\n" +
                    "À chaque essai, les lettres du mot que \n vous avez proposé changeront de \n couleur en fonction d'à quel point \n vous êtes proche de le trouver. " );
            Label phrase2 = new Label("Rouge : La lettre n'est pas dans le mot\n" +
                    "Jaune : La lettre n'est pas à la bonne place\n" +
                    "Vert : La lettre est dans le mot et à la bonne place\n");
            root5.getChildren().add(phrase);


            Image exemple = new Image("file:exemple.png");

            ImageView exempleView = new ImageView(exemple);
            VBox tout = new VBox(10);
            tout.getChildren().add(phrase);
            tout.getChildren().add(exempleView);
            tout.getChildren().add(phrase2);
            tout.getChildren().add(ok);
            root5.getChildren().add(tout);
            tout.setAlignment(Pos.CENTER);
            root5.getStyleClass().add("question");
            root.getChildren().add(root4);
            root.getChildren().add(root5);


        });


        Image Iindice = new Image("file:indice.png");

        ImageView indiceView = new ImageView(Iindice);

        indiceView.setFitHeight(30);
        indiceView.setFitWidth(30);

        Button indice = new Button("");
        indice.setGraphic(indiceView);
        indice.getStyleClass().add("btn");
        indice.setOnAction(event -> {
            Game.SCORE = Game.SCORE - 150;
            StackPane root3 = new StackPane();
            root3.setMaxSize(150, 45);

            root3.getStyleClass().add("indice");
            Verify.findWord();
            Label indiceLabel = new Label(Verify.mot);//
            root3.getChildren().add(indiceLabel);
            root.getChildren().add(root3);
            StackPane.setAlignment(root3, Pos.TOP_LEFT);
            StackPane.setMargin(root3, new javafx.geometry.Insets(10, 10, 10, 10));
            root.getChildren().remove(indice);
            StackPane.setMargin(question, new javafx.geometry.Insets(10, 10, 10, 170));

        });





        Image Irecommencer = new Image("file:recomencer.png");

        ImageView recomencerView = new ImageView(Irecommencer);

        recomencerView.setFitHeight(30);
        recomencerView.setFitWidth(30);



        Button recommencer = new Button("");
        recommencer.setGraphic(recomencerView);
        recommencer.getStyleClass().add("btn");
        recommencer.setOnAction(event -> {
            timeline.stop();
            this.primaryStage.setScene(createScene());

        });



        Image Imenu = new Image("file:menu.png");
        ImageView menuView = new ImageView(Imenu);

        menuView.setFitHeight(30);
        menuView.setFitWidth(30);

        Button menu = new Button("");
        menu.setGraphic(menuView);
        menu.getStyleClass().add("btn");
        menu.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {

                primaryStage.hide();
                Hub wordle = new Hub();
                try {
                    wordle.start(new Stage());
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }

            }

        });

        //titre
        Label titre = new Label("WORDLE");
        titre.getStyleClass().add("label");
        box.getChildren().add(titre);

        //timer
        HBox timerBox = new HBox(2);
        timerBox.getStyleClass().add("hbox");
        StackPane wins = new StackPane();
        wins.getStyleClass().add("timer");
        StackPane timer = new StackPane();
        timer.getStyleClass().add("timer");
        timerLabel = new Label("00:00");
        seconds = 0;

        timeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> updateTimer()));

        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();





        Image Itimer= new Image("file:temps.png");

        ImageView timerView = new ImageView(Itimer);

        timerView.setFitHeight(30);
        timerView.setFitWidth(30);

        Image IWin= new Image("file:gagne.png");

        ImageView winView = new ImageView(IWin);

        winView.setFitHeight(30);
        winView.setFitWidth(30);


        Label gameWonLabel = new Label(String.valueOf(Game.GAME_WON));
        wins.getChildren().add(gameWonLabel);
        wins.getChildren().add(winView);
        timer.getChildren().add(timerLabel);
        timer.getChildren().add(timerView);
        timerBox.getChildren().add(timer);
        timerBox.getChildren().add(wins);
        timer.setAlignment(timerView, Pos.CENTER_LEFT);
        timer.setAlignment(timerLabel, Pos.CENTER_RIGHT);
        wins.setAlignment(winView, Pos.CENTER_LEFT);
        wins.setAlignment(gameWonLabel, Pos.CENTER_RIGHT);
        box.getChildren().add(timerBox);


        //appel de la fonction cases pour creer les cases de la grille
        cases();


        box.getChildren().add(Lbox);
        VBox keyboard = createVirtualKeyboard();

        box.getChildren().add(keyboard);
        Image fond = new Image("file:FOND.jpg");
        ImageView fondView = new ImageView(fond);
        fondView.fitWidthProperty().bind(primaryStage.widthProperty());
        fondView.fitHeightProperty().bind(primaryStage.heightProperty());

        root.getChildren().addAll(box,indice,menu,recommencer,question);
        //positioner le bouton indice en haut a gauche
        StackPane.setAlignment(question, Pos.TOP_LEFT);
        StackPane.setAlignment(recommencer, Pos.TOP_RIGHT);
        StackPane.setAlignment(menu, Pos.TOP_RIGHT);
        StackPane.setAlignment(indice, Pos.TOP_LEFT);
        StackPane.setMargin(recommencer, new javafx.geometry.Insets(10, 60, 10, 10));
        StackPane.setMargin(question, new javafx.geometry.Insets(10, 10, 10, 60));
        StackPane.setMargin(menu, new javafx.geometry.Insets(10, 10, 10, 10));
        StackPane.setMargin(indice, new javafx.geometry.Insets(10, 10, 10, 10));
        return scene;

    }
    private void updateTimer() {
        seconds++;

        int minutes = (seconds % 3600) / 60;
        int secs = seconds % 60;
        timerLabel.setText(String.format("%02d:%02d", minutes, secs));
    }

    private void cases(){

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
                                    party_perdu();


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
    private void party_perdu(){
        timeline.stop();
        Game.GAME_WON=0;
        StackPane root2 = new StackPane();
        StackPane root4 = new StackPane();
        root2.setMaxSize(400, 400);
        Button rejouer = new Button("Rejouer");
        rejouer.getStyleClass().add("rejouer");
        rejouer.setOnAction(event -> {

            this.primaryStage.setScene(createScene());

        });
        Label phrase2 = new Label("Vous avez perdu");
        VBox score = new VBox(10);
        score.getStyleClass().add("vbox");
        score.getChildren().addAll(phrase2,rejouer);


        root2.getChildren().add(score);
        root2.setAlignment(score, Pos.CENTER);


        root2.getStyleClass().add("perdu");
        root.getChildren().add(root4);
        root.getChildren().add(root2);
        StackPane.setAlignment(root2, Pos.CENTER);
        StackPane.setMargin(root2, new javafx.geometry.Insets(10, 10, 10, 10));


    }

    private void party_gagne() {
        timeline.stop();
        Game.GAME_WON++;

        //creer une petite interface pour afficher le resultat de la partie
        StackPane root2 = new StackPane();
        StackPane root4 = new StackPane();
        //la taille de root2 est plus petite que root
        root2.setMaxSize(400, 400);
        Button rejouer = new Button("Rejouer");
        rejouer.getStyleClass().add("rejouer");
        rejouer.setOnAction(event -> {

                this.primaryStage.setScene(createScene());

        });
        Label phrase2 = new Label("Votre Score est de : "+Game.SCORE);
        Game.SCORE=0;
        Label phrase3= new Label(" BEST SCORE ");

        String filePath = "BestScores.txt";
        String bestScoreList[] = new String[3];
        int i = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while (i<3) {
                line = reader.readLine();
                bestScoreList[i]=line;
                i++;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        Label score1 = new Label(bestScoreList[0]);
        Label score2 = new Label(bestScoreList[1]);
        Label score3 = new Label(bestScoreList[2]);
        VBox score = new VBox(10);
        score.getStyleClass().add("vbox");
        score.getChildren().addAll(phrase2,phrase3,score1,score2,score3, rejouer);


        root2.getChildren().add(score);
        root2.setAlignment(score, Pos.CENTER);


        root2.getStyleClass().add("gagner");

        root.getChildren().add(root4);
        root.getChildren().add(root2);
        StackPane.setAlignment(root2, Pos.CENTER);
        StackPane.setMargin(root2, new javafx.geometry.Insets(10, 10, 10, 10));

    }


    private String fusionText() {
        String mot = "";
        for (int i = 0; i < nblettre; i++) {
            mot = mot + "" + lettre[currentRow][i].getText();
        }
        return mot;
    }

    private void moveFocusToNextTextField() {
        if(lettre[currentRow][currentCol].getText().isEmpty()){}
        else {

            lettre[currentRow][currentCol].setEditable(false);
            lettre[currentRow][currentCol].getStyleClass().add("text-field");
            if (currentCol < nblettre-1 ) {
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

    }

    private void moveFocusToPreviousTextField() {

        lettre[currentRow][currentCol].setEditable(false);
        lettre[currentRow][currentCol].getStyleClass().add("text-field");
        if(dernier){
            dernier=false;

            lettre[currentRow][currentCol].getStyleClass().remove("text-field");
            lettre[currentRow][currentCol].getStyleClass().add("text");

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

        Image IEnter = new Image("file:entrer.png");

        ImageView enterView = new ImageView(IEnter);

        enterView.setFitHeight(50);
        enterView.setFitWidth(50);





        Button buttonEnter = new Button("");
        buttonEnter.setGraphic(enterView);
        buttonEnter.getStyleClass().add("clavier-virtuel-button2");
        buttonEnter.setOnAction(e -> {
            if (currentCol == nblettre - 1) {
                game();
                if (!partie) {
                    if (resultat) {
                        lettre[currentRow][currentCol].setEditable(false);

                        party_gagne();




                    } else{

                        lettre[currentRow][currentCol].setEditable(false);
                        party_perdu();


                    }
                }
            }

        });
        kligne3.getChildren().add(buttonEnter);



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

        Image ISupp = new Image("file:supprimer.png");

        ImageView suppView = new ImageView(ISupp);

        suppView.setFitHeight(50);
        suppView.setFitWidth(50);


        Button buttonSupp = new Button();
        buttonSupp.setGraphic(suppView);
        buttonSupp.getStyleClass().add("clavier-virtuel-button2");
        buttonSupp.setOnAction(e -> {
            lettre[currentRow][currentCol].setText("");

            moveFocusToPreviousTextField();

        });
        kligne3.getChildren().add(buttonSupp);

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

                long endTime = System.currentTimeMillis();
                long finaltime = (endTime - Time) / 1000;
                System.out.println("Le temps écoulé est de " + finaltime + " secondes");

                if(finaltime<30 ) {
                    Game.SCORE=Game.SCORE+600;
                }
                else if(finaltime<120 ) {
                    Game.SCORE=Game.SCORE+300;
                }
                else if(finaltime<180) {
                    Game.SCORE=Game.SCORE+150;
                }
                else if(finaltime<240) {
                    Game.SCORE=Game.SCORE+75;
                }
                else {
                    Game.SCORE=Game.SCORE+37;
                }
                Game.SCORE = Game.SCORE + 150;
                Game.BestScore(Game.SCORE);
                resultat =true;
                partie =false;
                return;
            } else if (currentRow == nbligne-1) {
                resultat = false;
                partie =false;
                return;
            }
            Game.SCORE = Game.SCORE - 25;
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

    }




    public static void main(String[] args) {
        launch(args);
    }
}


