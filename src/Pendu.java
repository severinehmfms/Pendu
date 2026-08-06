import java.util.Scanner;

/**
 * Jeu du Pendu
 * 
 * Auteur : Séverine Hori Maitrehut
 */

public class Pendu{
	
	private static Scanner scanner = new Scanner(System.in);
	
	//Mot à trouver
	private static String word_choosen;
	
	//Nombre de tentatives de l'utilisateur
	private static int nb_attempt = 0;
	
	//Lettres proposées par l'utilisateur (qui figurent ou non dans le mot)
	
	
	public static void main(String[] args){
		
		//TODO Définir la liste des mots dans un tableau (une dizaine de mots simples au départ)
		
		//TODO Choix d'un mot aléatoire dans le tableau
		word_choosen = "bateau";		//char[] motCache = {'B', 'A', 'T', 'E', 'A', 'U'}; ???? voir si je travaille avec la chaine ou un tableau de char, y réfléchir....
		
		//TODO Tant que la partie continue (l'utilisateur n'a pas atteint la 10ème tentative, ou n'a pas trouvé le mot)
		display_word();
		
		//On demande à l'utilisateur de saisir une lettre
		ask_user_letter("Proposez une lettre : ");
		
	}
	
	
	public static void display_word() {
		System.out.print("Mot mystère :  ");   //char[] affichage = {'_', '_', '_', '_', '_'}; ? même question
		//On affiche le mot à découvrir, en masquant les lettres qui ne sont pas trouvées
		for (int i=0;i<word_choosen.length();i++) {
			System.out.print(" - ");
		}
		
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