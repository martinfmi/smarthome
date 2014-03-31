package org.eclipse.smarthome.core.thing.internal;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.eclipse.smarthome.core.thing.Bridge;
import org.eclipse.smarthome.core.thing.Thing;
import org.eclipse.smarthome.core.thing.ThingStatus;

public class BridgeImpl extends ThingImpl implements Bridge {

    private List<Thing> things = new CopyOnWriteArrayList<>();

    public BridgeImpl(String factoryPid, String id) {
        super(factoryPid, id);
    }

    public void addThing(Thing thing) {
        things.add(thing);
        if (thing.getBridge() == null || !thing.getBridge().getId().equals(this.getId())) {
            thing.setBridge(this);
        }
    }

    public void removeThing(Thing thing) {
        things.remove(thing);
        if (thing.getBridge() != null) {
            thing.setBridge(null);
        }
    }

    @Override
    public List<Thing> getThings() {
        return things;
    }

    @Override
    public void setStatus(ThingStatus status) {
        super.setStatus(status);
        for (Thing thing : this.things) {
            thing.setStatus(status);
        }
    }

}
