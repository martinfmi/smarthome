package org.eclipse.smarthome.config.setup;


public interface SetupStepProcess {

    void start() throws SetupStepHandlerException;

    void abort() throws SetupStepHandlerException;

}
