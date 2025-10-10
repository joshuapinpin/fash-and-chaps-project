package nz.ac.wgtn.swen225.lc.persistency.saver;

import javax.swing.JFrame;
import java.util.Optional;

/**
 * API for serialising and deserialising objects, via graphical dialogs.
 * @param <T> - the datatype to serialise/deserialise.
 */
public interface PersistManager<T> {
    /**
     * Given some object, allow a user to save it to a file of their choice.
     * @param data - the object to serialise.
     * @param parent - the parent JFrame/window.
     */
    boolean save(T data, JFrame parent);

    /**
     * Allow a user to select some file from which to load an object from.
     * @param parent - the parent JFrame/window.
     * @return - the deserialised object.
     */
    Optional<T> load(JFrame parent);
}
