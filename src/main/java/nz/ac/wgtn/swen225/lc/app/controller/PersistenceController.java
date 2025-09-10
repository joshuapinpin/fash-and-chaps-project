package nz.ac.wgtn.swen225.lc.app.controller;

import nz.ac.wgtn.swen225.lc.app.util.Domain;

public class PersistenceController {
    public PersistenceController(GameController controller) {

    }

    public Domain loadLevel(int level) {
        return new Domain();
    }

    public void saveCurrentGame(Domain domain) {
    }

    public Domain loadSavedGame() {
        return null;
    }
}
