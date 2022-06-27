package org.bjnick.practice.internal;

import org.cytoscape.model.CyNode;
import org.cytoscape.view.layout.AbstractLayoutAlgorithm;
import org.cytoscape.view.model.CyNetworkView;
import org.cytoscape.view.model.View;
import org.cytoscape.work.TaskIterator;
import org.cytoscape.work.undo.UndoSupport;

import java.util.Set;

public class MyLayoutAlgorithm extends AbstractLayoutAlgorithm {


    public MyLayoutAlgorithm(UndoSupport undoSupport) {
        super("MyLayout", "My awesome layout algorithm", undoSupport);
    }

    @Override
    public TaskIterator createTaskIterator(CyNetworkView cyNetworkView, Object o, Set<View<CyNode>> set, String s) {
        return new TaskIterator(new MyLayoutTask(cyNetworkView, set, s, undoSupport));
    }

}
