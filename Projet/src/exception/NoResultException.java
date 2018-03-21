package exception;

@SuppressWarnings("serial")
public class NoResultException extends Exception {
	
	public NoResultException() {
		super("Aucun chemin n'a été trouvé !");
	}

}
