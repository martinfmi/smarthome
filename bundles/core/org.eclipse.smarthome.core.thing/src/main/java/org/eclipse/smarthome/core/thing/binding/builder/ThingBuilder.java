package org.eclipse.smarthome.core.thing.binding.builder;

import org.eclipse.smarthome.core.thing.internal.ThingImpl;

public class ThingBuilder extends GenericThingBuilder<ThingBuilder> {

    private ThingBuilder(ThingImpl thing) {
        super(thing);
    }

    public static ThingBuilder create(String factoryPid, String id) {
        ThingImpl thing = new ThingImpl(factoryPid, id);
        return new ThingBuilder(thing);
    }

}
