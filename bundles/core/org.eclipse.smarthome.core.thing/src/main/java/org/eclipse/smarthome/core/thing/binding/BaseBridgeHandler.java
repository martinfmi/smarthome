package org.eclipse.smarthome.core.thing.binding;

import java.util.List;

import org.eclipse.smarthome.core.thing.Bridge;
import org.eclipse.smarthome.core.thing.Thing;


public abstract class BaseBridgeHandler<T extends ThingConfiguration> extends BaseThingHandler<T> {

    public BaseBridgeHandler(T configuration) {
        super(configuration);
    }

    public Thing getThingById(String id) {

        Bridge bridge = getThing();

        List<Thing> things = bridge.getThings();

        for (Thing thing : things) {
            if (thing.getId().equals(id)) {
                return thing;
            }
        }
        
        return null;
    }

    @Override
    public Bridge getThing() {
        return (Bridge) super.getThing();
    }
}