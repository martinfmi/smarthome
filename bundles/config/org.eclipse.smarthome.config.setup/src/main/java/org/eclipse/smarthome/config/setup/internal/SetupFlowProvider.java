package org.eclipse.smarthome.config.setup.internal;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.smarthome.config.setup.flow.SetupFlow;
import org.eclipse.smarthome.config.setup.flow.SetupFlows;
import org.eclipse.smarthome.config.setup.flow.ThingTypes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class SetupFlowProvider {

    private Logger logger = LoggerFactory.getLogger(SetupFlowProvider.class);

    private String moduleName;
    private List<SetupFlow> setupFlows;


    public SetupFlowProvider(String moduleName) {
        this.moduleName = (moduleName != null) ? moduleName : "undefined";
        this.setupFlows = new ArrayList<>();
    }

    public final SetupFlow getSetupFlow(String thingType) {
        if (thingType != null) {
            for (SetupFlow setupFlow : setupFlows) {
                ThingTypes thingTypes = setupFlow.getThingTypes();

                if (thingTypes != null) {
                    List<String> types = thingTypes.getThingTypes();
                    for (String type : types) {
                        try {
                            if (thingType.matches(type)) {
                                return setupFlow;
                            }
                        } catch (Exception ex) {
                            logger.error("The regular expression '" + type + "' within the module '"
                                    + this.moduleName + "' is invalid!", ex);
                        }
                    }
                }
            }
        }

        return null;
    }

    public final void addSetupFlows(SetupFlows setupFlows) {
        if ((setupFlows != null) && (setupFlows.getSetupFlows() != null)) {
            for (SetupFlow setupFlow : setupFlows.getSetupFlows()) {
                addSetupFlow(setupFlow);
            }
        }
    }

    public final void addSetupFlow(SetupFlow setupFlow) {
        if ((setupFlow != null) && (!this.setupFlows.contains(setupFlow))) {
            this.setupFlows.add(setupFlow);
        }
    }

    @Override
    public final String toString() {
        StringBuilder textBuffer = new StringBuilder();

        textBuffer.append("SetupFlowProvider [moduleName=").append(this.moduleName);

        if (this.setupFlows.size() > 0) {
            textBuffer.append(", setupFlows=");

            for (int index = 0; index < this.setupFlows.size(); index++) {
                SetupFlow setupFlow = this.setupFlows.get(index);
                textBuffer.append(setupFlow.getId());

                if (index < this.setupFlows.size() - 1) {
                    textBuffer.append(", ");
                }
            }
        }

        textBuffer.append("]");

        return textBuffer.toString();
    }

}
