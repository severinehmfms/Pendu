import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;

/**
 * Jeu du Pendu
 * 
 * Auteur : Séverine Hori Maitrehut
 */

public class Pendu{
	
	private static Scanner scanner = new Scanner(System.in);
	private static Random random = new Random();
	
	//Mot à trouver, et mot qui sera construit par le joueur
	private static String mysteryWord;
	private static StringBuilder wordFound = new StringBuilder("");
	
	//Nombre de tentatives de l'utilisateur
	private static final int MAX_NB_ATTEMPT = 10;
	private static int nb_attempt = 0;
	
	//Lettres ratées proposées par l'utilisateur
	private static ArrayList<Character> lettersFailed = new ArrayList<>(); 
	
	//Couleur rouge 
	public static final String RED = "\u001B[31m";
	public static final String RESET = "\u001B[0m";
	
	private static ArrayList<String> wordsList = new ArrayList<>();
	
	//Mode debug
	private static boolean modeDebug = false;
	
	public static void main(String[] args){
		// Mots 
		wordsList.add("Bateau"); 
		wordsList.add("Avion");
		wordsList.add("Pendu");
		wordsList.add("Anticonstitutionnellement");
		wordsList.add("Commode");
		wordsList.add("Ordinateur");
		wordsList.add("Elephant");
		wordsList.add("Australie");
		wordsList.add("Kangourou");
		wordsList.add("Trotinette");
		wordsList.add("Canyoning");
		wordsList.add("Plongée");
		
		//On initialise le mot mystère, et on construit le builder pour le mot à trouver
		init_words();
		
		boolean isPlayContinue = true;
		//Tant que la partie continue (l'utilisateur n'a pas atteint la 10ème tentative, ou n'a pas trouvé le mot)
		while (isPlayContinue) {
			display_word();
			
			//On demande à l'utilisateur de saisir une lettre
			char user_letter = ask_user_letter("Proposez une lettre : ");
			
			//On va découvrir la lettre si elle fait partie du mot
			discover_letter(user_letter);
			
			//Si le joueur a dépassé le nombre de tentatives autorisées, on arrête la partie
			if (nb_attempt >= MAX_NB_ATTEMPT) {
				System.out.println("PERDU Vous avez dépassé les "+MAX_NB_ATTEMPT+" échecs");
				isPlayContinue = false;
			}
			
			//Si le joueur a trouvé le mot mystère, on arrête la partie
			if (wordFound.toString().equals(mysteryWord)) {
				System.out.println("Félicitations ! Vous avez deviné le mot : "+mysteryWord);
				isPlayContinue = false;
			}
		}		
		
	}
	
	/**
	 * Fonction qui permet de découvrir la lettre si elle appartient au mot
	 * @param letter
	 */
	public static void discover_letter(char letter) {
		//On regarde si la lettre appartient au mot recherché, si non, on incrémente le nombre de tentatives
		if (mysteryWord.indexOf(letter) == -1 ) {
			nb_attempt ++;
			lettersFailed.add(letter);
			System.out.println("Dommage ! La lettre '" + letter + "' n'est pas dans le mot. Il vous reste " + RED + (MAX_NB_ATTEMPT-nb_attempt) + RESET + " essais");
			System.out.println("Lettres râtées : "+lettersFailed);
			
		}else {
			System.out.println("Bien joué ! La lettre '" + letter +"' est dans le mot.");
			//Si oui, on parcoure le mot cherché, et pour chaque indice qui contient la lettre on remplace le tiret par la lettre dans le mot trouvé
			for (int i=0;i<mysteryWord.length();i++) {
				if (mysteryWord.charAt(i) == letter) {
					wordFound.setCharAt(i, letter);
				}
			}
		}
	}
	
	
	/**
	 * Fonction qui initialise le mot mystère
	 */
	public static void init_words() {
		//On récupère un nombre aléatoire entre 0 et le nombre de mots possibles de words_list
		int random_nb = random.nextInt(wordsList.size());
		//System.out.println("Chiffre aléatoire : "+ random_nb);
		//System.out.println("Mot qui va bien: "+ wordsList.get(random_nb));
		
		//Choix d'un mot aléatoire dans le tableau
		mysteryWord = wordsList.get(random_nb);		//char[] motCache = {'B', 'A', 'T', 'E', 'A', 'U'}; ???? voir si je travaille avec la chaine ou un tableau de char, y réfléchir....
		
		//On met en minuscules le mot mystère
		mysteryWord = mysteryWord.toLowerCase();
		
		//On enlève les accents s'il y en a du mot mystère
		mysteryWord = removeAccents(mysteryWord);
		
		//On enlève les espaces s'il y en a 
		mysteryWord = mysteryWord.replaceAll("\\s+", "");
		
		if (modeDebug)	System.out.println("DEBUG - Mot à trouver : "+ mysteryWord);
		
		//On va mettre des tirets dans le mot à trouver
		for (int i=0;i<mysteryWord.length();i++) {
			wordFound.append('-');
		}
	}
	
	/**
	 * Fonction pour enlever les accents d'une chaine (Trouvée sur le net)
	 * @param texte
	 * @return
	 */
	public static String removeAccents(String texte) {
	    String normalise = Normalizer.normalize(texte, Normalizer.Form.NFD);
	    return normalise.replaceAll("\\p{M}", "");
	}
	
	
	/**
	 * Fonction qui affiche le mot mystère en cours de résolution
	 */
	public static void display_word() {
		System.out.print("Mot mystère :  ");  
		System.out.println(wordFound.toString());
	}
	
	/**
	 * Fonction qui demande au joueur de saisir une lettre, contrôle les données et renvoie le caractère entré.
	 * @param prompt
	 * @return
	 */
	public static char ask_user_letter(String prompt) {
		boolean is_input_ok = false;
		char proposition = ' ';
		String input_user = "";
		while (!is_input_ok) {
			System.out.println("Proposez une lettre :");
			
			//On récupère la saisie (sous forme de chaine)
			input_user = scanner.nextLine();
			
			if (input_user.trim().isEmpty()) {
				System.out.println("ERREUR - La saisie ne peut pas être à vide");
				is_input_ok = false;
			}else if (input_user.length() != 1) {
	            System.out.println("ERREUR - Vous devez saisir une seule lettre");
	            is_input_ok = false;
	        }
	        else if (!Character.isLetter(input_user.charAt(0))) {
	            System.out.println("ERREUR - La saisie doit être une lettre");
	            is_input_ok = false;
	        }else {	
	        	proposition = input_user.charAt(0);
	        	//On met le caractère en minuscules
	        	proposition = Character.toLowerCase(proposition);
				is_input_ok = true;
			}
		}
		return proposition;
	}
	
	
	/** 
	 * Fonction qui permet de demander une saisie à l'utilisateur
	 * prompt = Prompt qui demande à l'utilisateur de saisir 
	 */
	public static String input_string(String prompt) {
		boolean is_input_ok = false;
		String input_user = "";
		while (!is_input_ok) {
			System.out.println(prompt);
			input_user = scanner.nextLine();
			
			if (input_user.trim().isEmpty()) {
				System.out.println("ERREUR - La saisie ne peut pas être à vide");
				is_input_ok = false;
			}else {		
				is_input_ok = true;
			}
		}
		return input_user;
	}
	
	
	
}