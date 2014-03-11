package org.eclipse.smarthome.core.thing.binding.builder;

import org.eclipse.smarthome.core.thing.Bridge;
import org.eclipse.smarthome.core.thing.internal.BridgeImpl;

public class GenericBridgeBuilder<T extends GenericBridgeBuilder<T>> extends GenericThingBuilder<T> {

    protected GenericBridgeBuilder(BridgeImpl bridge) {
        super(bridge);
    }

    public Bridge build() {
        return (Bridge) super.build();
    }


}
