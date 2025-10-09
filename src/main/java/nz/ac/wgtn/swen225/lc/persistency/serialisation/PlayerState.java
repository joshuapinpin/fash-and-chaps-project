package nz.ac.wgtn.swen225.lc.persistency.serialisation;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class PlayerState {
    private int x;
    private int y;
    private int treasures;
    private int maxTreasures;
    private String direction;
    private List<String> keyColors;

    @JsonCreator
    public  PlayerState(
            @JsonProperty("x") int x,
            @JsonProperty("y") int y,
            @JsonProperty("treasures") int treasures,
            @JsonProperty("maxTreasures") int maxTreasures,
            @JsonProperty("direction") String direction,
            @JsonProperty("keyColors") List<String> keyColors
    ) {
        this.x = x;
        this.y = y;
        this.treasures = treasures;
        this.maxTreasures = maxTreasures;
        this.direction = direction;
        this.keyColors = keyColors;
    }

    public int getX() { return x; }
    public int getY() { return y; }
    public int getTreasures() { return treasures; }
    public int getMaxTreasures() { return maxTreasures; }
    public String getDirection() { return direction; }
    public List<String> getKeyColors() { return keyColors; }
}
