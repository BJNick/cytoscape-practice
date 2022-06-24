package org.bjnick.practice.internal;

import org.cytoscape.work.AbstractTaskFactory;
import org.cytoscape.work.TaskIterator;

public class MyTaskFactory extends AbstractTaskFactory {

    private final CyAccess cy;

    public MyTaskFactory(CyAccess cy) {
        this.cy = cy;
    }


    @Override
    public TaskIterator createTaskIterator() {
        return new TaskIterator(new MyTask(cy));
    }

}
