package org.eclipse.smarthome.binding.hue.handler;

import org.eclipse.smarthome.core.library.types.DecimalType;
import org.eclipse.smarthome.core.library.types.HSBType;
import org.eclipse.smarthome.core.library.types.OnOffType;
import org.eclipse.smarthome.core.library.types.PercentType;
import org.eclipse.smarthome.core.thing.Bridge;
import org.eclipse.smarthome.core.thing.Channel;
import org.eclipse.smarthome.core.thing.Thing;
import org.eclipse.smarthome.core.thing.binding.BaseThingHandler;
import org.eclipse.smarthome.core.thing.binding.ThingConfiguration;
import org.eclipse.smarthome.core.thing.binding.ThingHandler;
import org.eclipse.smarthome.core.types.Command;
import org.eclipse.smarthome.core.types.State;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.philips.lighting.model.PHLight.PHLightColorMode;
import com.philips.lighting.model.PHLightState;
import org.eclipse.smarthome.binding.hue.handler.HueLampHandler.HueLampConfiguration;

/**
 * {@link HueLampHandler} is the handler for a HUE lamp. It uses the
 * {@link HueBridgeHandler} to execute the actual command.
 * 
 * @author Dennis Nobel - Initial contribution of HUE binding
 * 
 */
public class HueLampHandler extends BaseThingHandler<HueLampConfiguration> {

    public static class HueLampConfiguration extends ThingConfiguration {
        public Integer lampId;
    }

    /**
     * ID of the color channel
     */
    public static final String CHANNEL_ID_COLOR = "color";

    /**
     * ID of the color temperature channel
     */
    public static final String CHANNEL_ID_COLOR_TEMPERATURE = "color_temperature";

    /**
     * Prefix for all HUE lamp IDs
     */
    public static final String HUE_LAMP_ID_PREFIX = "hue:lamp";

    private static final double COLOR_TEMPERATURE_FACTOR = 3.46;
    private static final int COLOR_TEMPERATURE_OFFSET = 154;
    private static final int HUE_FACTOR = 182;
    private static final double SATURATION_AND_BRIGHTNESS_FACTOR = 2.54;

    private Logger logger = LoggerFactory.getLogger(HueLampHandler.class.getName());

    public HueLampHandler(HueLampConfiguration configuration) {
        super(configuration);
    }

    @Override
    public void handleCommand(Channel channel, Command command) {

        HueBridgeHandler hueBridge = getHueBridgeHandler();
        if (hueBridge == null) {
            logger.warn("HUE bridge handler not found. Cannot handle command without bridge.");
            return;
        }

        String lampIdentifier = String.valueOf(getConfiguration().lampId);

        switch (channel.getId()) {

        case CHANNEL_ID_COLOR_TEMPERATURE:
            if (command instanceof PercentType) {
                PHLightState lightState = toColorTemperatureLightState((PercentType) command);
                hueBridge.updateLightState(lampIdentifier, lightState);
            } else if (command instanceof OnOffType) {
                PercentType percentType = command == OnOffType.ON ? PercentType.HUNDRED : PercentType.ZERO;
                PHLightState lightState = toColorTemperatureLightState(percentType);
                hueBridge.updateLightState(lampIdentifier, lightState);
            }
            // TODO: support increase and decrease
            break;
        case CHANNEL_ID_COLOR:
            if (command instanceof HSBType) {
                PHLightState lightState = toColorLightState((HSBType) command);
                hueBridge.updateLightState(lampIdentifier, lightState);
            } else if (command instanceof PercentType) {
                PHLightState lightState = toColorLightState((PercentType) command);
                hueBridge.updateLightState(lampIdentifier, lightState);
            } else if (command instanceof OnOffType) {
                PHLightState lightState = toColorLightState((OnOffType) command);
                hueBridge.updateLightState(lampIdentifier, lightState);
            }
            // TODO: support increase and decrease
            break;
        default:
            logger.warn("Command send to an unknown channel id: " + channel.getId());
            break;
        }

    }

    @Override
    public void handleUpdate(Channel channel, State newState) {
        // nothing to do
    }

    /**
     * This method can be called by other handlers when the light state of the
     * according HUE lamp has been changed.
     * 
     * @param lightState
     *            new light state of the HUE lamp
     */
    public void onLightStateChanged(PHLightState lightState) {

        HSBType hsbType = toHSBType(lightState);
        updateState(CHANNEL_ID_COLOR, hsbType);

        PercentType percentType = toColorTemperaturePercentType(lightState);
        updateState(CHANNEL_ID_COLOR_TEMPERATURE, percentType);
    }

