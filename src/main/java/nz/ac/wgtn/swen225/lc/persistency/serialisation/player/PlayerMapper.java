package nz.ac.wgtn.swen225.lc.persistency.serialisation.player;

import nz.ac.wgtn.swen225.lc.domain.Direction;
import nz.ac.wgtn.swen225.lc.domain.Player;
import nz.ac.wgtn.swen225.lc.domain.Position;
import nz.ac.wgtn.swen225.lc.domain.entities.EntityColor;
import nz.ac.wgtn.swen225.lc.domain.entities.Key;
import nz.ac.wgtn.swen225.lc.persistency.serialisation.api.Mapper;

import java.util.List;

/**
 * Concrete Mapper which converts to/from a Player and its JSON serialisation friendly
 * counterpart PlayerState.
 * @author Thomas Ru - 300658840
 */
public class PlayerMapper implements Mapper<Player, PlayerState> {
    /**
     * Convert from a Player to PlayerState.
     * @param data - the Player instance
     * @return - the corresponding PlayerState
     */
    @Override
    public PlayerState toState(Player data) {
        Position pos = data.getPos();
        int treasures = data.getTreasuresCollected();
        int maxTreasures = data.getTotalTreasures();
        String direction = data.getDirection().name();
        List<String> keyColors = data.getKeys()
                .stream()
                .map(k->k.getColor().name())
                .toList();
        return new PlayerState(pos.getX(), pos.getY(), treasures, maxTreasures, direction, keyColors);
    }

    /**
     * Convert from deserialised PlayerState instance
     * to a proper Player instance
     * @param state - the PlayerState
     * @return - the Player
     */
    @Override
    public Player fromState(PlayerState state) {
        Player player = Player.of();
        player.setPos(new Position(state.x(), state.y()) );
        player.setDirection(Direction.valueOf(state.direction()));
        player.setTotalTreasures(state.maxTreasures());

        //update treasure count
        for (int i = 0; i < state.treasures(); i++) {
            player.collectTreasure();
        }
        state.keyColors().forEach(c->player.addKey(Key.of(EntityColor.valueOf(c))));
        return player;
    }
}
