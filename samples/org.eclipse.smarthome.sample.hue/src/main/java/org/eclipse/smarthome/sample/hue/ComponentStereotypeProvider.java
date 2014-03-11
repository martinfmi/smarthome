package org.eclipse.smarthome.sample.hue;

import java.util.List;

import org.eclipse.smarthome.core.thing.Bridge;
import org.eclipse.smarthome.core.thing.Thing;

public interface ComponentStereotypeProvider {

    Bridge createBridge(String stereoType);

    Thing createThing(Bridge bridge, String stereoType);

    List<String> getSupportedBridgeStereoTypes();

    List<String> getSupportedThingStereoTypes();
}
