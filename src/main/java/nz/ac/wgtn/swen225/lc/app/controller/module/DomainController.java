package nz.ac.wgtn.swen225.lc.app.controller.module;

import nz.ac.wgtn.swen225.lc.app.controller.AppController;
import nz.ac.wgtn.swen225.lc.app.controller.Controller;
import nz.ac.wgtn.swen225.lc.domain.*;
import nz.ac.wgtn.swen225.lc.domain.entities.*;
import nz.ac.wgtn.swen225.lc.domain.tiles.Tile;
import nz.ac.wgtn.swen225.lc.renderer.Renderer;

import java.util.Collections;
import java.util.List;

public class DomainController implements Controller {
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
     * Called when a new game starts to initialize the domain for the current level.
     */
    @Override
    public void atNewGame(){
        initialiseDomain(c.persistencyController().level());
    }

    /**
     * Updates the domain model with a new maze state
     * @param maze
     */
    public void updateDomain(Maze maze){
        System.out.println("PLAYER TREASURES: " + player.getTreasuresCollected());
        this.domain = maze;
        initialiseDomainFields();

    }

    /**
     * Initialises the domain model for the specified level
     * @param level
     */
    public void initialiseDomain(int level){
        // Load Level
        this.domain = c.persistencyController().loadLevel(level).load();
        initialiseDomainFields();

    }

    private void initialiseDomainFields(){
        // Initialise Domain Fields
        this.tileGrid = domain.getTileGrid();
        this.player = domain.getPlayer();
        this.keysList = player.getKeys();

        System.out.println("PLAYER TREASURES: " + player.getTreasuresCollected());

        player.setTotalTreasures(c.persistencyController().maxTreasures());

        // Add Observers to the Domain
        domain.addObserver(Renderer.playSounds());
        domain.addObserver(new GameObserver() {
            @Override public void onInfoMessage() {c.windowController().displayInfo(true);}
            @Override public void onLevelComplete() {c.victory();}
            @Override public void onPlayerDrown(Player player){c.defeat();}
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
    public List<Monster> monsters(){return domain.getMonsters();}
}
