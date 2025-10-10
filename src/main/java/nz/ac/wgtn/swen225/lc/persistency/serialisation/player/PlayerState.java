package nz.ac.wgtn.swen225.lc.persistency.serialisation.player;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record PlayerState(
        int x,
        int y,
        int treasures,
        int maxTreasures,
        String direction,
        List<String> keyColors
) { }
