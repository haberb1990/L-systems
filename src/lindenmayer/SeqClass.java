/* Auteurs: Hani Berchan et Lucky Khounvongsa
 * SeqClass.java
 * Description: 
 *	Classe qui implémente Symbol.Seq
 */

package lindenmayer;

import java.util.ArrayList;
import java.util.Iterator;

public class SeqClass implements Symbol.Seq {

	protected ArrayList<Symbol> sy = new ArrayList<Symbol>();

	public SeqClass(String s) {

		for (int i = 0; i < s.length(); i++) {
			sy.add(new Symbol(s.charAt(i)));
		}
	}

	@Override
	public Iterator<Symbol> iterator() {
		return sy.iterator();
	}

	@Override
	public String toString() {
		String sequence = "";
		for (int i = 0; i < sy.size(); i++) {
			sequence += sy.get(i);
		}
		return sequence;
	}

}
