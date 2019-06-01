package pojo;

/**
 * Throw this when a exception is expected and handle this in global exception handler 
 * @author SouravS
 *
 */
@SuppressWarnings("serial")
public class DesiredException extends Exception {

	public DesiredException() {
		super();
	}

	public DesiredException(String message) {
		super(message);
	}

}
