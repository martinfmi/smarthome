package org.eclipse.smarthome.config.setup.internal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.smarthome.config.setup.SetupFlowManager;
import org.eclipse.smarthome.config.setup.SetupStepHandlerId;
import org.eclipse.smarthome.config.setup.SetupStepProcess;
import org.eclipse.smarthome.config.setup.flow.SetupFlow;
import org.eclipse.smarthome.config.setup.handler.SetupStepHandler;
import org.eclipse.smarthome.config.setup.internal.handler.SetupStepProcessBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class SetupFlowManagerImpl implements SetupFlowManager {

    private Logger logger = LoggerFactory.getLogger(SetupFlowManagerImpl.class);

    private List<SetupFlowProvider> setupFlowProviders;

    private List<SetupStepHandlerId> setupStepHandlerIdList;
    private Map<String, SetupStepProcess> uriSetupStepProcessMap;

    private boolean invalid;


    public SetupFlowManagerImpl() {
        this.setupFlowProviders = new ArrayList<>();

        this.setupStepHandlerIdList = new ArrayList<>();
        this.uriSetupStepProcessMap = new HashMap<>();
    }

    public final synchronized void release() {
        if (!this.invalid) {
            this.logger.debug("Release service...");

            this.setupFlowProviders.clear();

            this.setupStepHandlerIdList.clear();
            this.uriSetupStepProcessMap.clear();

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
    public final synchronized SetupFlow getSetupFlow(String thingType) throws IllegalStateException {
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
    public final boolean registerSetupStepHandler(SetupStepHandlerId stepHandlerId,
            SetupStepHandler stepHandler) throws IllegalArgumentException {

        if (stepHandlerId == null) {
            throw new IllegalArgumentException("The SetupStepHandlerId must not be null!");
        }
        if (stepHandler == null) {
            throw new IllegalArgumentException("The SetupStepHandler must not be null!");
        }

        if (!this.setupStepHandlerIdList.contains(stepHandlerId)) {
            String stepHandlerURI = stepHandlerId.toURI().toString();

            if (!this.uriSetupStepProcessMap.containsKey(stepHandlerURI)) {
                SetupStepProcess stepProcess =
                        SetupStepProcessBuilder.createSetupStepProcess(stepHandler);

                this.setupStepHandlerIdList.add(stepHandlerId);
                this.uriSetupStepProcessMap.put(stepHandlerURI, stepProcess);

                return true;
            }
        }

        return false;
    }

    @Override
    public final boolean unregisterSetupStepHandler(SetupStepHandlerId stepHandlerId)
            throws IllegalArgumentException {

        if (stepHandlerId == null) {
            throw new IllegalArgumentException("The SetupStepHandlerId must not be null!");
        }

        if (this.setupStepHandlerIdList.contains(stepHandlerId)) {
            String stepHandlerURI = stepHandlerId.toURI().toString();

            this.setupStepHandlerIdList.remove(stepHandlerId);
            this.uriSetupStepProcessMap.remove(stepHandlerURI);

            return true;
        }

        return false;
    }

    @Override
    public final SetupStepProcess getSetupStepProcess(SetupStepHandlerId stepHandlerId) {
        if (stepHandlerId != null) {
            String stepHandlerURI = stepHandlerId.toURI().toString();

            return this.uriSetupStepProcessMap.get(stepHandlerURI);
        }

        return null;
    }

}
