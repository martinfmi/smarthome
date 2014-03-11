package org.eclipse.smarthome.sample.hue.handler;

import org.eclipse.smarthome.core.library.types.HSBType;
import org.eclipse.smarthome.core.library.types.PercentType;
import org.eclipse.smarthome.core.thing.Channel;
import org.eclipse.smarthome.core.thing.Thing;
import org.eclipse.smarthome.core.thing.binding.BaseThingHandler;
import org.eclipse.smarthome.core.thing.binding.ThingConfiguration;
import org.eclipse.smarthome.core.thing.binding.ThingHandler;
import org.eclipse.smarthome.core.types.Command;
import org.eclipse.smarthome.sample.hue.handler.HueLampHandler.HueLampConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * {@link HueLampHandler} is the handler for a HUE lamp. It uses the
 * {@link HueBridgeHandler} to execute the actual command.
 * 
 * @author Dennis Nobel - Initial contribution of sample
 * 
 */
public class HueLampHandler extends BaseThingHandler<HueLampConfiguration> {

    private static final String CHANNEL_ID_DIMMER = "dimmer";

    private Logger logger = LoggerFactory.getLogger(HueLampHandler.class.getName());

    public static class HueLampConfiguration extends ThingConfiguration {
        public String bridgeId;
        public Integer lampId;
    }

    public HueLampHandler(HueLampConfiguration configuration) {
        super(configuration);
    }

    @Override
    protected void initialize(Thing component, HueLampConfiguration configuration) {
    }

    @Override
    public void handleCommand(Channel channel, Command command) {

        HueBridgeHandler hueBridge = getHueBridgeHandler();
        if (hueBridge == null) {
            logger.warn("Cannot handle command without bridge.");
            return;
        }

        switch (channel.getId()) {

        case CHANNEL_ID_DIMMER:
            if ((command instanceof PercentType) && !(command instanceof HSBType)) {
                PercentType percentType = (PercentType) command;
                hueBridge.setLampBrightness(getConfiguration().lampId, percentType.intValue());
            }
            break;

        default:
            // command is sent to an unknown channel - nothing to do
            break;
        }

    }

    private HueBridgeHandler getHueBridgeHandler() {
        ThingHandler<?> handler = this.getBridgeHandler();
        if (handler instanceof HueBridgeHandler) {
            return (HueBridgeHandler) handler;
        } else {
            return null;
        }
    }

}
