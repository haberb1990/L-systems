/* Auteurs: Hani Berchan et Lucky Khounvongsa
 * StateTurtle.java
 * Description: 
 * une classe bidon avec l�interface de tortue qui suit les �tats correctement, 
 * mais ne dessine rien en v�rit�.
 * Note: pour certaine m�thode, il y a plus d'explication dans la classe Tortue.java
 * Reference: https://docs.oracle.com/javase/7/docs/api/java/awt/geom/Point2D.html
 */
package lindenmayer;

import java.awt.geom.Point2D;
import java.util.Stack;

public class StateTurtle implements Turtle {
// Cr�ation d'une pile pour les instructions push/pop 
	protected Stack<State> pile = new Stack<State>();
// Etat active utilis�;
	protected State etatCourant;
// Longueur quand on fait le move() et draw();
	private double pas;
// Angle pour changer de direction;
	private double delta;

	// Classe interne State pour encapsuler l'�tat
	public class State {
		// position du bout de la tortue
		public Point2D positionBout;
		// angle de la tortue (degr�)
		public double angleD;

		public State(Point2D positionBout, double angle) {
			this.positionBout = positionBout;
			this.angleD = angle;
		}

	}

	/*
	 * m�thode draw() angleR: angle en radians
	 */
	public void draw() {
		// angle de degr� en radians
		double angleR = Math.toRadians(etatCourant.angleD);
		// Tracer la ligne
		etatCourant.positionBout.setLocation(etatCourant.positionBout.getX() + (pas * Math.cos(angleR)),
				etatCourant.positionBout.getY() + (pas * Math.sin(angleR)));

	}

	/*
	 * m�thode move() angleR: angle en radians
	 */
	public void move() {
		// angle de degr� en radians
		double angleR = Math.toRadians(etatCourant.angleD);
		// Deplacer la tortue
		etatCourant.positionBout.setLocation(etatCourant.positionBout.getX() + (pas * Math.cos(angleR)),
				etatCourant.positionBout.getY() + (pas * Math.sin(angleR)));

	}

	// m�thode turnR() pour tourner a droite
	public void turnR() {
		etatCourant.angleD += -delta;

	}

	// m�thode turnL() pour tourner a gauche
	public void turnL() {
		etatCourant.angleD += delta;

	}

	// m�thode push()
	public void push() {
		Point2D positionCourranteTemp = new Point2D.Double(etatCourant.positionBout.getX(),
				etatCourant.positionBout.getY());
		State etatCourantcopy = new State(positionCourranteTemp, etatCourant.angleD);
		pile.push(etatCourantcopy);

	}

	// m�thode pull()
	public void pop() {
		etatCourant = pile.pop();

	}

	// m�thode stay()
	public void stay() {
		// Rien faire
		return;
	}

	// m�thode pour initialiser l'�tat de la tortue
	public void init(Point2D pos, double angle_deg) {
		etatCourant = new State(pos, angle_deg);

	}

	// m�thode getPosition() pour trouver la position en x et y de l'�tat courant
	public Point2D getPosition() {
		return etatCourant.positionBout;
	}

	// m�thode getAngle() pour trouver l'angle de l'�tat courant
	public double getAngle() {
		return etatCourant.angleD;
	}

	// m�thode setUnits
	public void setUnits(double step, double delta) {
		this.pas = step;
		this.delta = delta;

	}

}
