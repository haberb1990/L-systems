package lindenmayer;

/**
 * Symbol in an L-system's alphabet.
 * 
 * @author Mikl&oacute;s Cs&#369;r&ouml;s
 */
public class Symbol {
	char s;
	String action;

	public Symbol(char s) {
		this.s = s;
	}

	@Override
	public String toString() {
		return Character.toString(s);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + s;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Symbol other = (Symbol) obj;
		if (s != other.s)
			return false;
		return true;
	}

	/**
	 * Common interface to a string of symbols.
	 * 
	 */
	public interface Seq extends Iterable<Symbol> {
	}
}
