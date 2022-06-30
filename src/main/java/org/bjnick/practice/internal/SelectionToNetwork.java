package org.bjnick.practice.internal;

import org.cytoscape.application.swing.AbstractCyAction;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class SelectionToNetwork extends AbstractCyAction {

    private OnSelect onSelect;

    public SelectionToNetwork(OnSelect onSelect) {
        super("SelectionToNetwork");

        this.onSelect = onSelect;

        ImageIcon icon = new ImageIcon(getClass().getResource("/icon-new-graph.png"));
        putValue(LARGE_ICON_KEY, icon);
        setPreferredMenu("Apps");

        setToolbarGravity(15.9f);

        this.useToggleButton = false;
        this.inToolBar = true;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        onSelect.selectionToNetwork();
    }

}