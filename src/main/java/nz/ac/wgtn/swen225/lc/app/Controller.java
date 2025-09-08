package nz.ac.wgtn.swen225.lc.app;

public interface Controller {

    /**
     * Factory method to create a new Controller instance.
     */
    public static Controller of(){
        return new Controller() {
            public void handleAction(Action action) {}
            public void startNewGame(int level) {}
            public void pauseGame() {}
            public void resumeGame() {}
            public void exitGame() {}
        };
    }

    /**
     * Handles a user action (e.g., move, pause, save, etc).
     */
    public void handleAction(Action action);

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

    /**
     * Exits the current game.
     */
    public void exitGame();
}
