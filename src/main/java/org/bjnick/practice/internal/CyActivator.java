package org.bjnick.practice.internal;

import org.cytoscape.model.CyNetworkFactory;
import org.cytoscape.model.CyNetworkManager;
import org.cytoscape.model.events.SelectedNodesAndEdgesListener;
import org.cytoscape.service.util.AbstractCyActivator;
import org.cytoscape.session.CyNetworkNaming;
import org.cytoscape.task.NetworkViewTaskFactory;
import org.cytoscape.view.layout.CyLayoutAlgorithm;
import org.cytoscape.view.layout.CyLayoutAlgorithmManager;
import org.cytoscape.view.model.CyNetworkViewFactory;
import org.cytoscape.view.model.CyNetworkViewManager;
import org.cytoscape.view.vizmap.VisualMappingFunctionFactory;
import org.cytoscape.view.vizmap.VisualMappingManager;
import org.cytoscape.view.vizmap.VisualStyleFactory;
import org.cytoscape.work.TaskFactory;
import org.cytoscape.work.TunableSetter;
import org.cytoscape.work.undo.UndoSupport;
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
        // Note the "." notation for cascading menus
        props.setProperty(PREFERRED_MENU, "Apps");
        props.setProperty(TITLE, "Run MyTask");
        // Not all task factories will be commands
        props.setProperty(COMMAND, "runTask");
        props.setProperty(COMMAND_NAMESPACE, "myapp");
        props.setProperty(IN_MENU_BAR, "true");
        // Usually means the second menu item
        props.setProperty(MENU_GRAVITY, "2.0");

        CyNetworkManager nm = getService(bc, CyNetworkManager.class);
        CyNetworkFactory nf = getService(bc, CyNetworkFactory.class);
        CyNetworkViewFactory vf = getService(bc, CyNetworkViewFactory.class);
        CyNetworkViewManager vm = getService(bc, CyNetworkViewManager.class);
        CyNetworkNaming cnn = getService(bc, CyNetworkNaming.class);
        VisualMappingManager vmm = getService(bc, VisualMappingManager.class);
        VisualStyleFactory vsf = getService(bc, VisualStyleFactory.class);
        UndoSupport undoSupport = getService(bc, UndoSupport.class);
		CyLayoutAlgorithmManager lam = getService(bc, CyLayoutAlgorithmManager.class);
		TunableSetter ts = getService(bc, TunableSetter.class);

        VisualMappingFunctionFactory vmff_c = getService(bc, VisualMappingFunctionFactory.class,
                "(mapping.type=continuous)");

        VisualMappingFunctionFactory vmff_d = getService(bc, VisualMappingFunctionFactory.class,
                "(mapping.type=discrete)");

        VisualMappingFunctionFactory vmff_p = getService(bc, VisualMappingFunctionFactory.class,
                "(mapping.type=passthrough)");

        CyAccess cy = new CyAccess(nf, nm, vf, vm, cnn, vmm, vsf, vmff_c, vmff_d, vmff_p, lam, ts);

        registerService(bc, new MyTaskFactory(cy),
                TaskFactory.class, props);

        // ON SELECT EVENT

        registerService(bc, new OnSelect(cy), SelectedNodesAndEdgesListener.class);

        // CUSTOM LAYOUT

        /*
        CustomLayout customLayout = new CustomLayout(undo);

		Properties customLayoutProps = new Properties();
		customLayoutProps.setProperty(PREFERRED_MENU, "Custom Layouts");
		registerService(bc, customLayout, CyLayoutAlgorithm.class, customLayoutProps);

		// ApplyCustomLayoutTaskFactory service
		CyLayoutAlgorithmManager layoutManager = getService(bc, CyLayoutAlgorithmManager.class);
		TunableSetter tunableSetter = getService(bc, TunableSetter.class);
		ApplyCustomLayoutTaskFactory applyLayoutTaskFactory = new ApplyCustomLayoutTaskFactory(layoutManager, tunableSetter);

		Properties applyCustomLayoutProperties = new Properties();
		applyCustomLayoutProperties.setProperty(PREFERRED_MENU, "Apps.Samples");
		applyCustomLayoutProperties.setProperty(TITLE, "Apply Custom Layout");
		registerService(bc, applyLayoutTaskFactory, NetworkViewTaskFactory.class, applyCustomLayoutProperties);
        */

        Properties myLayoutProps = new Properties();
		myLayoutProps.setProperty(PREFERRED_MENU, "Custom Layouts");

        registerService(bc, new MyLayoutAlgorithm(undoSupport), CyLayoutAlgorithm.class, myLayoutProps);

		ApplyMyLayoutTaskFactory applyLayoutTaskFactory = new ApplyMyLayoutTaskFactory(lam, ts);

        Properties applyCustomLayoutProperties = new Properties();
		applyCustomLayoutProperties.setProperty(PREFERRED_MENU, "Apps");
		applyCustomLayoutProperties.setProperty(TITLE, "Apply My Layout");
        applyCustomLayoutProperties.setProperty(MENU_GRAVITY, "2.0");
		registerService(bc, applyLayoutTaskFactory, NetworkViewTaskFactory.class, applyCustomLayoutProperties);


        System.out.println("Practice App loaded! " + new Date());
    }

}

