package nz.ac.wgtn.swen225.lc.app.gui.logic;

/**
 * LogicPanel interface for panels that display game information.
 * @author Joshua Pinpin (Student ID: 300662880)
 */
public interface LogicPanel {
    /**
     * Update the panel with the current game information.
     * @param info Information to update the panel with
     */
    void updatePanel(int info);

    /**
     * Initialise the panel with the maximum game information.
     * @param info Maximum information to initialise the panel with
     */
    default void initialisePanelInfo(int info){}
}
