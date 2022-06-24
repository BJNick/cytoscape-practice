package org.bjnick.practice.internal;

import org.cytoscape.model.*;
import org.cytoscape.view.model.CyNetworkView;
import org.cytoscape.view.model.CyNetworkViewFactory;
import org.cytoscape.view.model.CyNetworkViewManager;
import org.cytoscape.view.model.VisualProperty;
import org.cytoscape.view.presentation.property.ArrowShapeVisualProperty;
import org.cytoscape.view.presentation.property.BasicVisualLexicon;
import org.cytoscape.view.presentation.property.DefaultVisualizableVisualProperty;
import org.cytoscape.view.presentation.property.NodeShapeVisualProperty;
import org.cytoscape.view.presentation.property.values.NodeShape;
import org.cytoscape.view.vizmap.VisualMappingManager;
import org.cytoscape.view.vizmap.VisualStyle;
import org.cytoscape.view.vizmap.VisualStyleFactory;
import org.cytoscape.view.vizmap.mappings.DiscreteMapping;
import org.cytoscape.work.AbstractTask;
import org.cytoscape.work.TaskMonitor;
import org.cytoscape.session.CyNetworkNaming;
import org.cytoscape.work.Tunable;

import java.awt.*;
import java.util.Arrays;

public class MyTask extends AbstractTask {

    private final CyAccess cy;

    //@Tunable (description = "Your favourite color")
    public int pickedColor = 0x4ACE60;

    public MyTask(CyAccess cy) {
        this.cy = cy;
    }

    @Override
    public void run(TaskMonitor taskMonitor) throws Exception {

        System.out.println("Hi I'm a task!");

        // Creating a new network and adding it
        CyNetwork myNet = cy.nf.createNetwork();
        cy.nm.addNetwork(myNet);
        CyNode n1 = myNet.addNode();
        CyNode n2 = myNet.addNode();
        myNet.addEdge(n1, n2, true); // Directed

        // Check before access
        myNet.getRow(n1).set("name", "B");
        myNet.getRow(n2).set("name", "A");

        CyTable table = myNet.getDefaultNodeTable();
        if (table.getColumn("Hello") == null)
            table.createListColumn("Hello", String.class, false);
        if (table.getColumn("World") == null)
            table.createColumn("World", Double.class, false);

        myNet.getRow(n1).set("Hello", Arrays.asList("1", "2", "3"));
        myNet.getRow(n2).set("World", 3.141596d);

        taskMonitor.showMessage(TaskMonitor.Level.INFO, "Updating view...");
        taskMonitor.showMessage(TaskMonitor.Level.INFO, "Color picked: " + pickedColor);

        try {

            CyNetworkView myView = cy.vf.createNetworkView(myNet);
            cy.vm.addNetworkView(myView);

            // Avoids red circles
            VisualStyle style = cy.vsf.createVisualStyle(cy.vmm.getDefaultVisualStyle());

            style.setTitle("My Style");
            style.setDefaultValue(BasicVisualLexicon.NODE_FILL_COLOR, new Color(0x4ACE60));
            style.setDefaultValue(BasicVisualLexicon.EDGE_TARGET_ARROW_SHAPE, ArrowShapeVisualProperty.ARROW);
            style.setDefaultValue(BasicVisualLexicon.NODE_SHAPE, NodeShapeVisualProperty.DIAMOND);
            // SET ARROW COLOR: https://groups.google.com/g/cytoscape-app-dev/c/lycOc00M-dg/m/bxVQfin-AQAJ


            // All 3 important
            cy.vmm.addVisualStyle(style);
            cy.vmm.setVisualStyle(style, myView);
            style.apply(myView);

            DiscreteMapping<String, Paint> mapping = (DiscreteMapping<String, Paint>) cy.vmff_d.createVisualMappingFunction(
                    "name", String.class, BasicVisualLexicon.NODE_FILL_COLOR);

            mapping.putMapValue("A", new Color(0xF6E105));
            mapping.putMapValue("B", new Color(0x6E8BFF));

            style.addVisualMappingFunction(mapping);

            myView.updateView();



        } catch (Exception e) {
            taskMonitor.showMessage(TaskMonitor.Level.ERROR, e.getLocalizedMessage());
        }

        taskMonitor.showMessage(TaskMonitor.Level.INFO, "Task completed.");

    }

}
