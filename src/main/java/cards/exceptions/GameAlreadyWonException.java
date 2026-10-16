package cards.exceptions;

public class GameAlreadyWonException extends RuntimeException {
    public GameAlreadyWonException() {
        super("Game logic missed a win somewhere. Review code!!");
    }
}
