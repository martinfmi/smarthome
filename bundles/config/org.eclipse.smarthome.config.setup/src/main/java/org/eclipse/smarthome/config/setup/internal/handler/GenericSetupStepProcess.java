package org.eclipse.smarthome.config.setup.internal.handler;

import org.eclipse.smarthome.config.setup.SetupStepHandlerException;
import org.eclipse.smarthome.config.setup.SetupStepProcess;


public abstract class GenericSetupStepProcess implements SetupStepProcess {

    @Override
    public void start() throws SetupStepHandlerException {
        try {
            startProcess();
        } catch (Exception ex) {
            throw new SetupStepHandlerException(ex);
        }
    }

    protected abstract void startProcess() throws Exception;

    @Override
    public void abort() throws SetupStepHandlerException {
        try {
            abortProcess();
        } catch (Exception ex) {
            throw new SetupStepHandlerException(ex);
        }
    }

    protected abstract void abortProcess() throws Exception;

}
