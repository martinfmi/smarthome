package org.eclipse.smarthome.sample.hue.factory;

import java.util.Dictionary;

import org.eclipse.smarthome.config.core.Configuration;
import org.eclipse.smarthome.core.thing.binding.BaseThingHandlerFactory;
import org.eclipse.smarthome.sample.hue.handler.HueLampHandler;
import org.eclipse.smarthome.sample.hue.handler.HueLampHandler.HueLampConfiguration;

/**
 * {@link HueBridgeHandlerFactory} is a factory for {@link HueLampHandler}s.
 * 
 * @author Dennis Nobel - Initial contribution of sample
 */
public class HueLampHandlerFactory extends BaseThingHandlerFactory<HueLampHandler, HueLampConfiguration> {

    @Override
    protected HueLampHandler createThingHandler(HueLampConfiguration configuration) {
        return new HueLampHandler(configuration);
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
