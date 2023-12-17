package wordle;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.FileWriter;
import java.io.IOException;

/**
 * This class provides methods for parsing and processing words from web
 * sources.
 *
 * @author Chementel Ylies
 */

public class Parser {

    /**
     * Removes accents from a given word.
     *
     * @param word The word with accents.
     * @return The word with accents removed.
     */
    public String removeAccent(String word) {
        if (word.contains("à") || word.contains("á") || word.contains("â") || word.contains("ä")) {
            word = word.replace("à", "a").replace("á", "a").replace("â", "a").replace("ä", "a");
        }
        if (word.contains("é") || word.contains("è") || word.contains("ê") || word.contains("ë")) {
            word = word.replace("é", "e").replace("è", "e").replace("ê", "e").replace("ë", "e");
        }
        if (word.contains("î") || word.contains("ï")) {
            word = word.replace("î", "i").replace("ï", "i");
        }
        if (word.contains("ô") || word.contains("ö")) {
            word = word.replace("ô", "o").replace("ö", "o");
        }
        if (word.contains("û") || word.contains("ù") || word.contains("ü")) {
            word = word.replace("û", "u").replace("ù", "u").replace("ü", "u");
        }
        if (word.contains("ç")) {
            word = word.replace("ç", "c");
        }
        if (word.contains("œ")) {
            word = word.replace("œ", "oe");
        }
        if (word.contains("æ")) {
            word = word.replace("æ", "ae");
        }
        return word;
    }

    /**
     * Scrapes a web page to extract common French words and store them in files
     * based on word length.
     */
    public void gameWords() {
        String url = "https://fr.wiktionary.org/wiki/Wiktionnaire:Liste_de_1750_mots_fran%C3%A7ais_les_plus_courants";
        try {
            Document recovery = Jsoup.connect(url).get();
            Elements tags = recovery.select("a");
            String[] words = new String[tags.size()];
            int index = 0;
            for (Element tag : tags) {
                if (!tag.select("*").isEmpty()) {
                    String word = tag.attr("title");
                    if (word != null && !word.contains(" ") && !word.contains("-")) {
                        words[index++] = removeAccent(word).toUpperCase();
                    }
                }
            }

            for (int i = 2; i <= 12; i++) {
                String directory = "Game_Words_files/";
                String fileName = directory + "Words_with_" + i + "_letters.txt";
                FileWriter writer = new FileWriter(fileName, true);

                for (String word : words) {
                    if (word != null && word.length() == i) {
                        writer.write(word + "\n");
                    }
                }

                writer.close();
                System.out.println("Game words file created");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Scrapes a web page to extract dictionary words and store them in files based
     * on word length.
     */
    public void dictionaryWords() {
        String directory = "Dictionary_Words_files/";
        for (int code = 97; code <= 122; code++) {
            String url = "https://usito.usherbrooke.ca/index/mots/tous/" + (char) code + "#" + (char) code;
            try {
                Document recovery = Jsoup.connect(url).get();
                Elements tags = recovery.select("a");
                String[] words = new String[tags.size()];
                int index = 0;
                for (Element tag : tags) {
                    if (!tag.select("*").isEmpty()) {
                        String word = tag.text();
                        if (word != null && !word.contains(" ") && !word.contains("-")) {
                            words[index++] = removeAccent(word).toUpperCase();
                        }
                    }
                }

                for (int i = 2; i <= 12; i++) {
                    String fileName = directory + "Words_with_" + i + "_letters.txt";
                    FileWriter writer = new FileWriter(fileName, true);

                    for (String word : words) {
                        if (word != null && word.length() == i) {
                            writer.write(word + "\n");
                        }
                    }
                    writer.close();
                }
                System.out.println("Dictionary words file updated");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}