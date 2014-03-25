package org.eclipse.smarthome.core.thing.internal;

import java.util.List;

import org.eclipse.smarthome.core.thing.Bridge;
import org.eclipse.smarthome.core.thing.Thing;


public class BridgeImpl extends ThingImpl implements Bridge {

    public BridgeImpl(String factoryPid, String id) {
        super(factoryPid, id);
    }

    @Override
    public List<Thing> getThings() {
        // TODO Auto-generated method stub
        return null;
    }

}
