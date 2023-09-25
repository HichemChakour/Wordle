package Game;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;


public class Game {

	static int NB_VICTOIRE = 0;
	
	public static void main(String[] args) {
		Parser parser = new Parser();
		parser.fileWords();
		Game jeu = new Game();
		String motOrigine=jeu.selectMot(jeu.difficulteJeu());
		System.out.println(motOrigine);
		Scanner scanner = new Scanner(System.in);
        System.out.print("Veuillez entrer un mot : ");
		String motEntrer = scanner.nextLine();
		scanner.close();
		jeu.etatMots(motEntrer,motOrigine);
		
	}
	
	public String selectMot(int nbLettre) {////1
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
	
	public Boolean verifMot(String mot, int nbLettre) {////2
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
	
	public void etatMots(String motIn, String motOrigine) {////3
		String etat[]= new String[motIn.length()];
		for (int i = 0; i < motIn.length(); i++) {
			if(motIn.charAt(i)==motOrigine.charAt(i)) {// mettre charat dans la description du commit
				etat[i]="Vert";//Correct
			}
			else if(motOrigine.contains(String.valueOf(motIn.charAt(i)))){
				etat[i]="Jaune";//Mauvaise Place
			}
			else {
				etat[i]="Rouge";//Incorrect
			}
			System.out.print(etat[i]+",");
		}
		
	}
	
	public int difficulteJeu() {///4
		if(NB_VICTOIRE==0) {
			return 3;//nombre de lettres
		}
		else if(NB_VICTOIRE==1) {
			return 4;
		}
		else if(NB_VICTOIRE==2) {
			return 5;	
		}
		else if(NB_VICTOIRE==3) {
			return 6;
		}
		else if(NB_VICTOIRE==4) {
			return 7;
		}
		else {
			return 8;
		}
	}
	
	public void jeu() {
		
		
	}

}
