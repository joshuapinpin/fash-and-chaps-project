package nz.ac.wgtn.swen225.lc.persistency.levelloader.parse.entity;

import nz.ac.wgtn.swen225.lc.domain.Position;
import nz.ac.wgtn.swen225.lc.domain.entities.*;
import nz.ac.wgtn.swen225.lc.persistency.levelloader.parse.tile.LevelMaker;

public enum EntityParser {
    DoorParser {
        @Override
        public Entity parse(String entity) {
            String[] split = entity.split(separator);
            assert split.length == 2;
            assert split[0].equals("Door");
            return Door.of(Color.valueOf(split[1]));
        }
    },
    KeyParser {
        @Override
        public Entity parse(String entity) {
            String[] split = entity.split(separator);
            assert split.length == 2;
            assert split[0].equals("Key");
            return Door.of(Color.valueOf(split[1]));
        }
    },
    ExitLockParser {
        @Override
        public Entity parse(String entity) {
            if (entity.equals("ExitLock")) {return ExitLock.of();}
            throw new IllegalArgumentException("Expected 'ExitLock' but got '" + entity + "'");
        }
    },
    TreasureParser {
        @Override
        public Entity parse(String entity) {
            if (entity.equals("Treasure")) {return Treasure.of();}
            throw new IllegalArgumentException("Expected 'Treasure' but got '" + entity + "'");
        }
    };

    public static final String separator = "-";
    public abstract Entity parse(String entity);
}
