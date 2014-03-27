package org.eclipse.smarthome.config.setup;

import org.eclipse.smarthome.config.setup.flow.SetupFlow;


public interface SetupFlowProvider {

    SetupFlow getFlow(String flowId);

}
