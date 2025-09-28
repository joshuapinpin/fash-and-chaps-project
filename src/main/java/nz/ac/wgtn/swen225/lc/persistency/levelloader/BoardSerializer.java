package nz.ac.wgtn.swen225.lc.persistency.levelloader;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

/**
 * Custom serialiser to convert a 2D String array into JSON, with each row
 * formatted on a different line for readability.
 */
class BoardSerializer extends JsonSerializer<String[][]> {
    /**
     * Given a JsonGenerator
     * @param value - the String[][] array to serialise
     * @param gen - the JsonGenerator JSON writer
     * @param serializers - the SerializerProvider to serialise any context objects (unused)
     * @throws IOException
     */
    @Override
    public void serialize(String[][] value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartArray();

        for (String[] row : value) {
            gen.writeRaw("\n"); // force newline after each row for pretty printing
            gen.writeStartArray();
            for (String cell : row) {
                gen.writeString(cell);
            }
            gen.writeEndArray();
        }

        gen.writeRaw("\n");
        gen.writeEndArray();
    }
}
