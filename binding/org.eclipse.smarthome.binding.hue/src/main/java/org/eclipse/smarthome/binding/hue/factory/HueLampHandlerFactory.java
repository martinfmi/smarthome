package org.eclipse.smarthome.binding.hue.factory;

import java.util.Dictionary;

import org.eclipse.smarthome.config.core.Configuration;
import org.eclipse.smarthome.core.thing.binding.BaseThingHandlerFactory;

import org.eclipse.smarthome.binding.hue.handler.HueLampHandler;
import org.eclipse.smarthome.binding.hue.handler.HueLampHandler.HueLampConfiguration;

/**
 * {@link HueBridgeHandlerFactory} is a factory for {@link HueLampHandler}s.
 * 
 * @author Dennis Nobel - Initial contribution of HUE binding
 * 
 */
public class HueLampHandlerFactory extends BaseThingHandlerFactory<HueLampHandler, HueLampConfiguration> {

    @Override
    protected HueLampHandler createThingHandler(HueLampConfiguration configuration) {
        HueLampHandler hueLampHandler = new HueLampHandler(configuration);
        return hueLampHandler;
    }

    @Override
    protected HueLampConfiguration createConfiguration(Dictionary<String, Object> configuration) {
        return Configuration.create(HueLampConfiguration.class, configuration);
    }

    @Override
    protected void removeThingHandler(HueLampHandler componentHandler) {
        // nothing to do
    }

}

