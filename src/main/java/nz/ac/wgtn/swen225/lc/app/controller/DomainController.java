package nz.ac.wgtn.swen225.lc.app.controller;

import nz.ac.wgtn.swen225.lc.domain.*;
import nz.ac.wgtn.swen225.lc.domain.entities.*;

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


}
