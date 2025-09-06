package nz.ac.wgtn.swen225.lc.app;

public interface Controller {
    /**
     * Handles a user action (e.g., move, pause, save, etc).
     */
    public void handleAction(String action);

    /**
     * Starts a new game at the given level.
     */
    public void startNewGame(int level);

    /**
     * Pauses the game.
     */
    public void pauseGame();

    /**
     * Resumes the game.
     */
    public void resumeGame();
}
