package nz.ac.wgtn.swen225.lc.persistency.levelloader.parse;

import nz.ac.wgtn.swen225.lc.domain.entities.*;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public enum EntityParser {
    DoorParser("Door") {
        @Override
        public Entity parse(String entity) {
            return this.parseWithColor(entity, Door::of);
        }
    },
    KeyParser("Key") {
        @Override
        public Entity parse(String entity) {
            return this.parseWithColor(entity, Key::of);
        }
    },
    ExitLockParser("ExitLock") {
        @Override
        public Entity parse(String entity) {
            return this.parse(entity, ExitLock::of);
        }
    },
    TreasureParser("Treasure") {
        @Override
        public Entity parse(String entity) {
            return this.parse(entity, Treasure::of);
        }
    };

    private final String entityName;
    public static final String separator = "-";
    private static final Map<String, EntityParser> mapper;
    abstract Entity parse(String entity);

    static {
        mapper = Arrays
                .stream(EntityParser.values())
                .collect(Collectors.toMap(
                        EntityParser::entityName,
                        Function.identity()
                ));
    }

    EntityParser(String name) {
        entityName = name;
    }

    public static Entity parseEntity(String entity) {
        EntityParser result = mapper.get(entity.split(separator)[0]);
        if (result == null) {throw new IllegalArgumentException("No such entity: " + entity);}
        return result.parse(entity);
    }
    
    String entityName() {
        return entityName;
    }

    Entity parse(String entity, Supplier<Entity> creator) {
        if (entity.equals(entityName())) {
            return creator.get();
        }
        throw new IllegalArgumentException("Expected '"+entityName()+"' but got '"+entity+"'");
    }

    Entity parseWithColor(String entity, Function<EntityColor, Entity> creator) {
        String[] split = entity.split(separator);
        assert split.length == 2;
        if (!split[0].equals(entityName())) {
            throw new IllegalArgumentException("Expected '"+entityName()+"' but got '"+entity+"'");
        }

        return creator.apply(EntityColor.valueOf(split[1]));
    }
}
