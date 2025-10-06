package nz.ac.wgtn.swen225.lc.app.gui.logic;

public interface LogicPanel {
    void updatePanel(int info);
    default void initialisePanelInfo(int info){}
}
