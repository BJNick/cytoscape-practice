package org.bjnick.practice.internal;

import org.cytoscape.application.swing.AbstractCyAction;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class HopDistanceAction extends AbstractCyAction {

    private OnSelect onSelect;

    public int counter = 1;

    public HopDistanceAction(OnSelect onSelect) {
        super("HopDistanceAction");

        this.onSelect = onSelect;

        ImageIcon icon = new ImageIcon(getClass().getResource("/hop1.png"));
        putValue(LARGE_ICON_KEY, icon);

        setToolbarGravity(15.5f);

        this.useToggleButton = false;
        this.inToolBar = true;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        counter = (counter % 3) + 1;
        onSelect.setDesiredHopDistance(counter);
        // TODO: (bug) Icon is bigger and cropped when set on runtime
        ImageIcon icon = new ImageIcon(getClass().getResource("/hop" + counter + ".png"));
        putValue(LARGE_ICON_KEY, icon);

    }

}
