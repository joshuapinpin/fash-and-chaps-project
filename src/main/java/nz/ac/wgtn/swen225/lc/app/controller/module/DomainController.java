package nz.ac.wgtn.swen225.lc.app.controller.module;

import nz.ac.wgtn.swen225.lc.app.controller.AppController;
import nz.ac.wgtn.swen225.lc.app.controller.Controller;
import nz.ac.wgtn.swen225.lc.app.state.PlayState;
import nz.ac.wgtn.swen225.lc.app.util.Input;
import nz.ac.wgtn.swen225.lc.domain.*;
import nz.ac.wgtn.swen225.lc.domain.Tile;
import nz.ac.wgtn.swen225.lc.renderer.Renderer;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * DomainController manages the domain model of the game, including the maze, player, and game entities.
 * It handles player movements, level initialization, and updates to the game state.
 *
 * @author Joshua Pinpin (Student ID: 300662880)
 */
public class DomainController implements Controller {
    AppController c;
    Maze domain;
    Player player;
    List<Key> keysList;
    Tile[][] tileGrid;
    Map<Direction, Input> directionToInput;

    /**
     * Initializes the DomainController
     * @param controller Reference to the main AppController
     */
    public DomainController(AppController controller){
        this.c = controller;
        this.directionToInput = Map.of(
                Direction.UP, Input.MOVE_UP,
                Direction.DOWN, Input.MOVE_DOWN,
                Direction.LEFT, Input.MOVE_LEFT,
                Direction.RIGHT, Input.MOVE_RIGHT
        );
        initialiseDomain(1);
    }

    /**
     * Moves the player in the specified direction
     * @param dir Direction to move the player
     */
    public void movePlayer(Direction dir){
        if(domain == null) throw new RuntimeException("Cannot move player: Domain is null.");
        c.windowController().displayInfo(false);
        if(c.state() instanceof PlayState) {
            c.recorderController().addMovement(directionToInput.get(dir));
        }
        domain.movePlayer(dir);
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
     * @param maze New maze state to set as the domain
     */
    public void updateDomain(Maze maze){
        this.domain = maze;
        initialiseDomainFields();
    }

    /**
     * Initialises the domain model for the specified level
     * @param level Level number to load
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
     * Moves the crab in level 2
     * Called by TimerController every second
     */
    public void moveMonster(){
        domain.ping();
    }

    // ========== GETTERS AND SETTERS ==========
    public Maze domain() {return domain;}
    public Player player() {return player;}
    public Tile[][] tileGrid() {return tileGrid;}
    public List<Key> keysList() {return Collections.unmodifiableList(keysList);}
    public List<Monster> monsters(){return domain.getMonsters();}
}
