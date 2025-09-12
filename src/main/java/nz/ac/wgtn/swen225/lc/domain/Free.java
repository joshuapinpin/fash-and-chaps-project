package nz.ac.wgtn.swen225.lc.domain;

import java.util.Optional;

/**
 * Represents a free tile that the player can walk on
 * It may contain a collectable entity like a key, treasure, door etc
 */
public class Free extends Tile{
    //Fields
    Optional<Entity> collectable = Optional.empty();

    //Constructor
    Free(Position pos){
        super(pos);
    }

    //Checks if collectable on tile to collect
    @Override
    public void onEnter(Player p){
        throw new UnsupportedOperationException();
    }

    //Setter
    public void setCollectable(Entity collectable){
        throw new UnsupportedOperationException();
    }
    //Getter
    public Optional<Entity> getCollectable(){
        throw new UnsupportedOperationException();
    }
}
