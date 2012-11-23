package common.db;

public class DatabaseException extends RuntimeException {
    public DatabaseException() {
        super("No messsage given");
    }

    public DatabaseException(String message) {
        super(message);
    }

}
