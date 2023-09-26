package wordle;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


public class app extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("NEW WORDLE");//titre de l'app
		
		StackPane root = new StackPane();
		VBox BOX = new VBox(10);//stack les elements a l'ecran avec le parents du noeud root
		
		TextField Ligne1 = new TextField();
		Ligne1.textProperty().addListener((obs, oldText, newText) -> {
	            if (newText.length() > 1) {
	            	Ligne1.setText(newText.substring(0, 1));
	            }
	        });
		TextField Ligne2 = new TextField();
		Ligne2.textProperty().addListener((obs, oldText, newText) -> {
	            if (newText.length() > 1) {
	            	Ligne2.setText(newText.substring(0, 1));
	            }
	        });
		TextField Ligne3 = new TextField();
		Ligne3.textProperty().addListener((obs, oldText, newText) -> {
	            if (newText.length() > 1) {
	            	Ligne3.setText(newText.substring(0, 1));
	            }
	        });
		TextField Ligne4 = new TextField();
		Ligne4.textProperty().addListener((obs, oldText, newText) -> {
	            if (newText.length() > 1) {
	            	Ligne4.setText(newText.substring(0, 1));
	            }
	        });
		TextField Ligne5 = new TextField();
		Ligne5.textProperty().addListener((obs, oldText, newText) -> {
	            if (newText.length() > 1) {
	            	Ligne5.setText(newText.substring(0, 1));
	            }
	        });
		TextField Ligne6 = new TextField();
		Ligne6.textProperty().addListener((obs, oldText, newText) -> {
	            if (newText.length() > 1) {
	            	Ligne6.setText(newText.substring(0, 1));
	            }
	        });
		Image fond = new Image("file:FOND.jpg");
		ImageView fondView = new ImageView(fond);
		fondView.fitWidthProperty().bind(primaryStage.widthProperty());
		fondView.fitHeightProperty().bind(primaryStage.heightProperty());
		Label Phrase =new Label("WORDLE",Color.WHITE);
		root.getChildren().addAll(fondView,BOX);
		
		BOX.getChildren().addAll(Phrase,Ligne1,Ligne2,Ligne3,Ligne4,Ligne5,Ligne6);
		Scene scene = new Scene(root, 600, 400);//la premiere scean parent
		primaryStage.setScene(scene);//afficher la scene
		primaryStage.setMaximized(true);//ouvrir la fenetre au max
		//primaryStage.setResizable(false);//ne pas modifier la taille de fenetre
		primaryStage.show();//afficher la fenetre
		primaryStage.centerOnScreen();//mettre la fenetre au centre
		
		
		
	}

}
