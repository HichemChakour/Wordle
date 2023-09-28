package Game;

import java.io.BufferedReader;
import java.io.FileReader;
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

	public static void main(String[] args) {
		Parser parser = new Parser();
		parser.gameWords();
		parser.dictionaryWords();
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
			} else if (selectedWord.contains(String.valueOf(inputWord.charAt(i)))) {
				status[i] = "Jaune";// Wrong Place
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
	public int gameDifficulty() {
		if (GAME_WON == 0) {
			return 3;// return the number of letter of the selected word
		} else if (GAME_WON == 1) {
			return 4;
		} else if (GAME_WON == 2) {
			return 5;
		} else if (GAME_WON == 3) {
			return 6;
		} else if (GAME_WON == 4) {
			return 7;
		} else {
			return 8;
		}
	}

	/**
	 * Runs the word guessing game.
	 */
	public void game() {
		Scanner scanner = new Scanner(System.in);
		boolean win = true;
		boolean game = true;
		while (game == true) {
			String selectedWord = this.selectWord(this.gameDifficulty());
			System.out.println(selectedWord);
			for (int i = 0; i < 6; i++) {// six tries to win the game
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
					break;
				}
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
			}
		}
		scanner.close();
	}

}
