package org.eclipse.smarthome.config.setup.internal;

import org.eclipse.smarthome.config.setup.SetupFlowManager;
import org.eclipse.smarthome.config.setup.SetupStepHandlerId;
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
    public final void start(BundleContext context) throws Exception {
        SetupStepHandlerId id = new SetupStepHandlerId("flow", "binding", "step", "thing");
        System.out.println(id.toURI());
        XMLTypeParser setupFlowParser = new XMLTypeParser(
                context.getBundle().getResource(SETUP_FLOW_XSD_FILE), SetupFlows.class);

        this.setupFlowManager = new SetupFlowManagerImpl();

        this.setupFlowTracker = new DeclarativeSetupFlowTracker(
                context, setupFlowParser, this.setupFlowManager);

        this.setupFlowTracker.open();

        this.setupFlowManagerReg = context.registerService(
                SetupFlowManager.class.getName(), this.setupFlowManager, null);
    }

    @Override
    public final void stop(BundleContext context) throws Exception {
        this.setupFlowManagerReg.unregister();

        this.setupFlowTracker.close();

        this.setupFlowManager.release();
    }

}
