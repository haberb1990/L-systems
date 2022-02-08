/* Auteurs: Hani Berchan et Lucky Khounvongsa
 * LSystem.java
 * Description: 
 * L-système inclut la représentation de symboles, de chaînes, et de règles, 
 * ainsi que les opérations pour dériver les chaînes, et pour les interpréter 
 * comme instructions à la tortue.
 * Description des méthodes dans AbstractLSystem.java
 */

package lindenmayer;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import lindenmayer.Symbol.Seq;


class LSystem extends AbstractLSystem{
	// Liste qui contient l'alphabet
	protected Map<Character, Symbol> symbole = new HashMap<Character, Symbol>();
	// Liste qui stock les règles
	protected Map<Symbol, List<Seq>> regles = new HashMap<Symbol, List<Seq>>();
	// Liste axiom
	protected Seq axiom;

	
	// méthode addSymbol
	public Symbol addSymbol(char sym) {
		 Symbol nouveauSymbole = new Symbol(sym);
	        symbole.put(sym, nouveauSymbole);
	        return nouveauSymbole;
	}

	// méthode addRule
	public void addRule(Symbol sym, String expansion) {
		 Seq regleReference = new SeqClass(expansion);
	     List<Seq> reglesMultiple = new ArrayList<Seq>(); 
	     	        
	        if (regles.containsKey(sym)) {
	        	 regles.get(sym).add(regleReference);
	        } else {
	            reglesMultiple.add(regleReference);
	            regles.put(sym, reglesMultiple);
	        }
	}


	// méthode setAction
	public void setAction(Symbol sym, String action) {
		symbole.get(sym.s).action = action;		
	}

	// méthode setAxiom
	@Override
	public void setAxiom(String str) {
		Seq tempListe = new SeqClass(str);
		axiom = tempListe;
	}

	// méthode getAxiom
	@Override
	public Seq getAxiom() {
		return axiom;
	}

	// méthode rewrite
	@Override
	public Seq rewrite(Symbol sym) {
		Random RNG = new Random();

		if (regles.containsKey(sym)) {
            int grandeur = regles.get(sym).size();
            int rngRegle = RNG.nextInt(grandeur);
            return regles.get(sym).get(rngRegle);
		}     else {
			Seq sym2 = new SeqClass(sym.toString());
			return sym2;
		}

		
    }

	// méthode appluRules
	@Override
	public Seq applyRules(Seq seq, int n) {
		Seq dernierAxiom = seq;
        if (n == 0) { 
        	return seq;
        } else {
            for (int i = 0; i < n; i++) {                           
            	String temporaire = "";
            	Iterator<Symbol> iter = dernierAxiom.iterator();
                while (iter.hasNext()) {                          
                    Symbol prochainSymbole = iter.next();
                    temporaire += rewrite(prochainSymbole);
 
                }            
                dernierAxiom = new SeqClass(temporaire);
               
            }
            return dernierAxiom; 
           
        }
		
	}

	// méthode tell pour le graphisme de la tortue
	@Override
	public void tell(Turtle turtle, Symbol sym) {
		        switch (symbole.get(sym.s).action) {
		            case "draw":
		                turtle.draw();
		                break;
		            case "move":
		                turtle.move();
		                break;
		            case "turnL":
		                turtle.turnL();
		                break;
		            case "turnR":
		                turtle.turnR();
		                break;
		            case "pop":
		                turtle.pop();
		                break;
		            case "push":
		                turtle.push();
		                break;
		            default: // stay
		            	turtle.stay();
		            	break;
		        }
		    
		
	}
	
	
	@Override
	public Rectangle2D tell(Turtle turtle, Seq seq, int rounds) {
		
		Iterator<Symbol> iter = applyRules(seq,rounds).iterator();
		Seq temporaire;
		Rectangle2D rectCourant = new Rectangle2D.Double(0,0,0,0); 
		if (rounds == 0) {
			while (iter.hasNext()) {
				
				Point2D PosC = turtle.getPosition();
				tell(turtle, iter.next());
				 rectCourant = getBoundingBox(turtle, PosC);
				 
			}
			return rectCourant;
			
		} else { //si rounds > 0
			while (iter.hasNext()) {
				
				Point2D PosC = turtle.getPosition();
				tell(turtle, iter.next());
				 rectCourant = getBoundingBox(turtle, PosC);
				 
				 
			}

			tell(turtle, seq, rounds - 1);	
			
		}
			
		return rectCourant;
			
	}

	// méthode pour le boundingBox
	public Rectangle2D getBoundingBox(Turtle g, Point2D pos) {

			double largeur = 0;
		    double hauteur = 0;
	        Rectangle2D boundingbox;
	        double x = g.getPosition().getX();
	        double y = g.getPosition().getY();
	        

	        hauteur = y - pos.getY();
	        largeur = x - pos.getY();
	        
	        boundingbox = new Rectangle2D.Double(x, y, largeur, hauteur); 

	        return boundingbox;
	    }
	
	
	// Json qui va lire le fichier .json et appliquer 
	 public static void readJSONFile(String filename, Turtle turtle, LSystem lsystem) throws java.io.IOException {
	        JSONObject input = new JSONObject(new JSONTokener(new java.io.FileReader(filename))); 
	        JSONArray alphabet = input.getJSONArray("alphabet");
	        JSONObject regle = input.getJSONObject("rules");
	        String axiom = input.getString("axiom");
	        JSONObject actions = input.getJSONObject("actions");
	       
	        //AXIOM
	        lsystem.setAxiom(axiom);
	        
	        //regles
	        for (int i = 0; i < alphabet.length(); i++) {
	            String lettre = alphabet.getString(i);
	            if (regle.has(lettre)) {
	                int numRegle = regle.getJSONArray(lettre).length();
	                for (int j = 0; j < numRegle; j++) {
	                    String expansion = regle.getJSONArray(lettre).getString(j);
	                    lsystem.addRule(new Symbol (lettre.charAt(0)), expansion);
	                }
	            }
	        }
	        // alphabet (symbole)
	        for (int i = 0; i < alphabet.length(); i++) {
	            String lettre = alphabet.getString(i);
	            Symbol sym = lsystem.addSymbol(lettre.charAt(0)); 
	            lsystem.setAction(sym, actions.getString(lettre));

	        }
	
	        
	        JSONObject parametre = input.getJSONObject("parameters");
	        JSONArray init_turtle = parametre.getJSONArray("start");
	        turtle.init(new Point2D.Double(init_turtle.getDouble(0), init_turtle.getDouble(1)), init_turtle.getDouble(2));
	        double unit_step = parametre.getDouble("step");
	        double unit_angle = parametre.getDouble("angle");
	        turtle.setUnits(unit_step, unit_angle);       
	    } 
	    
	
	
}