    private HueBridgeHandler getHueBridgeHandler() {
        Bridge bridge = getThing().getBridge();
        if (bridge == null) {
            return null;
        }
        ThingHandler<?> handler = bridge.getHandler();
        if (handler instanceof HueBridgeHandler) {
            return (HueBridgeHandler) handler;
        } else {
            return null;
        }
    }

    /**
     * Transforms the given {@link HSBType} into a light state.
     * 
     * @param hsbType
     *            HSB type
     * @return light state representing the {@link HSBType}.
     */
    private PHLightState toColorLightState(HSBType hsbType) {

        PHLightState lightState = new PHLightState();

        lightState.setColorMode(PHLightColorMode.COLORMODE_HUE_SATURATION);

        int newHueCalculated = new Long(Math.round(hsbType.getHue().doubleValue() * 182)).intValue();
        int newSaturationCalculated = new Long(Math.round(hsbType.getSaturation().doubleValue() * 2.54)).intValue();
        int newBrightnessCalculated = new Long(Math.round(hsbType.getBrightness().doubleValue() * 2.54)).intValue();

        lightState.setHue(newHueCalculated);
        lightState.setSaturation(newSaturationCalculated);
        lightState.setBrightness(newBrightnessCalculated);

        return lightState;
    }

    /**
     * Transforms the given {@link OnOffType} into a light state containing the
     * 'on' value.
     * 
     * @param onOffType
     *            on or off state
     * @return light state containing the 'on' value
     */
    private PHLightState toColorLightState(OnOffType onOffType) {
        PHLightState lightState = new PHLightState();

        lightState.setOn(OnOffType.ON.equals(onOffType));

        return lightState;
    }

    /**
     * Transforms the given {@link PercentType} into a light state containing
     * the brightness and the 'on' value represented by {@link PercentType}.
     * 
     * @param percentType
     *            brightness represented as {@link PercentType}
     * @return light state containing the brightness and the 'on' value
     */
    private PHLightState toColorLightState(PercentType percentType) {
        PHLightState lightState = new PHLightState();

        lightState.setOn(percentType.equals(PercentType.ZERO) ? false : true);

        double brightness = percentType.doubleValue();

        int newBrightnessCalculated = new Long(Math.round(brightness * 2.54)).intValue();

        lightState.setBrightness(newBrightnessCalculated);

        return lightState;
    }

    /**
     * Transforms the given {@link PercentType} into a light state containing
     * the color temperature represented by {@link PercentType}.
     * 
     * @param percentType
     *            color temperature represented as {@link PercentType}
     * @return light state containing the color temperature
     */
    private PHLightState toColorTemperatureLightState(PercentType percentType) {
        PHLightState lightState = new PHLightState();
        lightState.setCt(COLOR_TEMPERATURE_OFFSET + (int) (COLOR_TEMPERATURE_FACTOR * percentType.intValue()));
        return lightState;
    }

    /**
     * Transforms {@link PHLightState} into {@link PercentType} representing the
     * color temperature.
     * 
     * @param lightState
     *            light state
     * @return percent type representing the color temperature
     */
    private PercentType toColorTemperaturePercentType(PHLightState lightState) {
        int percent = (int) ((lightState.getCt() - COLOR_TEMPERATURE_OFFSET) / COLOR_TEMPERATURE_FACTOR);
        return new PercentType(percent);
    }

    /**
     * Transforms {@link PHLightState} into {@link HSBType} representing the
     * color.
     * 
     * @param lightState
     *            light state
     * @return HSB type representing the color
     */
    private HSBType toHSBType(PHLightState lightState) {

        int hue = lightState.getHue();
        int saturation = lightState.getSaturation();
        int brightness = lightState.getBrightness();

        int saturationInPercent = (int) (saturation / SATURATION_AND_BRIGHTNESS_FACTOR);
        int brightnessInPercent = (int) (brightness / SATURATION_AND_BRIGHTNESS_FACTOR);

        HSBType hsbType = new HSBType(new DecimalType(hue / HUE_FACTOR), new PercentType(saturationInPercent),
                new PercentType(brightnessInPercent));

        return hsbType;
    }

    @Override
    protected void initialize(Thing component, HueLampConfiguration configuration) {
    }

}
