package nz.ac.wgtn.swen225.lc.persistency.levelloader;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class BoardSerializer extends JsonSerializer<String[][]> {
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
