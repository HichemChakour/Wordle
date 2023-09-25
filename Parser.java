package Game;

import org.jsoup.Jsoup; 
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.FileWriter;
import java.io.IOException;


public class Parser {

	public static void main(String[] args) {
		Parser dico = new Parser();
		dico.dico();
	}
	
	
	public void fileWords() {
		String url ="https://fr.wiktionary.org/wiki/Wiktionnaire:Liste_de_1750_mots_fran%C3%A7ais_les_plus_courants";
			try {
	            Document recup = Jsoup.connect(url).get();
	            Elements tags = recup.select("a");
	            String[] mots = new String[tags.size()];
	            int index = 0;
	            for (Element tag : tags) {
	                if (!tag.select("*").isEmpty()) {
	                	//System.out.println("test");
		                String mot = tag.attr("title");
		                if (mot != null && !mot.contains(" ") && !mot.contains("-")) {
		                    mots[index++] = mot;
		                }
	                }
	            }

	            for (int i = 2; i <= 12; i++) {
	                String nomFichier =  i + "_Lettres.txt";
	                FileWriter writer = new FileWriter(nomFichier);

	                for (String mot : mots) {
	                    if (mot != null && mot.length() == i) {
	                        writer.write(mot + "\n");
	                    }
	                }

	                writer.close();
	            }

	            System.out.println("File created");
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	}
	public void dico() {
		for (int code = 97; code <= 122; code++) {
			String url ="https://usito.usherbrooke.ca/index/mots/tous/"+(char)code+"#"+(char)code;
			System.out.println(url);
			try {
			    Document recup = Jsoup.connect(url).get();
		        Elements tags = recup.select("a");
		        String[] mots = new String[tags.size()];
		        int index = 0;
		        for (Element tag : tags) {
		             if (!tag.select("*").isEmpty()) {
		             	//System.out.println("test");
			                String mot = tag.text();
			                //System.out.println(mot);
			                if (mot != null && !mot.contains(" ") && !mot.contains("-")) {
			                    mots[index++] = mot;
			                }
		             }
		         }
		
		        for (int i = 2; i <= 12; i++) {
		             String nomFichier =  i + "_"+(char)code+"_Lettres.txt";
		             FileWriter writer = new FileWriter(nomFichier,true);
		
		             for (String mot : mots) {
		                 if (mot != null && mot.length() == i) {
		                     writer.write(mot + "\n");
		                 }
		             }
		
		             writer.close();
		         }
		
		         //System.out.println("File created");
		    } 
			catch (IOException e) {
	         e.printStackTrace();
			}
		}
	}
	
}