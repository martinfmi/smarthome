package org.eclipse.smarthome.config.setup.internal;

import org.eclipse.smarthome.config.setup.SetupFlowManager;
import org.eclipse.smarthome.config.setup.flow.SetupFlows;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;


public class Activator implements BundleActivator {

    /** The constant defining the schema file used for validation. */
    private static final String SETUP_FLOW_XSD_FILE = "org.eclipse.smarthome.config.setup.schema.xsd";

    private DeclarativeSetupFlowTracker setupFlowTracker;

    private SetupFlowManagerImpl setupFlowManager;
    private ServiceRegistration setupFlowManagerReg;


    @Override
    public void start(BundleContext context) throws Exception {
        XMLTypeParser<SetupFlows> setupFlowParser = new XMLTypeParser<>(
                context.getBundle().getResource(SETUP_FLOW_XSD_FILE), SetupFlows.class);

        this.setupFlowTracker = new DeclarativeSetupFlowTracker(context, setupFlowParser);

        this.setupFlowManager = new SetupFlowManagerImpl();
        this.setupFlowManagerReg = context.registerService(
                SetupFlowManager.class.getName(), this.setupFlowManager, null);
    }

    @Override
    public void stop(BundleContext context) throws Exception {
        this.setupFlowManagerReg.unregister();
        this.setupFlowManager.release();
    }

}
