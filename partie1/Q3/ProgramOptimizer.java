import java.io.*;
import java.util.*;

public class ProgramOptimizer {
	
	public static HashMap<String, Integer> variables;
	public static void main(String[] args) {
		if(args.length != 2){
			System.err.println("Usage : \n\t java ProgramOptimizer <inputFile> <outputFile>");
		}else{
			try {
				// Lecture du fichier d'entrée
				BufferedReader reader = new BufferedReader(new FileReader(args[0]));
				String ligne, var1 = "";
				variables = new HashMap<>(); // Dictionnaire des variables et de leurs valeurs abstraites
				while ((ligne = reader.readLine()) != null) {
					String[] parts = ligne.split(" = "); // Séparation de la ligne en mots
					String var = parts[0]; // Nom de la variable à gauche de l'égalité
					String expr = parts[1]; // Expression à droite de l'égalité
					int valeur = evalExpr(expr, variables); // Evaluation de l'expression
					variables.put(var, valeur); // Mise à jour de la valeur de la variable
					var1 = var;
				}
				int last_value = evalExpr(var1, variables);
				reader.close();

				// Ecriture du fichier de sortie
				FileWriter writer = new FileWriter(args[1]);
				
				if(last_value != 2){
					writer.write("Valeur variables à la fin :\n");
					for(String var : variables.keySet()){
						writer.write(var+" = "+variables.get(var)+"\n");
					}
					writer.write("Optimisation :\n\t");
					writer.write(var1+" = "+last_value);
				}
				else{
					writer.write("Valeur variables à la fin :\n");
					for(String var : variables.keySet()){
						writer.write(var+" = "+variables.get(var)+"\n");
					}
					writer.write("Pas d'optimisation : \n\tpour "+var1+" : valeur indéterminée");
				}

				writer.close();	

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * methode evaluant une expression et renvoyant sa valeur abstraite (0, 1 ou 2 pour valeur inconnue)
	 * @param expr
	 * @param variables
	 * @return
	 */
	public static int evalExpr(String expr, HashMap<String, Integer> variables) {

		// Si l'expression est une opération, on evalue ses opérandes et on effectue l'opération
		String[] parts = expr.split(" ");
		//System.out.println("longueur="+parts.length);
		if (parts.length == 3) {
			int op1 = evalExpr(parts[0], variables);
			int op2 = evalExpr(parts[2], variables);
			switch (parts[1]) {
				case "+":
					if (op1 == 0 || op2 == 0) {
						return 0;
					}
					if (op1 == 1 && op2 == 1) {
						return 2;
					}
					return 1;
				
				case "*":
					if(op1 == 0 || op2 == 0){
						return 0;
					}
					if(op1 == 1 && op2 == 1){
						return 1;
					}
					return 2;

				case "-":
					if(op1 == 0 && op2 == 0){
						return 0;
					}
					if(op1 == 1 && op2 == 1){
						return 0;
					}
					if(op1 == 2 || op2 == 2){
						return 2;
					}
					return 1;
				
				default:
					return 2;
			}
		}
		// Si l'expression est une variable, on renvoie sa valeur abstraite
		if (variables.containsKey(expr)) {
			return variables.get(expr);
		}

		
		// Si l'expression est un nombre trop grand (ici superieur strictement a 100), on renvoie 2
		if (Integer.parseInt(expr) > 100) {
			return 2;
		}
		// Si l'expression est un nombre égal à 0, on renvoie 0
		if (Integer.parseInt(expr) == 0) {
			return 0;
		}
		// Si l'expression est un nombre égal à 1, on renvoie 1
		if (Integer.parseInt(expr) == 1) {
			return 1;
		}

		
		return 2;
	}
}

