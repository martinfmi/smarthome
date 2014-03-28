package org.eclipse.smarthome.config.setup.internal;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.smarthome.config.setup.SetupFlowManager;
import org.eclipse.smarthome.config.setup.SetupStepHandlerId;
import org.eclipse.smarthome.config.setup.SetupStepProcess;
import org.eclipse.smarthome.config.setup.flow.SetupFlow;
import org.eclipse.smarthome.config.setup.handler.SetupStepHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class SetupFlowManagerImpl implements SetupFlowManager {

    private Logger logger = LoggerFactory.getLogger(SetupFlowManagerImpl.class);

    private List<SetupFlowProvider> setupFlowProviders;

    private boolean invalid;


    public SetupFlowManagerImpl() {
        this.setupFlowProviders = new ArrayList<>();
    }

    public final synchronized void release() {
        if (!this.invalid) {
            this.logger.debug("Release service...");
            this.setupFlowProviders.clear();
            this.invalid = true;
        }
    }

    private void assertServiceValid() throws IllegalStateException {
        if (this.invalid) {
            throw new IllegalStateException("The service is no longer available!");
        }
    }

    public final synchronized void addSetupFlowProvider(SetupFlowProvider flowProvider)
            throws IllegalStateException {

        assertServiceValid();

        if (flowProvider != null) {
            if (!this.setupFlowProviders.contains(flowProvider)) {
                this.logger.debug("Add SetupFlowProvider '{}'...", flowProvider);
                this.setupFlowProviders.add(flowProvider);
            }
        }
    }

    public final synchronized void removeFlowProvider(SetupFlowProvider flowProvider)
            throws IllegalStateException {

        assertServiceValid();

        if (flowProvider != null) {
            boolean removed = this.setupFlowProviders.remove(flowProvider);
            if (removed) {
                this.logger.debug("SetupFlowProvider '{}' has been removed.", flowProvider);
            }
        }
    }

    @Override
    public synchronized SetupFlow getSetupFlow(String thingType) throws IllegalStateException {
        assertServiceValid();

        for (SetupFlowProvider setupFlowProvider : this.setupFlowProviders) {
            SetupFlow setupFlow = setupFlowProvider.getSetupFlow(thingType);

            if (setupFlow != null) {
                return setupFlow;
            }
        }

        return null;
    }

    @Override
    public boolean registerSetupStepHandler(SetupStepHandlerId stepHandlerId,
            SetupStepHandler stepHandler) {

        return false;
    }

    @Override
    public boolean unregisterSetupStepHandler(SetupStepHandlerId stepHandlerId,
            SetupStepHandler stepHandler) {

        return false;
    }

    @Override
    public SetupStepProcess getSetupStepProcess(SetupStepHandlerId stepHandlerId) {
        return null;
    }

}
