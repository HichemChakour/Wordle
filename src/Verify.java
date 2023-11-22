import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.Socket;
import static java.lang.Thread.sleep;

public class Verify {
    private static final String PYTHON_FILE_PATH = "/home/iasmina/AN3/projet programmation/project/httpserver.py";

    private static final String WORD2VEC_SERVER_URL = "http://10.188.65.135:5000/top_similar_words";


    public static void main(String[] args) {
        try {
            // Build the command to run the Python file
            String pythonCommand = "python"; // Use "python" for Python 2.x
            String[] command = {pythonCommand, PYTHON_FILE_PATH};

            // Execute the Python file
            ProcessBuilder processBuilder = new ProcessBuilder(command);
            Process a = processBuilder.start();
            Thread.sleep(15000);
            Process process = processBuilder.start();



            // Read and print the output of the Python process
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

            // Wait for the Python process to complete
            int exitCode = process.waitFor();
            System.out.println("Python process exited with code: " + exitCode);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            // Handle the exception (e.g., log it or provide a default behavior)
        }

        // Replace "chien" with the word you want to find similar words for
        String wordToFindSimilarWords = "chien";

        try {
            // Print a debug message before making the HTTP request
            System.out.println("Making HTTP request to Word2Vec server...");

            // Make the HTTP request
            String similarWordsResponse = getTopSimilarWords(wordToFindSimilarWords);

            // Print a debug message after receiving the HTTP response
            System.out.println("Received HTTP response from Word2Vec server.");

            // Print the response
            System.out.println("Most Similar Words for '" + wordToFindSimilarWords + "':");
            System.out.println(similarWordsResponse);

            // Extract the top similar word from the response
            String topSimilarWord = extractTopSimilarWord(similarWordsResponse);
            System.out.println("Top Similar Word: " + topSimilarWord);
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



}
