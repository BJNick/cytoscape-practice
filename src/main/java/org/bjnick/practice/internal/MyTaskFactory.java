package org.bjnick.practice.internal;

import org.cytoscape.model.CyNetworkFactory;
import org.cytoscape.model.CyNetworkManager;
import org.cytoscape.session.CyNetworkNaming;
import org.cytoscape.view.model.CyNetworkViewFactory;
import org.cytoscape.view.model.CyNetworkViewManager;
import org.cytoscape.work.AbstractTaskFactory;
import org.cytoscape.work.TaskIterator;

public class MyTaskFactory extends AbstractTaskFactory {

    private final CyNetworkFactory nf;
    private final CyNetworkManager nm;
    private final CyNetworkViewFactory vf;
    private final CyNetworkViewManager vm;
    private final CyNetworkNaming cyNetworkNaming;


    public MyTaskFactory(CyNetworkFactory nf, CyNetworkManager nm, CyNetworkViewFactory vf, CyNetworkViewManager vm, CyNetworkNaming cyNetworkNaming) {
        this.nf = nf;
        this.nm = nm;
        this.vf = vf;
        this.vm = vm;
        this.cyNetworkNaming = cyNetworkNaming;
    }

    @Override
    public TaskIterator createTaskIterator() {
        return new TaskIterator(new MyTask(nf, nm, vf, vm, cyNetworkNaming));
    }

}
