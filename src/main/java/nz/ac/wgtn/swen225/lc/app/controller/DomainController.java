package nz.ac.wgtn.swen225.lc.app.controller;

import nz.ac.wgtn.swen225.lc.domain.*;
import nz.ac.wgtn.swen225.lc.domain.entities.*;

import java.util.Collections;
import java.util.List;

public class DomainController {
    Maze maze;
    Player player;
    List<Key> keysList;

    public DomainController(Maze maze){
        this.maze = maze;
        this.player = maze.getPlayer();
        this.keysList = player.getKeys();
    }


    // ========== GETTERS AND SETTERS ==========
    public Maze getMaze() {return maze;}
    public Player getPlayer() {return player;}
    public List<Key> getKeysList() {return Collections.unmodifiableList(keysList);}
}
