package nz.ac.wgtn.swen225.lc.app.controller.module;

import nz.ac.wgtn.swen225.lc.app.controller.AppController;
import nz.ac.wgtn.swen225.lc.domain.*;
import nz.ac.wgtn.swen225.lc.domain.entities.*;
import nz.ac.wgtn.swen225.lc.domain.tiles.Tile;
import nz.ac.wgtn.swen225.lc.persistency.levelloader.Levels;
import nz.ac.wgtn.swen225.lc.renderer.Renderer;

import java.util.Collections;
import java.util.List;

public class DomainController {
    AppController c;
    Maze domain;
    Player player;
    List<Key> keysList;
    Tile[][] tileGrid;

    /**
     * Initializes the DomainController
     * @param controller Reference to the main AppController
     */
    public DomainController(AppController controller){
        this.c = controller;
        initialiseDomain(1);
    }

    /**
     * Initialises the domain model for the specified level
     * @param level
     */
    public void initialiseDomain(int level){
        // Load Level
        this.domain = c.persistencyController().loadLevel(level).load();

        // Initialise Domain Fields
        this.tileGrid = domain.getTileGrid();
        this.player = domain.getPlayer();
        this.keysList = player.getKeys();

        // Add Observers to the Domain
        domain.addObserver(Renderer.playSounds());
        domain.addObserver(new GameObserver() {
            @Override public void onInfoMessage() {
                c.windowController().displayInfo(true);
            }
            @Override public void onLevelComplete() {
                c.victory();
            }
            @Override public void onPlayerDrown(Player player){
                c.defeat();
            }
            @Override public void onPlayerDie(Player player){
                c.defeat();
            }
        });
    }

    /**
     * Moves the player in the specified direction
     * @param dir Direction to move the player
     */
    public void movePlayer(Direction dir){
        if(domain == null) throw new RuntimeException("Cannot move player: Domain is null.");
        // TODO: need a better and safer way to turn info on and off
        c.windowController().displayInfo(false);
        domain.movePlayer(dir);
    }

    /**
     * Moves the crab in level 2
     * Called by TimerController every second
     */
    public void moveCrab(){
        domain.ping();
    }


    // ========== GETTERS AND SETTERS ==========
    public Maze domain() {return domain;}
    public Player player() {return player;}
    public Tile[][] tileGrid() {return tileGrid;}
    public List<Key> keysList() {return Collections.unmodifiableList(keysList);}
}
