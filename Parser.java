package Parser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.FileWriter;
import java.io.IOException;


public class Parser {

	public static void main(String[] args) {
		String url ="https://fr.wiktionary.org/wiki/Wiktionnaire:Liste_de_1750_mots_fran%C3%A7ais_les_plus_courants";
		try {
            Document recup = Jsoup.connect(url).get();
            Elements tags = recup.select("a");
            String[] mots = new String[tags.size()];
            int index = 0;
            for (Element tag : tags) {
                if (!tag.select("*").isEmpty()) {
	                String mot = tag.attr("title");
	                if (mot != null && !mot.contains(" ") && !mot.contains("-")) {
	                    mots[index++] = mot;
	                }
                }
            }
                FileWriter writer = new FileWriter("Mots.txt");
                for (String mot : mots) {
                    if (mot != null) {
                        writer.write(mot + "\n");
                    }
                }

                writer.close();

            System.out.println("File created");
        } catch (IOException e) {
            e.printStackTrace();
        }
	}

}