package wordle;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Locale;

public class Verify {
    private static final String PYTHON_FILE_PATH = "C:\\Users\\hchak\\IdeaProjects\\wordle\\httpserver.py";

    private static final String WORD2VEC_SERVER_URL = "http://127.0.0.1:5000/top_similar_words";

    static String mot;

    public static void main(String[] args) {
        findWord();
    }

    public static void findWord() {
        try {
            mot = "wait...";
            // Build the command to run the Python file
            String pythonCommand = "C:\\Users\\hchak\\AppData\\Local\\Programs\\Python\\Python312\\python.exe"; // Use "python" for Python 2.x
            String[] command = {pythonCommand, PYTHON_FILE_PATH};

            // Execute the Python file
            ProcessBuilder processBuilder = new ProcessBuilder(command);
            Process process = processBuilder.start();

            // Read and print the output of the Python process
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                //System.out.println(line);
            }

            // Wait for the Python process to complete
            int exitCode = process.waitFor();
            //System.out.println("Python process exited with code: " + exitCode);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            // Handle the exception (e.g., log it or provide a default behavior)
        }

        // Replace "chien" with the word you want to find similar words for
        String wordToFindSimilarWords = app.selectedWord.toLowerCase();

        try {
            // Print a debug message before making the HTTP request
            //System.out.println("Making HTTP request to Word2Vec server...");

            // Make the HTTP request
            String similarWordsResponse = getTopSimilarWords(wordToFindSimilarWords);

            // Print a debug message after receiving the HTTP response
            //System.out.println("Received HTTP response from Word2Vec server.");

            // Print the response
            //System.out.println("Most Similar Words for '" + wordToFindSimilarWords + "':");
            //System.out.println(similarWordsResponse);

            // Extract the top similar word from the response
            String topSimilarWord = extractTopSimilarWord(similarWordsResponse);
            //System.out.println("Top Similar Word: " + topSimilarWord);

            int start = topSimilarWord.indexOf("[")+3 ;
            // Trouver la fin du premier élément du tableau
            int end = topSimilarWord.indexOf("\",", start);

            // Extraire la chaîne
            mot = topSimilarWord.substring(start, end);
            mot = replaceAccents(mot);

            System.out.println(mot);

        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception (e.g., log it or provide a default behavior)
        }
    }

    private static String getTopSimilarWords(String word) throws IOException {
        String url = WORD2VEC_SERVER_URL + "?word=" + word;

        // Open a connection to the URL
        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();

        try {
            // Send a GET request
            connection.setRequestMethod("GET");

            // Get the response
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder content = new StringBuilder();
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();

            return content.toString();
        } finally {
            connection.disconnect();
        }
    }

    private static String extractTopSimilarWord(String response) {
        // Assuming the response is plain text
        return response.trim(); // You might need to perform additional processing based on your server's response
    }

    private static String replaceAccents(String input) {
        return input
                .replace("\\u00c0", "A")
                .replace("\\u00c1", "A")
                .replace("\\u00c2", "A")
                .replace("\\u00c3", "A")
                .replace("\\u00c4", "A")
                .replace("\\u00c5", "A")
                .replace("\\u00c6", "AE")
                .replace("\\u00c7", "C")
                .replace("\\u00c8", "E")
                .replace("\\u00c9", "E")
                .replace("\\u00ca", "E")
                .replace("\\u00cb", "E")
                .replace("\\u00cc", "I")
                .replace("\\u00cd", "I")
                .replace("\\u00ce", "I")
                .replace("\\u00cf", "I")
                .replace("\\u00d1", "N")
                .replace("\\u00d2", "O")
                .replace("\\u00d3", "O")
                .replace("\\u00d4", "O")
                .replace("\\u00d5", "O")
                .replace("\\u00d6", "O")
                .replace("\\u00d8", "O")
                .replace("\\u00d9", "U")
                .replace("\\u00da", "U")
                .replace("\\u00db", "U")
                .replace("\\u00dc", "U")
                .replace("\\u00dd", "Y")
                .replace("\\u00df", "ss")
                .replace("\\u00e0", "a")
                .replace("\\u00e1", "a")
                .replace("\\u00e2", "a")
                .replace("\\u00e3", "a")
                .replace("\\u00e4", "a")
                .replace("\\u00e5", "a")
                .replace("\\u00e6", "ae")
                .replace("\\u00e7", "c")
                .replace("\\u00e8", "e")
                .replace("\\u00e9", "e")
                .replace("\\u00ea", "e")
                .replace("\\u00eb", "e")
                .replace("\\u00ec", "i")
                .replace("\\u00ed", "i")
                .replace("\\u00ee", "i")
                .replace("\\u00ef", "i")
                .replace("\\u00f0", "d")
                .replace("\\u00f1", "n")
                .replace("\\u00f2", "o")
                .replace("\\u00f3", "o")
                .replace("\\u00f4", "o")
                .replace("\\u00f5", "o")
                .replace("\\u00f6", "o")
                .replace("\\u00f8", "o")
                .replace("\\u00f9", "u")
                .replace("\\u00fa", "u")
                .replace("\\u00fb", "u")
                .replace("\\u00fc", "u")
                .replace("\\u00fd", "y")
                .replace("\\u00ff", "y");
    }
}
