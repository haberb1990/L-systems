/* Auteurs: Hani Berchan et Lucky Khounvongsa
 * PostscriptTurtle.java
 * Description: 
 * classe qui implémente la tortue sur postscript
 */

package lindenmayer;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.PrintStream;

import lindenmayer.Symbol.Seq;

public class PostscriptTurtle implements Turtle {

	protected Turtle tortue;
	protected PrintStream sortie;

	public PostscriptTurtle(Turtle tortue, PrintStream out) {
		this.tortue = tortue;
		this.sortie = System.out;
	}

	// BoundingBox
	public void dessin(LSystem lsystem, int n) {

		Seq axiom = lsystem.getAxiom();

		Rectangle2D boundingbox = lsystem.tell(this, axiom, n);

		sortie.println("stroke");
		sortie.println("%%Trailer");
		sortie.println("%%BoundingBox: " + Integer.toString((int) boundingbox.getX()) + " "
				+ Integer.toString((int) boundingbox.getY()) + " " + Integer.toString((int) boundingbox.getWidth())
				+ " " + Integer.toString((int) boundingbox.getHeight()));
		sortie.println("%%EOF");
	}

	// Entete (debut) du fichier postscript
	public void imprimerEntete() {
		sortie.println("%!PS-Adobe-3.0 EPSF-3.0");
		sortie.println("%%Title: L system");
		sortie.println("%%Creator:" + getClass().getName());
		sortie.println("%%BoundingBox: (atend)");
		sortie.println("%%EndComments");
		sortie.println("/M {moveto} bind def");
		sortie.println("/L {lineto} bind def");
		sortie.println("0.5 setlinewidth");
	}

	private static final String formatPosition = "%.1f %.1f";

	// méthode pour la position courante
	public void printPositionCourante() {
		Point2D positionC = tortue.getPosition();
		sortie.printf(formatPosition, positionC.getX(), positionC.getY());
	}

	// ******************************************************************//
	// 				Implémentation des méthodes de tortue 	   		     //
	// ******************************************************************//

	@Override
	public void draw() {
		tortue.draw();
		printPositionCourante();
		sortie.println(" L ");
	}

	@Override
	public void move() {
		tortue.draw();
		printPositionCourante();
		sortie.println(" M ");
	}

	@Override
	public void turnR() {
		tortue.turnR();
	}

	@Override
	public void turnL() {
		tortue.turnL();
	}

	@Override
	public void push() {
		sortie.println("stroke ");
		tortue.push();
		printPositionCourante();
		sortie.println(" newpath M ");
	}

	@Override
	public void pop() {
		sortie.println("stroke ");
		tortue.pop();
		printPositionCourante();
		sortie.println(" newpath M ");
	}

	@Override
	public void stay() {
		tortue.stay();
	}

	@Override
	public void init(Point2D pos, double angle_deg) {
		imprimerEntete();
		tortue.init(pos, angle_deg);
		printPositionCourante();
		sortie.println(" newpath moveto ");
	}

	@Override
	public Point2D getPosition() {
		return tortue.getPosition();
	}

	@Override
	public double getAngle() {
		return tortue.getAngle();
	}

	@Override
	public void setUnits(double step, double delta) {
		tortue.setUnits(step, delta);
	}

}
