package nz.ac.wgtn.swen225.lc.domain;

import java.util.function.Consumer;

/**
 * Treasure class representing a treasure entity in the game
 * Treasures can be collected by the player to decrease the treasure count
 * Implements Entity interface for interaction
 * @author Hayley Far (300659141)
 */
public class Treasure implements Entity {
    /**
     * Constructor for treasure
     */
    Treasure(){}

    /**
     * Static factory method to create a treasure
     * @return new Treasure instance
     */
    public static Treasure of(){
        return new Treasure();
    }

    /**
     * Method to handle player interaction with the treasure
     * To be able to collect the treasure
     * @param p player interacting with the treasure
     * @return Consumer to notify observers of treasure collection
     */
    @Override
    public Consumer<GameObserver> onInteract(Player p){
        if(p == null){
            throw new IllegalArgumentException("Player cannot be null");
        }
        if(p.getTreasuresCollected() == p.getTotalTreasures()){
            throw new IllegalStateException("All treasures have already been collected");
        }

        int oldSize = p.getTreasuresCollected();
        p.collectTreasure();

        assert p.getTreasuresCollected() == oldSize + 1;
        assert p.getTreasuresCollected() >= 0;

        return observer -> observer.onTreasureCollected();

    }

    /**
     * Treasures can always be collected, so they are always removable
     * @return true
     */
    @Override
    public boolean equals(Object obj) {
        if(obj == this) return true;
        if(obj == null) return false;
        if(this.getClass() != obj.getClass()) return false;
        return true; // All treasures are considered equal
    }

    /**
     * Accept method for visitor pattern
     * @param visitor the visitor to accept
     * @param <T> the return type of the visitor's operation
     * @return result of visitor's visitTreasure method
     */
    @Override
    public <T> T accept(EntityVisitor<T> visitor) {
        return visitor.visitTreasure(this);
    }
}
