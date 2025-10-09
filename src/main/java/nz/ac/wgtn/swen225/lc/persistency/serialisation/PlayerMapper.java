package nz.ac.wgtn.swen225.lc.persistency.serialisation;

import nz.ac.wgtn.swen225.lc.domain.Direction;
import nz.ac.wgtn.swen225.lc.domain.Player;
import nz.ac.wgtn.swen225.lc.domain.Position;
import nz.ac.wgtn.swen225.lc.domain.entities.EntityColor;
import nz.ac.wgtn.swen225.lc.domain.entities.Key;
import nz.ac.wgtn.swen225.lc.persistency.parse.EntityParsers;

import java.util.List;

public class PlayerMapper implements Mapper<Player, PlayerState> {
    @Override
    public PlayerState toState(Player data) {
        Position pos = data.getPos();
        int treasures = data.getTreasuresCollected();
        String direction = data.getDirection().name();
        List<String> keyColors = data.getKeys()
                .stream()
                .map(k->k.getColor().name())
                .toList();
        return new PlayerState(pos.getX(), pos.getY(), treasures, direction, keyColors);
    }

    @Override
    public Player fromState(PlayerState state) {
        Player player = Player.of();
        player.setPos(new Position(state.getX(), state.getY()) );
        player.setDirection(Direction.valueOf(state.getDirection()));
        for (int i = 0; i < state.getTreasures(); i++) {
            player.collectTreasure();
        }
        state.getKeyColors().forEach(c->player.addKey(Key.of(EntityColor.valueOf(c))));
        return player;
    }
}
