package org.bjnick.practice.internal;

import org.cytoscape.model.CyEdge;
import org.cytoscape.model.CyNode;
import org.cytoscape.model.events.SelectedNodesAndEdgesEvent;
import org.cytoscape.model.events.SelectedNodesAndEdgesListener;
import org.cytoscape.view.model.CyNetworkView;
import org.cytoscape.view.presentation.property.BasicVisualLexicon;
import org.cytoscape.view.presentation.property.NodeShapeVisualProperty;

import java.util.Collection;

public class OnSelect implements SelectedNodesAndEdgesListener {

    private final CyAccess cy;

    public OnSelect(CyAccess cy) {
        this.cy = cy;
    }

    @Override
    public void handleEvent(SelectedNodesAndEdgesEvent event) {
        if (event.nodesChanged()) {
            Collection<CyNetworkView> views = cy.vm.getNetworkViews(event.getNetwork());
            for (CyNetworkView v : views) {
                if (v == null) continue;
                for (CyNode n : event.getSelectedNodes()) {
                    v.getNodeView(n).setLockedValue(BasicVisualLexicon.NODE_SHAPE,
                                                NodeShapeVisualProperty.HEXAGON);
                }
                for (CyNode n : event.getUnselectedNodes()) {
                    v.getNodeView(n).clearValueLock(BasicVisualLexicon.NODE_SHAPE);
                }
                v.updateView();
            }
        }
    }
}
