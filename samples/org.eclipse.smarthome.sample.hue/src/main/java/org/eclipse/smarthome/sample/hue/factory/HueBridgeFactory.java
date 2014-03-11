package org.eclipse.smarthome.sample.hue.factory;

import java.util.Dictionary;

import org.eclipse.smarthome.core.thing.binding.BaseThingHandlerFactory;
import org.eclipse.smarthome.core.thing.binding.ThingConfiguration;
import org.eclipse.smarthome.sample.hue.handler.HueBridgeHandler;
import org.eclipse.smarthome.sample.hue.handler.HueBridgeHandler.HueBridgeConfiguration;


public class HueBridgeFactory extends BaseThingHandlerFactory<HueBridgeHandler, HueBridgeConfiguration> {

    protected HueBridgeHandler createThingHandler(HueBridgeConfiguration configuration) {
        return new HueBridgeHandler(configuration);
    }

    @Override
    protected HueBridgeConfiguration createConfiguration(Dictionary<String, Object> configuration) {
        return ThingConfiguration.create(HueBridgeConfiguration.class, configuration);
    }

    @Override
    protected void removeThingHandler(HueBridgeHandler hueBridge) {
        // nothing to do
    }

}
