package Game;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;


public class Game {

	public static void main(String[] args) {
		Parser parser = new Parser();
		parser.fileWords();
		Game jeu = new Game();
		System.out.println(jeu.selectMot(12));
	}
	
	public String selectMot(int nbLettre) {
		String filePath = nbLettre+"_Lettres.txt";
		String mot = null;
		String mots[]= new String[2000];
		Random random = new Random();
		int index=0;
		try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
            	mots[index++]=line;	
            }
            int randomInt = random.nextInt(index);
            mot=mots[randomInt];
        } catch (IOException e) {
            e.printStackTrace();
        }
		return mot;
	}
	
	public Boolean verifMot(String mot, int nbLettre) {
		Scanner scanner = new Scanner(System.in);
        System.out.print("Veuillez entrer un mot : ");
		String motEntrer = scanner.nextLine();
		scanner.close();
		if (motEntrer.length() > mot.length()){
			return false;
		}
		//Trouver le moyen de faire en sorte de scanner chaque mot pour voir si il est dans l'alphabet français;
		return true;
	}

}
