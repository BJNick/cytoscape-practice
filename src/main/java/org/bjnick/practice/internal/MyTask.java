package org.bjnick.practice.internal;

import org.cytoscape.model.CyNetworkFactory;
import org.cytoscape.model.CyNetworkManager;
import org.cytoscape.work.AbstractTask;
import org.cytoscape.work.TaskMonitor;

public class MyTask extends AbstractTask {

    private final CyNetworkFactory nf;
    private final CyNetworkManager nm;

    public MyTask(CyNetworkFactory nf, CyNetworkManager nm) {
        this.nf = nf;
        this.nm = nm;
    }

    @Override
    public void run(TaskMonitor taskMonitor) throws Exception {
        System.out.println("Hi I'm a task!");
    }

}
