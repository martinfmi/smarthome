package org.eclipse.smarthome.core.thing;


/**
 * A {@link ThingTracker} can be used to track added, removed or updated things.
 */
public interface ThingTracker {

    public enum ThingTrackerEvent {
        THING_ADDED, THING_REMOVED, TRACKER_ADDED, TRACKER_REMOVED
    }

    void thingAdded(Thing thing, ThingTrackerEvent thingTrackerEvent);

    void thingUpdated(Thing thing);

    void thingRemoved(Thing thing, ThingTrackerEvent thingTrackerEvent);

}
