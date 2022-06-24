package org.bjnick.practice.internal;

import org.cytoscape.model.CyNetworkFactory;
import org.cytoscape.model.CyNetworkManager;
import org.cytoscape.session.CyNetworkNaming;
import org.cytoscape.view.model.CyNetworkViewFactory;
import org.cytoscape.view.model.CyNetworkViewManager;
import org.cytoscape.view.vizmap.VisualMappingFunctionFactory;
import org.cytoscape.view.vizmap.VisualMappingManager;
import org.cytoscape.view.vizmap.VisualStyleFactory;

public class CyAccess {


    public final CyNetworkFactory nf;
    public final CyNetworkManager nm;
    public final CyNetworkViewFactory vf;
    public final CyNetworkViewManager vm;
    public final CyNetworkNaming cnn;
    public final VisualMappingManager vmm;
    public final VisualStyleFactory vsf;
    public final VisualMappingFunctionFactory vmff_c, vmff_d, vmff_p;


    public CyAccess(CyNetworkFactory nf, CyNetworkManager nm, CyNetworkViewFactory vf, CyNetworkViewManager vm, CyNetworkNaming cnn, VisualMappingManager vmm, VisualStyleFactory vsf, VisualMappingFunctionFactory vmff_c, VisualMappingFunctionFactory vmff_d, VisualMappingFunctionFactory vmff_p) {
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
    }


}
