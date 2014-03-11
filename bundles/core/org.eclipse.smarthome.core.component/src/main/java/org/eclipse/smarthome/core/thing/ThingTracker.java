package org.eclipse.smarthome.core.thing;


public interface ThingTracker {

    void thingAdded(Thing thing);

    void thingUpdated(Thing thing);

    void thingRemoved(Thing thing);

}
