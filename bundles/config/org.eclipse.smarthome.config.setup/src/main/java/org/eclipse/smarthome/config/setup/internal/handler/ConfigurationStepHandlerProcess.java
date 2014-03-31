package org.eclipse.smarthome.config.setup.internal.handler;

import org.eclipse.smarthome.config.setup.handler.ConfigurationStepHandler;


public class ConfigurationStepHandlerProcess extends GenericSetupStepProcess {

    private ConfigurationStepHandler stepHandler;


    public ConfigurationStepHandlerProcess(ConfigurationStepHandler stepHandler)
            throws IllegalArgumentException {

        if (stepHandler == null) {
            throw new IllegalArgumentException("The ConfigurationStepHandler must not be null!");
        }

        this.stepHandler = stepHandler;
    }

    @Override
    protected final void startProcess() throws Exception {
        this.stepHandler.configured();
    }

    @Override
    protected void abortProcess() throws Exception {
    }

}
