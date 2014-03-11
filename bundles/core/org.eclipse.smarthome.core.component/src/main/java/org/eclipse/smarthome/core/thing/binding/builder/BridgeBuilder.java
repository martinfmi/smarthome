package org.eclipse.smarthome.core.thing.binding.builder;

import org.eclipse.smarthome.core.thing.internal.BridgeImpl;

public class BridgeBuilder extends GenericBridgeBuilder<BridgeBuilder> {

    private BridgeBuilder(BridgeImpl thing) {
        super(thing);
    }

    public static BridgeBuilder create(String factoryPid, String id) {
        BridgeImpl bridge = new BridgeImpl(factoryPid, id);
        bridge.setId(id);
        return new BridgeBuilder(bridge);
    }

}
