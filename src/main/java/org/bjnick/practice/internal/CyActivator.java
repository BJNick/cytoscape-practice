package org.bjnick.practice.internal;

import org.cytoscape.model.CyNetworkFactory;
import org.cytoscape.model.CyNetworkManager;
import org.cytoscape.service.util.AbstractCyActivator;
import org.cytoscape.work.TaskFactory;
import org.osgi.framework.BundleContext;
import static org.cytoscape.work.ServiceProperties.*;

import java.util.Date;
import java.util.Properties;

public class CyActivator extends AbstractCyActivator {

    /**
     * This is the {@code start} method, which sets up your app. The
     * {@code BundleContext} object allows you to communicate with the OSGi
     * environment. You use {@code BundleContext} to import services or ask OSGi
     * about the status of some service.
     */
    @Override
    public void start(BundleContext bc) throws Exception {
        //MyTaskFactory myFactory = new MyTaskFactory();
        Properties props = new Properties();
        // Note the “.” notation for cascading menus
        props.setProperty(PREFERRED_MENU, "Apps.SampleApp.Test");
        props.setProperty(TITLE, "Load CDD Domains for Node");
        // Not all task factories will be commands
        props.setProperty(COMMAND, "runTask");
        props.setProperty(COMMAND_NAMESPACE, "myapp");
        props.setProperty(IN_MENU_BAR, "true");
        // Usually means the second menu item
        props.setProperty(MENU_GRAVITY, "2.0");

        CyNetworkManager nm = getService(bc, CyNetworkManager.class);
        CyNetworkFactory nf = getService(bc, CyNetworkFactory.class);

        registerService(bc, new MyTaskFactory(nf, nm),
                TaskFactory.class, props);
        System.out.println("Practice App loaded! " + new Date());
    }

}

