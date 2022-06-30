package org.bjnick.practice.internal;

import org.cytoscape.model.CyEdge;
import org.cytoscape.model.CyNetwork;
import org.cytoscape.model.CyNode;
import org.cytoscape.model.events.SelectedNodesAndEdgesEvent;
import org.cytoscape.model.events.SelectedNodesAndEdgesListener;
import org.cytoscape.model.subnetwork.CyRootNetwork;
import org.cytoscape.view.layout.CyLayoutAlgorithm;
import org.cytoscape.view.model.CyNetworkView;
import org.cytoscape.view.model.View;
import org.cytoscape.view.model.VisualProperty;
import org.cytoscape.view.presentation.property.BasicVisualLexicon;
import org.cytoscape.view.presentation.property.NodeShapeVisualProperty;
import org.cytoscape.view.vizmap.VisualStyle;
import org.cytoscape.work.TaskIterator;

import java.awt.*;
import java.util.*;

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
                clearStyle(e, v);
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

    protected void exploreIncomingEdges(CyNode n, CyNetwork net, CyNetworkView view, int depth, Collection<CyNode> nodes, Collection<CyEdge> edges) {
        if (nodes != null) nodes.add(n);
        if (depth == 0) return;

        for (CyEdge e : net.getAdjacentEdgeIterable(n, CyEdge.Type.INCOMING)) {
            if (edges != null) edges.add(e);
            if (view != null)
                applyIncomingStyle(e, view);
            exploreIncomingEdges(e.getSource(), net, view, depth-1, nodes, edges);
        }
    }

    protected void exploreOutgoingEdges(CyNode n, CyNetwork net, CyNetworkView view, int depth, Collection<CyNode> nodes, Collection<CyEdge> edges) {
        if (nodes != null) nodes.add(n);
        if (depth == 0) return;

        for (CyEdge e : net.getAdjacentEdgeIterable(n, CyEdge.Type.OUTGOING)) {
            if (edges != null) edges.add(e);
            if (view != null)
                applyOutgoingStyle(e, view);
            exploreOutgoingEdges(e.getTarget(), net, view, depth-1, nodes, edges);
        }
    }

    protected void exploreIncomingEdges(CyNode n, CyNetwork net, CyNetworkView view, int depth) {
        exploreIncomingEdges(n, net, view, depth, null, null);
    }

    protected void exploreOutgoingEdges(CyNode n, CyNetwork net, CyNetworkView view, int depth) {
        exploreOutgoingEdges(n, net, view, depth, null, null);
    }

        protected void applyIncomingStyle(CyEdge edge, CyNetworkView view) {
        View<CyEdge> edgeView = view.getEdgeView(edge);
        edgeView.setLockedValue(BasicVisualLexicon.EDGE_UNSELECTED_PAINT, Color.BLUE);
        edgeView.setLockedValue(BasicVisualLexicon.EDGE_TRANSPARENCY, 255);
        View<CyNode> nodeView = view.getNodeView(edge.getSource());
        nodeView.setLockedValue(BasicVisualLexicon.NODE_TRANSPARENCY, 255);
    }

    protected void applyOutgoingStyle(CyEdge edge, CyNetworkView view) {
        View<CyEdge> edgeView = view.getEdgeView(edge);
        edgeView.setLockedValue(BasicVisualLexicon.EDGE_UNSELECTED_PAINT, Color.RED);
        edgeView.setLockedValue(BasicVisualLexicon.EDGE_TRANSPARENCY, 255);
        View<CyNode> nodeView = view.getNodeView(edge.getTarget());
        nodeView.setLockedValue(BasicVisualLexicon.NODE_TRANSPARENCY, 255);
    }

    protected void clearStyle(CyEdge edge, CyNetworkView view) {
        View<CyEdge> edgeView = view.getEdgeView(edge);
        edgeView.clearValueLock(BasicVisualLexicon.EDGE_UNSELECTED_PAINT);
        edgeView.clearValueLock(BasicVisualLexicon.EDGE_TRANSPARENCY);
        View<CyNode> nodeView = view.getNodeView(edge.getSource());
        nodeView.clearValueLock(BasicVisualLexicon.NODE_TRANSPARENCY);
        nodeView = view.getNodeView(edge.getTarget());
        nodeView.clearValueLock(BasicVisualLexicon.NODE_TRANSPARENCY);
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

    public void selectionToNetwork() {
        // TODO: Check if anything is selected, send user notifications

        if (lastEvent == null) {
            System.out.println("Last event is null");
            return;
        }

        CyNetwork supernet = lastEvent.getNetwork();
        CyRootNetwork root = cy.rnm.getRootNetwork(supernet);

        ArrayList<CyNode> selectedNodes = new ArrayList<>();
        ArrayList<CyEdge> selectedEdges = new ArrayList<>();

        for (CyNode n : lastEvent.getSelectedNodes()) {
            exploreIncomingEdges(n, supernet, null, desiredHopDistance, selectedNodes, selectedEdges);
            exploreOutgoingEdges(n, supernet, null, desiredHopDistance, selectedNodes, selectedEdges);
        }

        CyNetwork net = root.addSubNetwork(selectedNodes, selectedEdges);

        cy.nm.addNetwork(net);

        CyNetworkView view = cy.vf.createNetworkView(net);
        cy.vm.addNetworkView(view);

        Collection<CyNetworkView> oldViewList = cy.vm.getNetworkViews(supernet);

        if (!oldViewList.isEmpty()) {

            CyNetworkView oldView = oldViewList.iterator().next();

            VisualStyle style = cy.vmm.getVisualStyle(oldView);

            cy.vmm.addVisualStyle(style);
            cy.vmm.setVisualStyle(style, view);
            style.apply(view);

            // Copy node positions and zoom

            for (CyNode n : selectedNodes) {
                view.getNodeView(n).setVisualProperty(BasicVisualLexicon.NODE_X_LOCATION, oldView.getNodeView(n).getVisualProperty(BasicVisualLexicon.NODE_X_LOCATION));
                view.getNodeView(n).setVisualProperty(BasicVisualLexicon.NODE_Y_LOCATION, oldView.getNodeView(n).getVisualProperty(BasicVisualLexicon.NODE_Y_LOCATION));
            }

            VisualProperty[] toCopy = new VisualProperty[] {
                    BasicVisualLexicon.NETWORK_CENTER_X_LOCATION,
                    BasicVisualLexicon.NETWORK_CENTER_Y_LOCATION,
                    BasicVisualLexicon.NETWORK_HEIGHT,
                    BasicVisualLexicon.NETWORK_WIDTH,
                    BasicVisualLexicon.NETWORK_SCALE_FACTOR,
                    BasicVisualLexicon.NETWORK_CENTER_Z_LOCATION,
                    BasicVisualLexicon.NETWORK_SIZE};
            for (VisualProperty vp : toCopy)
                view.setVisualProperty(vp, oldView.getVisualProperty(vp));


            for (CyNode n : lastEvent.getSelectedNodes()) {
                exploreIncomingEdges(n, supernet, view, desiredHopDistance);
                exploreOutgoingEdges(n, supernet, view, desiredHopDistance);
            }
        }


        /*CyLayoutAlgorithm layout = cy.lam.getDefaultLayout();

        Object context = layout.createLayoutContext();
        String layoutAttribute = null;

        TaskIterator layoutTasks = layout.createTaskIterator(view, context, CyLayoutAlgorithm.ALL_NODE_VIEWS, layoutAttribute);

        cy.taskm.execute(layoutTasks);*/


    }
}
