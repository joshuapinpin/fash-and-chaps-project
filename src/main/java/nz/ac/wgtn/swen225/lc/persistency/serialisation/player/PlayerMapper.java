package nz.ac.wgtn.swen225.lc.persistency.serialisation.player;

import nz.ac.wgtn.swen225.lc.domain.Direction;
import nz.ac.wgtn.swen225.lc.domain.Player;
import nz.ac.wgtn.swen225.lc.domain.Position;
import nz.ac.wgtn.swen225.lc.domain.entities.EntityColor;
import nz.ac.wgtn.swen225.lc.domain.entities.Key;
import nz.ac.wgtn.swen225.lc.persistency.serialisation.api.Mapper;

import java.util.List;

public class PlayerMapper implements Mapper<Player, PlayerState> {
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

    @Override
    public Player fromState(PlayerState state) {
        Player player = Player.of();
        player.setPos(new Position(state.x(), state.y()) );
        player.setDirection(Direction.valueOf(state.direction()));
        player.setTotalTreasures(state.maxTreasures());
        for (int i = 0; i < state.treasures(); i++) {
            player.collectTreasure();
        }
        state.keyColors().forEach(c->player.addKey(Key.of(EntityColor.valueOf(c))));
        return player;
    }
}
