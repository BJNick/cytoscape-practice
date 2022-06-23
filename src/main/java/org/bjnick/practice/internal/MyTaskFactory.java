package org.bjnick.practice.internal;

import org.cytoscape.model.CyNetworkFactory;
import org.cytoscape.model.CyNetworkManager;
import org.cytoscape.work.AbstractTaskFactory;
import org.cytoscape.work.TaskIterator;

public class MyTaskFactory extends AbstractTaskFactory {

    private final CyNetworkFactory nf;
    private final CyNetworkManager nm;

    public MyTaskFactory(CyNetworkFactory nf, CyNetworkManager nm) {
        this.nf = nf;
        this.nm = nm;
    }

    @Override
    public TaskIterator createTaskIterator() {
        return new TaskIterator(new MyTask(nf, nm));
    }

}
