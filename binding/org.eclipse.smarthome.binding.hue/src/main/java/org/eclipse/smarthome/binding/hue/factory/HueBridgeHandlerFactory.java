package org.eclipse.smarthome.binding.hue.factory;

import java.util.Dictionary;

import org.eclipse.smarthome.core.thing.binding.BaseThingHandlerFactory;
import org.eclipse.smarthome.core.thing.binding.ThingConfiguration;

import org.eclipse.smarthome.binding.hue.handler.HueBridgeHandler;
import org.eclipse.smarthome.binding.hue.handler.HueBridgeHandler.HueBridgeConfiguration;

/**
 * {@link HueBridgeHandlerFactory} is a factory for {@link HueBridgeHandler}s.
 * 
 * @author Dennis Nobel - Initial contribution of HUE binding
 * 
 */
public class HueBridgeHandlerFactory extends BaseThingHandlerFactory<HueBridgeHandler, HueBridgeConfiguration> {

    @Override
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
