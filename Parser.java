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
		                String mot = tag.attr("title");
		                if (mot != null && !mot.contains(" ") && !mot.contains("-")) {
		                    if (mot.contains("à") || mot.contains("á") || mot.contains("â") || mot.contains("ä")) {
		                        mot = mot.replace("à", "a").replace("á", "a").replace("â", "a").replace("ä", "a");
		                    }
		                    if (mot.contains("é") || mot.contains("è") || mot.contains("ê") || mot.contains("ë")) {
		                        mot = mot.replace("é", "e").replace("è", "e").replace("ê", "e").replace("ë", "e");
		                    }
		                    if (mot.contains("î") || mot.contains("ï")) {
		                        mot = mot.replace("î", "i").replace("ï", "i");
		                    }
		                    if (mot.contains("ô") || mot.contains("ö")) {
		                        mot = mot.replace("ô", "o").replace("ö", "o");
		                    }
		                    if (mot.contains("û") || mot.contains("ù") || mot.contains("ü")) {
		                        mot = mot.replace("û", "u").replace("ù", "u").replace("ü", "u");
		                    }
		                    if (mot.contains("ç")) {
		                        mot = mot.replace("ç", "c");
		                    }
		                    if (mot.contains("œ")) {
		                        mot = mot.replace("œ", "oe");
		                    }
		                    if (mot.contains("æ")) {
		                        mot = mot.replace("æ", "ae");
		                    }
		                    mots[index++] = mot.toUpperCase();
		                }
	                }
	            }

	            for (int i = 2; i <= 12; i++) {
	            	String dossier = "Lettres_files/";
	                String nomFichier = dossier + i + "_Lettres.txt";
	                FileWriter writer = new FileWriter(nomFichier,true);

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
		String dossier = "dico_files/";
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
			                String mot = tag.text();
			                if (mot != null && !mot.contains(" ") && !mot.contains("-")) {
			                	 if (mot.contains("à") || mot.contains("á") || mot.contains("â") || mot.contains("ä")) {
				                        mot = mot.replace("à", "a").replace("á", "a").replace("â", "a").replace("ä", "a");
			                    }
			                    if (mot.contains("é") || mot.contains("è") || mot.contains("ê") || mot.contains("ë")) {
			                        mot = mot.replace("é", "e").replace("è", "e").replace("ê", "e").replace("ë", "e");
			                    }
			                    if (mot.contains("î") || mot.contains("ï")) {
			                        mot = mot.replace("î", "i").replace("ï", "i");
			                    }
			                    if (mot.contains("ô") || mot.contains("ö")) {
			                        mot = mot.replace("ô", "o").replace("ö", "o");
			                    }
			                    if (mot.contains("û") || mot.contains("ù") || mot.contains("ü")) {
			                        mot = mot.replace("û", "u").replace("ù", "u").replace("ü", "u");
			                    }
			                    if (mot.contains("ç")) {
			                        mot = mot.replace("ç", "c");
			                    }
			                    if (mot.contains("œ")) {
			                        mot = mot.replace("œ", "oe");
			                    }
			                    if (mot.contains("æ")) {
			                        mot = mot.replace("æ", "ae");
			                    }
			                    mots[index++] = mot.toUpperCase();
			                }
		             }
		         }
		
		        for (int i = 2; i <= 12; i++) {
		             String nomFichier = dossier + i +"_Lettres.txt";
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