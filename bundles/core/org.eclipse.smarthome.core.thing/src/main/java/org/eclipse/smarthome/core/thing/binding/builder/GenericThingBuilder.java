package org.eclipse.smarthome.core.thing.binding.builder;

import org.eclipse.smarthome.core.thing.Bridge;
import org.eclipse.smarthome.core.thing.Channel;
import org.eclipse.smarthome.core.thing.Thing;
import org.eclipse.smarthome.core.thing.binding.ThingConfiguration;
import org.eclipse.smarthome.core.thing.internal.ThingImpl;

import com.google.common.collect.Lists;

public class GenericThingBuilder<T extends GenericThingBuilder<T>> {

    private ThingImpl thing;

    protected GenericThingBuilder(ThingImpl thing) {
        this.thing = thing;
    }

    public T withChannels(Channel... channels) {
        this.thing.setChannels(Lists.newArrayList(channels));
        return self();
    }
    
    public T withConfiguration(ThingConfiguration thingConfiguration) {
        this.thing.setConfiguration(thingConfiguration);
        return self();
    }

    public T withBridge(Bridge bridge) {
        this.thing.setBridge(bridge);
        return self();
    }

    public Thing build() {
        return this.thing;
    }

    @SuppressWarnings("unchecked")
    protected T self() {
        return (T) this;
    }

}
