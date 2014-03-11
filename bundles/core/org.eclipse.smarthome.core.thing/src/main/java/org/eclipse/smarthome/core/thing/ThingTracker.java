package org.eclipse.smarthome.core.thing;


/**
 * A {@link ThingTracker} can be used to track added, removed or updated things.
 */
public interface ThingTracker {

    void thingAdded(Thing thing);

    void thingUpdated(Thing thing);

    void thingRemoved(Thing thing);

}
