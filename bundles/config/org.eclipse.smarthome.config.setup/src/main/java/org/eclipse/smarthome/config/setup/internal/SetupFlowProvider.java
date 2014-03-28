package org.eclipse.smarthome.config.setup.internal;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.smarthome.config.setup.flow.SetupFlow;
import org.eclipse.smarthome.config.setup.flow.ThingTypes;


public class SetupFlowProvider {

    private List<SetupFlow> setupFlows;

    public SetupFlowProvider() {
        this.setupFlows = new ArrayList<>();
    }

    SetupFlow getSetupFlow(String thingType) {
        for (SetupFlow setupFlow : setupFlows) {
            ThingTypes thingTypes = setupFlow.getThingTypes();
        }

        return null;
    }

}
