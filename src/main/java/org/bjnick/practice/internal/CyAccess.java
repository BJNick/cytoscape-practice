package org.bjnick.practice.internal;

import org.cytoscape.model.CyNetworkFactory;
import org.cytoscape.model.CyNetworkManager;
import org.cytoscape.model.CyNetworkTableManager;
import org.cytoscape.model.CyTableManager;
import org.cytoscape.model.subnetwork.CyRootNetworkManager;
import org.cytoscape.session.CyNetworkNaming;
import org.cytoscape.view.layout.CyLayoutAlgorithmManager;
import org.cytoscape.view.model.CyNetworkViewFactory;
import org.cytoscape.view.model.CyNetworkViewManager;
import org.cytoscape.view.vizmap.VisualMappingFunctionFactory;
import org.cytoscape.view.vizmap.VisualMappingManager;
import org.cytoscape.view.vizmap.VisualStyleFactory;
import org.cytoscape.work.TaskManager;
import org.cytoscape.work.TunableSetter;

public class CyAccess {


    public final CyNetworkFactory nf;
    public final CyNetworkManager nm;
    public final CyNetworkViewFactory vf;
    public final CyNetworkViewManager vm;
    public final CyNetworkNaming cnn;
    public final VisualMappingManager vmm;
    public final VisualStyleFactory vsf;
    public final VisualMappingFunctionFactory vmff_c, vmff_d, vmff_p;
    public final CyLayoutAlgorithmManager lam;
    public final TunableSetter ts;
    public final CyNetworkTableManager ntm;
    public final CyTableManager tm;
    public final CyRootNetworkManager rnm;
    public final TaskManager taskm;


    public CyAccess(CyNetworkFactory nf, CyNetworkManager nm, CyNetworkViewFactory vf, CyNetworkViewManager vm, CyNetworkNaming cnn, VisualMappingManager vmm, VisualStyleFactory vsf, VisualMappingFunctionFactory vmff_c, VisualMappingFunctionFactory vmff_d, VisualMappingFunctionFactory vmff_p, CyLayoutAlgorithmManager lam, TunableSetter ts, CyNetworkTableManager ntm, CyTableManager tm, CyRootNetworkManager rnm, TaskManager taskm) {
        this.nf = nf;
        this.nm = nm;
        this.vf = vf;
        this.vm = vm;
        this.cnn = cnn;
        this.vmm = vmm;
        this.vsf = vsf;
        this.vmff_c = vmff_c;
        this.vmff_d = vmff_d;
        this.vmff_p = vmff_p;
        this.lam = lam;
        this.ts = ts;
        this.ntm = ntm;
        this.tm = tm;
        this.rnm = rnm;
        this.taskm = taskm;
    }


}
