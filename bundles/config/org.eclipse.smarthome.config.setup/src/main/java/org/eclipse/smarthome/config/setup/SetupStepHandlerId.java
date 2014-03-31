package org.eclipse.smarthome.config.setup;

import java.net.URI;
import java.net.URISyntaxException;


public class SetupStepHandlerId {

    private String flowId;
    private String bindingId;
    private String stepId;
    private String thingId;


    public SetupStepHandlerId(String flowId, String bindingId, String stepId, String thingId) {
        this.flowId = flowId;
        this.bindingId = bindingId;
        this.stepId = stepId;
        this.thingId = thingId;
    }

    public final String getFlowId() {
        return flowId;
    }

    public final String getBindingId() {
        return bindingId;
    }

    public final String getStepId() {
        return stepId;
    }

    public final String getThingId() {
        return thingId;
    }

    public final URI toURI() {
        try {
            return new URI("setup-flow", this.flowId, "/" + this.bindingId + "/" + stepId, thingId);
        } catch (URISyntaxException use) {
            return null;
        }

    }

    @Override
    public final String toString() {
        return "SetupStepHandlerId [flowId=" + flowId + ", bindingId="
                + bindingId + ", stepId=" + stepId + ", thingId=" + thingId
                + "]";
    }

}
