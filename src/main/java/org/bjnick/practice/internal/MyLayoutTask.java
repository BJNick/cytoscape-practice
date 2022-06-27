package org.bjnick.practice.internal;

import org.cytoscape.model.CyNode;
import org.cytoscape.view.layout.AbstractLayoutTask;
import org.cytoscape.view.model.CyNetworkView;
import org.cytoscape.view.model.View;
import org.cytoscape.view.presentation.property.BasicVisualLexicon;
import org.cytoscape.work.TaskMonitor;
import org.cytoscape.work.undo.UndoSupport;

import java.util.Random;
import java.util.Set;
import java.util.function.Consumer;

public class MyLayoutTask extends AbstractLayoutTask {

    public MyLayoutTask(CyNetworkView networkView, Set<View<CyNode>> nodesToLayOut, String layoutAttribute, UndoSupport undo) {
        super("My Layout Task", networkView, nodesToLayOut, layoutAttribute, undo);
    }

    @Override
    protected void doLayout(TaskMonitor taskMonitor) {
        taskMonitor.showMessage(TaskMonitor.Level.INFO, "Running layout");
        double prev_node_x = 0, prev_node_y = 0;
        for (View<CyNode> v : nodesToLayOut) {
            Random r = new Random();
            prev_node_x += (r.nextDouble()-0.5) * 400;
            prev_node_y += (r.nextDouble()-0.5) * 400;
            v.setVisualProperty(BasicVisualLexicon.NODE_X_LOCATION, prev_node_x);
            v.setVisualProperty(BasicVisualLexicon.NODE_Y_LOCATION, prev_node_y);
        }
        taskMonitor.showMessage(TaskMonitor.Level.INFO, "Layout complete");
    }

}
