package org.eclipse.smarthome.config.setup;


public class SetupStepHandlerId {

    private String bindingId;
    private String thingId;
    private String flowId;
    private String stepId;


    public SetupStepHandlerId(String bindingId, String thingId, String flowId, String stepId) {
        this.bindingId = bindingId;
        this.thingId = thingId;
        this.flowId = flowId;
        this.stepId = stepId;
    }

    public String getBindingId() {
        return bindingId;
    }

    public String getThingId() {
        return thingId;
    }

    public String getFlowId() {
        return flowId;
    }

    public String getStepId() {
        return stepId;
    }

}
