package org.eclipse.smarthome.config.setup;

import org.eclipse.smarthome.config.setup.flow.SetupFlow;
import org.eclipse.smarthome.config.setup.handler.SetupStepHandler;


public interface SetupFlowManager {

    SetupFlow getSetupFlow(String thingType);

    boolean registerSetupStepHandler(
            SetupStepHandlerId stepHandlerId, SetupStepHandler stepHandler);

    boolean unregisterSetupStepHandler(
            SetupStepHandlerId stepHandlerId, SetupStepHandler stepHandler);

    SetupStepProcess getSetupStepProcess(SetupStepHandlerId stepHandlerId);

}
