package nz.ac.wgtn.swen225.lc.persistency.levelloader.parse;

import nz.ac.wgtn.swen225.lc.domain.entities.*;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * Represents a parsing Strategy for an Entity, i.e.
 * gives a way for each Entity type to be parsed.
 * @author Thomas Ru - 300658840
 */
public enum EntityParsers {
    /**
     * Parse a String into a Door, with its given colour.
     */
    DoorP("Door") {
        @Override
        public Entity parse(String entity) {
            return this.parseWithColor(entity, Door::of);
        }
    },
    /**
     * Parse a String into a Key, with its given colour.
     */
    KeyP("Key") {
        @Override
        public Entity parse(String entity) {
            return this.parseWithColor(entity, Key::of);
        }
    },
    /**
     * Parse a String into an ExitLock.
     */
    ExitLockP("ExitLock") {
        @Override
        public Entity parse(String entity) {
            return this.parse(entity, ExitLock::of);
        }
    },
    /**
     * Parse a String into a Treasure.
     */
    TreasureP("Treasure") {
        @Override
        public Entity parse(String entity) {
            return this.parse(entity, Treasure::of);
        }
    };

    private final String entityName;
    public static final String separator = "-";
    private static final Map<String, EntityParsers> mapper;
    abstract Entity parse(String entity);

    static { // create legend to map from each entity name to the relevant EntityParser
        mapper = Arrays
                .stream(EntityParsers.values())
                .collect(Collectors.toMap(
                        EntityParsers::entityName,
                        Function.identity()
                ));
    }

    /**
     * Constructs an EntityParser.
     * @param name - the first segment of the String representing the Entity.
     */
    EntityParsers(String name) {
        entityName = name;
    }

    /**
     * Utility method to parse any given String into an Entity, if formatted correctly.
     * @param entity - the String representation.
     * @return - the Entity instance created from the String.
     */
    public static Entity parseEntity(String entity) {
        EntityParsers result = mapper.get(entity.split(separator)[0]);
        if (result == null) {throw new IllegalArgumentException("No such entity: " + entity);}
        return result.parse(entity);
    }

    /**
     * Getter for the Entity's name.
     * @return - the name as a String.
     */
    public String entityName() {
        return entityName;
    }

    /**
     * Parses a String representation into an Entity with no colour property.
     * @param entity - the String representation.
     * @param creator - the factory for new Entities as a Supplier<Entity>.
     * @return - the Entity instance.
     */
    Entity parse(String entity, Supplier<Entity> creator) {
        if (entity.equals(entityName())) {
            return creator.get();
        }
        throw new IllegalArgumentException("Expected '"+entityName()+"' but got '"+entity+"'");
    }

    /**
     * Parses a String representation into an Entity with a colour.
     * @param entity - the String representation.
     * @param creator - the factory for new Entities as a Function<EntityColor, Entity>.
     * @return - the Entity instance.
     */
    Entity parseWithColor(String entity, Function<EntityColor, Entity> creator) {
        String[] split = entity.split(separator);

        if(split.length != 2) {
            throw new IllegalArgumentException("Expected 'Entity-COLOUR' but got '"+entity+"'");
        }

        if (!split[0].equals(entityName())) {
            throw new IllegalArgumentException("Expected '"+entityName()+"' but got '"+entity+"'");
        }

        return creator.apply(EntityColor.valueOf(split[1]));
    }
}
