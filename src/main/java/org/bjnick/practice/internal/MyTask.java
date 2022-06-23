package org.bjnick.practice.internal;

import org.cytoscape.model.*;
import org.cytoscape.view.model.CyNetworkView;
import org.cytoscape.view.model.CyNetworkViewFactory;
import org.cytoscape.view.model.CyNetworkViewManager;
import org.cytoscape.work.AbstractTask;
import org.cytoscape.work.TaskMonitor;
import org.cytoscape.session.CyNetworkNaming;

import java.util.Arrays;

public class MyTask extends AbstractTask {

    private final CyNetworkFactory nf;
    private final CyNetworkManager nm;
    private final CyNetworkViewFactory vf;
    private final CyNetworkViewManager vm;
    private final CyNetworkNaming cyNetworkNaming;


    public MyTask(CyNetworkFactory nf, CyNetworkManager nm, CyNetworkViewFactory vf, CyNetworkViewManager vm, CyNetworkNaming cyNetworkNaming) {
        this.nf = nf;
        this.nm = nm;
        this.vf = vf;
        this.vm = vm;
        this.cyNetworkNaming = cyNetworkNaming;
    }

    @Override
    public void run(TaskMonitor taskMonitor) throws Exception {

        System.out.println("Hi I'm a task!");

        // Creating a new network and adding it
        CyNetwork myNet = nf.createNetwork();
        nm.addNetwork(myNet);
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

        CyNetworkView myView = vf.createNetworkView(myNet);
        vm.addNetworkView(myView);




    }

}
