package Game;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

/**
 * The main game class for a word guessing game called wordle.
 * 
 * @author Chementel Ylies
 */
public class Game {

	static int GAME_WON = 0;
	static int SCORE = 0;

	public static void main(String[] args) {
		Game jeu = new Game();
		jeu.game();
	}

	/**
	 * Selects a random word of a specified number of letters from a file.
	 * 
	 * @param nbLetter The number of letters in the word to select.
	 * @return The selected word.
	 */
	public String selectWord(int nbLetter) {
		String directory = "Game_Words_files/";
		String filePath = directory + "Words_with_" + nbLetter + "_letters.txt";
		String word = null;
		String words[] = new String[2000];
		Random random = new Random();
		int index = 0;
		try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
			String line;
			while ((line = reader.readLine()) != null) {
				words[index++] = line;
			}
			int randomInt = random.nextInt(index);
			word = words[randomInt];
		} catch (IOException e) {
			e.printStackTrace();
		}
		return word;
	}

	/**
	 * Checks if an input word exists in the dictionary of words with the same
	 * length.
	 * 
	 * @param inputWord    The input word to check.
	 * @param selectedWord The selected word to compare against.
	 * @return True if the input word exists in the dictionary; otherwise, false.
	 */
	public Boolean wordCheck(String inputWord, String selectedWord) {
		if (inputWord.length() > selectedWord.length() || inputWord.length() < selectedWord.length()) {
			return false;
		}
		String filePath = "Dictionary_Words_files/" + "Words_with_" + selectedWord.length() + "_letters.txt";
		try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
			String line;
			while ((line = reader.readLine()) != null) {
				if (line.equals(inputWord)) {
					return true;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * Determines the status of each letter in the input word compared to the
	 * selected word.
	 * 
	 * @param inputWord    The input word to compare.
	 * @param selectedWord The selected word for comparison.
	 * @return An array of status values for each letter in the input word.
	 */
	public String[] wordStatus(String inputWord, String selectedWord) {
		String status[] = new String[inputWord.length()];
		for (int i = 0; i < inputWord.length(); i++) {
			if (inputWord.charAt(i) == selectedWord.charAt(i)) {
				status[i] = "Vert";// Correct
				SCORE=SCORE+10;
			} else if (selectedWord.contains(String.valueOf(inputWord.charAt(i)))) {
				status[i] = "Jaune";// Wrong Place
				SCORE=SCORE+5;
			} else {
				status[i] = "Rouge";// Incorrect
			}
			System.out.print(status[i] + " ");
		}
		System.out.println();
		return status;
	}

	/**
	 * Determines the difficulty level of the game based on the number of times the
	 * player has won.
	 * 
	 * @return The difficulty level, which is the number of letters in the selected
	 *         word.
	 */
	public static int gameDifficulty() {
		if (GAME_WON == 0) {
			return 4;// return the number of letter of the selected word
		} else if (GAME_WON == 1) {
			return 5;
		} else if (GAME_WON == 2) {
			return 6;
		} else if (GAME_WON == 3) {
			return 7;
		} else if (GAME_WON == 4) {
			return 8;
		} else {
			return 9;
		}
	}

	/**
	 * Put the best score based on the player's score in a file.
	 *
	 * @param score The score obtained by the player.
	 */
	public void BestScore(int score) {
		String filePath = "BestScores.txt";
		String bestScoreList[] = new String[11];
		int i = 0;
		try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
			String line;
			while ((line = reader.readLine()) != null) {
				bestScoreList[i]=line;
				i++;
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			Scanner scanner = new Scanner(System.in);
			System.out.print("Veuillez entrer deux lettres pour votre score : ");
			String inputWord = scanner.nextLine();
			while(inputWord.length()!=2){
				System.out.print("Veuillez entrer deux lettres pour votre score : ");
				inputWord = scanner.nextLine();
			}
			String line;
			line= inputWord +" "+ score;
			bestScoreList[i]=line;
			int j=0;
			while(j<10) {
				if(bestScoreList[j]!=null) {
					String subString1 = bestScoreList[i].substring(3).trim();
					String subString2 = bestScoreList[j].substring(3).trim();
					if(Integer.parseInt(subString1)>Integer.parseInt(subString2)) {
						String tmp=bestScoreList[j];
						bestScoreList[j]=bestScoreList[i];
						bestScoreList[i]=tmp;
					}
					j++;
				}
				else {
					break;
				}
			}
			try (FileWriter writer = new FileWriter(filePath, false)) {
				i=0;
				while(i<10) {
					if(bestScoreList[i]!=null) {
						writer.write(bestScoreList[i]+"\n");
						i++;
					}
					else {
						break;
					}
				}
				writer.close();		      
			}
			//scanner1.close();//Problem to be solved, can cause bugs
		} catch (IOException e) {
			e.printStackTrace();
		}
		SCORE=0;
	}


	/**
	 * Runs the word guessing game.
	 */
	public void game() {
		Scanner scanner = new Scanner(System.in);
		boolean win = true;
		boolean game = true;
		long startTime = System.currentTimeMillis();
		while (game == true) {
			String selectedWord = this.selectWord(Game.gameDifficulty());
			System.out.println(selectedWord);
			int i;
			for (i = 0; i < 6; i++) {// six tries to win the game
				System.out.print("Veuillez entrer un mot : ");// This part serves to enter a word
				String inputWord = scanner.nextLine();
				inputWord = inputWord.toUpperCase();
				while (this.wordCheck(inputWord, selectedWord) == false) {
					System.out.print("Ce mot n'est pas dans la liste, veuillez entrer un mot : ");
					inputWord = scanner.nextLine();
					inputWord = inputWord.toUpperCase();
				}

				String status[] = this.wordStatus(inputWord, selectedWord);
				win = true;
				for (int j = 0; j < inputWord.length(); j++) {// This part serves to see if the game is won
					if (status[j] == "Rouge" || status[j] == "Jaune") {
						win = false;
					}
				}

				if (win == true) {
					GAME_WON++;
					System.out.println("Vous avez gagné ! Le mot était : " + selectedWord);
					System.out.println("Votre série de victoire actuel : "+ GAME_WON + " partie gagné");
					break;
				}
			}

			if (win == false) {
				System.out.println("Vous avez perdu... Le mot était : " + selectedWord);
				System.out.println("Votre série de victoire s'arrête à : "+ GAME_WON + " partie gagné");
				GAME_WON=0;
			}
			long endTime = System.currentTimeMillis();
			long time = (endTime - startTime) / 1000;
			System.out.println("Le temps écoulé est de " + time + " secondes");

			if(time<30 && win==true) {// This part serves to put a score according to time
				SCORE=SCORE+600;
			}
			else if(time<120 && win==true) {
				SCORE=SCORE+300;
			}
			else if(time<180 && win==true) {
				SCORE=SCORE+150;
			}
			else if(time<240 && win==true) {
				SCORE=SCORE+75;
			}
			else if(win==true) {
				SCORE=SCORE+37;
			}

			if(i==0) {// This part serves to put a score according to the tries put 
				SCORE=SCORE+150;
			}
			else if(i==1) {
				SCORE=SCORE+125;
			}
			else if(i==2) {
				SCORE=SCORE+100;			
			}
			else if(i==3) {
				SCORE=SCORE+75;
			}
			else if(i==4) {
				SCORE=SCORE+50;
			}
			else if(i==5) {
				SCORE=SCORE+25;
			}

			System.out.println("Votre score : "+ SCORE);
			BestScore(SCORE); 
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
			}
		}
		scanner.close();
	}


}
