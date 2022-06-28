package org.bjnick.practice.internal;

import org.cytoscape.application.swing.AbstractCyAction;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class DirectedEdgesAction extends AbstractCyAction {

    private OnSelect onSelect;

    public DirectedEdgesAction(OnSelect onSelect) {
        super("DirectedEdgeColor");

        this.onSelect = onSelect;

        ImageIcon icon = new ImageIcon(getClass().getResource("/colored-arrows-icon3.png"));
        putValue(LARGE_ICON_KEY, icon);
        setPreferredMenu("Apps");

        setToolbarGravity(15);

        this.useCheckBoxMenuItem = true;
        this.useToggleButton = true;
        this.inMenuBar = true;
        this.inToolBar = true;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        onSelect.toggleFeature();
    }

}
