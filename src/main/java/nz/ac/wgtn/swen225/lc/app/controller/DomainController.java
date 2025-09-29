package nz.ac.wgtn.swen225.lc.app.controller;

import nz.ac.wgtn.swen225.lc.domain.*;
import nz.ac.wgtn.swen225.lc.domain.entities.*;
import nz.ac.wgtn.swen225.lc.persistency.levelloader.Levels;

import java.util.Collections;
import java.util.List;

public class DomainController {
    GameController controller;
    Maze domain;
    Player player;
    List<Key> keysList;

    public DomainController(GameController controller){
        this.controller = controller;
        initialiseDomain(1);
    }

    public void initialiseDomain(int level){
        if(level == 1) this.domain = Levels.LevelOne.load();
//        else if(level == 2) this.domain = Levels.LevelTwo.load();
        else throw new IllegalArgumentException("Invalid level: " + level);

        this.player = domain.getPlayer();
        this.keysList = player.getKeys();
    }

    public void movePlayer(Direction dir){
        assert domain != null;
        domain.movePlayer(dir);
    }


    // ========== GETTERS AND SETTERS ==========
    public Maze getMaze() {return domain;}
    public Player getPlayer() {return player;}
    public List<Key> getKeysList() {return Collections.unmodifiableList(keysList);}
}
