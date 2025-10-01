package nz.ac.wgtn.swen225.lc.app.controller;

import nz.ac.wgtn.swen225.lc.domain.*;
import nz.ac.wgtn.swen225.lc.domain.entities.*;
import nz.ac.wgtn.swen225.lc.domain.tiles.Tile;
import nz.ac.wgtn.swen225.lc.persistency.levelloader.Levels;

import java.util.Collections;
import java.util.List;

public class DomainController {
    GameController controller;
    Maze domain;
    Player player;
    List<Key> keysList;
    Tile[][] tileGrid;

    /**
     * Initializes the DomainController
     * @param controller Reference to the main GameController
     */
    public DomainController(GameController controller){
        this.controller = controller;
        initialiseDomain(1);
    }

    /**
     * Initialises the domain model for the specified level
     * @param level
     */
    public void initialiseDomain(int level){
        if(level == 1) this.domain = Levels.LevelOne.load();
        else if(level == 2) this.domain = Levels.LevelTwo.load();
        else throw new IllegalArgumentException("Invalid level: " + level);
        this.tileGrid = domain.getTileGrid();
        this.player = domain.getPlayer();
        this.keysList = player.getKeys();

    }

    /**
     * Moves the player in the specified direction
     * @param dir Direction to move the player
     */
    public void movePlayer(Direction dir){
        if(domain == null) throw new RuntimeException("Cannot move player: Domain is null.");
        domain.movePlayer(dir);
    }


    // ========== GETTERS AND SETTERS ==========
    public Maze domain() {return domain;}
    public Player player() {return player;}
    public Tile[][] tileGrid() {return tileGrid;}
    public List<Key> keysList() {return Collections.unmodifiableList(keysList);}
}
