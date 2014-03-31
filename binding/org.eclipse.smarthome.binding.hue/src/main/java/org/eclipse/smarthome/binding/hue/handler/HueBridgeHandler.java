package org.eclipse.smarthome.binding.hue.handler;

import java.util.List;

import org.eclipse.smarthome.config.core.annotation.Default;
import org.eclipse.smarthome.core.thing.Channel;
import org.eclipse.smarthome.core.thing.Thing;
import org.eclipse.smarthome.core.thing.ThingStatus;
import org.eclipse.smarthome.core.thing.binding.BaseBridgeHandler;
import org.eclipse.smarthome.core.thing.binding.ThingConfiguration;
import org.eclipse.smarthome.core.types.Command;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.philips.lighting.hue.sdk.PHAccessPoint;
import com.philips.lighting.hue.sdk.PHHueSDK;
import com.philips.lighting.hue.sdk.PHSDKListener;
import com.philips.lighting.model.PHBridge;
import com.philips.lighting.model.PHLight;
import com.philips.lighting.model.PHLightState;
import org.eclipse.smarthome.binding.hue.handler.HueBridgeHandler.HueBridgeConfiguration;

/**
 * {@link HueBridgeHandler} is the handler for a HUE bridge and connects it to
 * the framework. All {@link HueLampHandler}s use the {@link HueBridgeHandler}
 * to execute the actual commands.
 * 
 * @author Dennis Nobel - Initial contribution of HUE binding
 * 
 */
public class HueBridgeHandler extends BaseBridgeHandler<HueBridgeConfiguration> implements PHSDKListener {

    public static class HueBridgeConfiguration extends ThingConfiguration {
        public String ipAddress;
        @Default("ESH")
        public String secret;
    }

    private PHAccessPoint accessPoint;

    private PHHueSDK hueSDK;
    private Logger logger = LoggerFactory.getLogger(HueBridgeHandler.class.getName());

    public HueBridgeHandler(HueBridgeConfiguration configuration) {
        super(configuration);
    }

    @Override
    public void handleCommand(Channel componentPort, Command command) {
        // not needed
    }

    @Override
    public void onAccessPointsFound(List<PHAccessPoint> accessPoints) {
        logger.info("Access point found");
    }

    @Override
    public void onAuthenticationRequired(PHAccessPoint accessPoint) {
        logger.info("Authentication required");
    }

    @Override
    public void onBridgeConnected(PHBridge bridge) {
        logger.info("Bridge connected. Updating thing status to ONLINE.");
        hueSDK.setSelectedBridge(bridge);
        hueSDK.enableHeartbeat(bridge, PHHueSDK.HB_INTERVAL);
        updateStatus(ThingStatus.ONLINE);
    }

    @Override
    public void onCacheUpdated(int flags, PHBridge bridge) {
        logger.info("Cache updated.");

        List<PHLight> allLights = bridge.getResourceCache().getAllLights();

        for (PHLight light : allLights) {

            String identifier = light.getIdentifier();
            Thing hueLamp = getThingById(HueLampHandler.HUE_LAMP_ID_PREFIX + identifier);
            if (hueLamp != null) {
                HueLampHandler hueLampThingHandler = (HueLampHandler) hueLamp.getHandler();
                if (hueLampThingHandler != null) {
                    PHLightState lightState = light.getLastKnownLightState();
                    hueLampThingHandler.onLightStateChanged(lightState);
                } else {
                    logger.warn("Handler for thing with ID '{}' is null. Cannot update light state.", hueLamp.getId());
                }
            } else {
                logger.warn("Received an update for a HUE lamp with ID '{}', but could not found according thing.",
                        identifier);
            }
        }

    }

    @Override
    public void onConnectionLost(PHAccessPoint accessPoints) {
        logger.info("Bridge connection lost. Updating thing status to OFFLINE.");
        updateStatus(ThingStatus.OFFLINE);
    }

    @Override
    public void onConnectionResumed(PHBridge bridge) {
        logger.info("Bridge connection resumed. Updating thing status to ONLINE.");
        updateStatus(ThingStatus.ONLINE);
    }

    @Override
    public void onError(int code, String message) {
        logger.error("Error received: {} - {}.", code, message);
    }

    public void updateLightState(String lightIdentifier, PHLightState lightState) {
        PHBridge bridge = hueSDK.getSelectedBridge();
        if (bridge != null) {
            bridge.updateLightState(lightIdentifier, lightState, null);
        } else {
            logger.warn("No bridge connected or selected. Cannot set light state.");
        }
    }

    @Override
    protected void dispose() {
        logger.debug("Handler disposes. Unregistering listener.");
        hueSDK.disableAllHeartbeat();
        hueSDK.getNotificationManager().unregisterSDKListener(this);
    }

    @Override
    protected void initialize(Thing thing, HueBridgeConfiguration configuration) {
        logger.debug("Initializing HUE bridge handler.");
        if (configuration.ipAddress != null && configuration.secret != null) {
            hueSDK = PHHueSDK.create();
            accessPoint = new PHAccessPoint();
            accessPoint.setIpAddress(configuration.ipAddress);
            accessPoint.setUsername(configuration.secret);
            if (hueSDK.getSelectedBridge() == null) {
                hueSDK.connect(accessPoint);
                hueSDK.getNotificationManager().registerSDKListener(this);
            }
        } else {
            logger.warn("Cannot connect to HUE bridge. IP address or secret not set.");
        }
    }

}
