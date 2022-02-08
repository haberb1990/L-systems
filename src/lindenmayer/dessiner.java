/* Auteurs: Hani Berchan et Lucky Khounvongsa
 * dessiner.java
 * Description: 
 * une classe pour faire les test
 */

package lindenmayer;

import java.io.IOException;
import java.io.PrintStream;

public class dessiner {

	protected static PrintStream sortie = System.out;

	dessiner(LSystem ls, PostscriptTurtle turtle) {
		this.lsystem = ls;
		this.turtle = turtle;
	}

	protected LSystem lsystem;
	protected PostscriptTurtle turtle;

	public static void main(String[] args) throws IOException {

		Turtle tortue = new PostscriptTurtle(new StateTurtle(), sortie);
		LSystem sys = new LSystem();
		LSystem.readJSONFile(".\\test\\buisson.json", tortue, sys);
		((PostscriptTurtle) tortue).dessin(sys, 2);
		// pour faire le test: Run configurations dans common, on a choisit 
		// un output file dans le dossier sortie

	}

}
