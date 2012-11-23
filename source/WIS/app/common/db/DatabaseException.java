package common.db;

public class DatabaseException extends RuntimeException {
   
	/**
	 * Serial
	 */
	private static final long serialVersionUID = -6213234661746926655L;

	public DatabaseException() {
        super("No messsage given");
    }

    public DatabaseException(String message) {
        super(message);
    }

}
