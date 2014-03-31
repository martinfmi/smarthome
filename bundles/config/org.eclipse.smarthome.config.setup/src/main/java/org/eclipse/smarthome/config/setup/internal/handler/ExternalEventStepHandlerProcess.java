package org.eclipse.smarthome.config.setup.internal.handler;

import org.eclipse.smarthome.config.setup.handler.ExternalEventStepHandler;


public class ExternalEventStepHandlerProcess extends GenericSetupStepProcess {

    private ExternalEventStepHandler stepHandler;


    public ExternalEventStepHandlerProcess(ExternalEventStepHandler stepHandler)
            throws IllegalArgumentException {

        if (stepHandler == null) {
            throw new IllegalArgumentException("The ExternalEventStepHandler must not be null!");
        }

        this.stepHandler = stepHandler;
    }

    @Override
    protected final void startProcess() throws Exception {
        this.stepHandler.execute(null);
    }

    @Override
    protected final void abortProcess() throws Exception {
        this.stepHandler.abort();
    }

}
