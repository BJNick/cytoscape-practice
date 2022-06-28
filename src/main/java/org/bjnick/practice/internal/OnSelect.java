package org.bjnick.practice.internal;

import org.cytoscape.model.CyEdge;
import org.cytoscape.model.CyNetwork;
import org.cytoscape.model.CyNode;
import org.cytoscape.model.events.SelectedNodesAndEdgesEvent;
import org.cytoscape.model.events.SelectedNodesAndEdgesListener;
import org.cytoscape.view.model.CyNetworkView;
import org.cytoscape.view.model.View;
import org.cytoscape.view.presentation.property.BasicVisualLexicon;
import org.cytoscape.view.presentation.property.NodeShapeVisualProperty;

import java.awt.*;
import java.util.Collection;

public class OnSelect implements SelectedNodesAndEdgesListener {

    private final CyAccess cy;
    private SelectedNodesAndEdgesEvent lastEvent;

    private boolean enabled = false;
    private int desiredHopDistance = 1;

    public OnSelect(CyAccess cy) {
        this.cy = cy;
    }

    public void toggleFeature() {
        enabled = !enabled;

        if (enabled)
            applyHighlighting(lastEvent);
        else
            clearHighlighting(lastEvent);
    }

    public void setDesiredHopDistance(int desiredHopDistance) {
        this.desiredHopDistance = desiredHopDistance;
        if (enabled) {
            clearHighlighting(lastEvent);
            applyHighlighting(lastEvent);
        }
    }

    protected void clearHighlighting(SelectedNodesAndEdgesEvent event) {
        if (event == null) return;
        Collection<CyNetworkView> views = cy.vm.getNetworkViews(event.getNetwork());
        for (CyNetworkView v : views) {
            if (v == null) continue;
            for (CyEdge e : event.getUnselectedEdges()) {
                clearStyle(v.getEdgeView(e));
            }
        }
    }

    protected void applyHighlighting(SelectedNodesAndEdgesEvent event) {
        if (event == null) return;
        Collection<CyNetworkView> views = cy.vm.getNetworkViews(event.getNetwork());
        for (CyNetworkView v : views) {
            for (CyNode n : event.getSelectedNodes()) {
                exploreIncomingEdges(n, event.getNetwork(), v, desiredHopDistance);
                exploreOutgoingEdges(n, event.getNetwork(), v, desiredHopDistance);
            }
        }
    }

    protected void exploreIncomingEdges(CyNode n, CyNetwork net, CyNetworkView view, int depth) {
        if (depth == 0) return;

        for (CyEdge e : net.getAdjacentEdgeIterable(n, CyEdge.Type.INCOMING)) {
            applyIncomingStyle(view.getEdgeView(e));
            exploreIncomingEdges(e.getSource(), net, view, depth-1);
        }
    }

    protected void exploreOutgoingEdges(CyNode n, CyNetwork net, CyNetworkView view, int depth) {
        if (depth == 0) return;

        for (CyEdge e : net.getAdjacentEdgeIterable(n, CyEdge.Type.OUTGOING)) {
            applyOutgoingStyle(view.getEdgeView(e));
            exploreOutgoingEdges(e.getTarget(), net, view, depth-1);
        }
    }

    protected void applyIncomingStyle(View<CyEdge> edgeView) {
        edgeView.setLockedValue(BasicVisualLexicon.EDGE_UNSELECTED_PAINT, Color.BLUE);
        edgeView.setLockedValue(BasicVisualLexicon.EDGE_TRANSPARENCY, 255);
    }

    protected void applyOutgoingStyle(View<CyEdge> edgeView) {
        edgeView.setLockedValue(BasicVisualLexicon.EDGE_UNSELECTED_PAINT, Color.RED);
        edgeView.setLockedValue(BasicVisualLexicon.EDGE_TRANSPARENCY, 255);
    }

    protected void clearStyle(View<CyEdge> edgeView) {
        edgeView.clearValueLock(BasicVisualLexicon.EDGE_UNSELECTED_PAINT);
        edgeView.clearValueLock(BasicVisualLexicon.EDGE_TRANSPARENCY);
    }

    @Override
    public void handleEvent(SelectedNodesAndEdgesEvent event) {
        lastEvent = event;

        if (enabled && event.nodesChanged()) {
            clearHighlighting(event);
            applyHighlighting(event);
        }
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
        if (enabled)
            applyHighlighting(lastEvent);
        else
            clearHighlighting(lastEvent);
    }
}
