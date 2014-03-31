package org.eclipse.smarthome.core.thing.binding;

import org.eclipse.smarthome.core.thing.Channel;
import org.eclipse.smarthome.core.thing.Thing;
import org.eclipse.smarthome.core.thing.ThingStatus;
import org.eclipse.smarthome.core.types.State;


public abstract class BaseThingHandler<T extends ThingConfiguration> implements ThingHandler<T> {
    private ThingHandler<?> bridgeHandler;
    private T configuration;

    private Thing thing;

    public BaseThingHandler(T configuration) {
        this.configuration = configuration;
    }

    @Override
    public ThingHandler<?> getBridgeHandler() {
        return this.bridgeHandler;
    }

    public T getConfiguration() {
        return this.configuration;
    }

    public Thing getThing() {
        return this.thing;
    }

    @Override
    public void handleUpdate(Channel channel, State newState) {
        // TODO Auto-generated method stub
    }

    public void setBridgeHandler(ThingHandler<?> bridgeHandler) {
        this.bridgeHandler = bridgeHandler;
    }

    @Override
    public void setThing(Thing thing) {
        this.thing = thing;
        if (this.thing != null) {
            initialize(thing, getConfiguration());
        }
    }

    protected void updateStatus(ThingStatus status) {
        if (thing != null && thing.getStatus() != status) {
            thing.setStatus(status);
        }
    }

    protected void updateState(String channelId, State state) {
        if (thing != null) {
            thing.channelUpdated(channelId, state);
        }
    }

    @Override
    public void updated(T configuration) {
        this.configuration = configuration;
    }

    protected void initialize(Thing thing, T configuration) {

    }

    protected void dispose() {

    }


}