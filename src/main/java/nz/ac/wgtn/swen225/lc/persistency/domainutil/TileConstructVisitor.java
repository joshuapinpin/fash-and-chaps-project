package nz.ac.wgtn.swen225.lc.persistency.domainutil;

import nz.ac.wgtn.swen225.lc.domain.Position;
import nz.ac.wgtn.swen225.lc.domain.tiles.*;
import nz.ac.wgtn.swen225.lc.persistency.serialisation.game.GameState;

public class TileConstructVisitor implements TileVisitor { // TODO: decide on generic type
    private final GameState state;
    private final Position position;
    private final String[] parts;

    public TileConstructVisitor(GameState state, Position position, String[] parts) {
        this.state = state;
        this.position = position;
        this.parts = parts;
    }

    @Override
    public Object visitWall(Wall wall) {
        throw new UnsupportedOperationException("to do");
    }

    @Override
    public Object visitFree(Free free) {
        throw new UnsupportedOperationException("to do");
    }

    @Override
    public Object visitInfo(Info info) {
        throw new UnsupportedOperationException("to do");
    }

    @Override
    public Object visitWater(Water water) {
        throw new UnsupportedOperationException("to do");
    }

    @Override
    public Object visitExit(Exit exit) {
        throw new UnsupportedOperationException("to do");
    }
}
