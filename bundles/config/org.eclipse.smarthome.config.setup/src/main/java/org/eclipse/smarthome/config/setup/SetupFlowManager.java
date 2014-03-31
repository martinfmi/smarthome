package org.eclipse.smarthome.config.setup;

import org.eclipse.smarthome.config.setup.flow.SetupFlow;
import org.eclipse.smarthome.config.setup.handler.SetupStepHandler;


public interface SetupFlowManager {

    SetupFlow getSetupFlow(String thingType);

    boolean registerSetupStepHandler(SetupStepHandlerId stepHandlerId, SetupStepHandler stepHandler)
            throws IllegalArgumentException;

    boolean unregisterSetupStepHandler(SetupStepHandlerId stepHandlerId)
            throws IllegalArgumentException;

    SetupStepProcess getSetupStepProcess(SetupStepHandlerId stepHandlerId);

}
