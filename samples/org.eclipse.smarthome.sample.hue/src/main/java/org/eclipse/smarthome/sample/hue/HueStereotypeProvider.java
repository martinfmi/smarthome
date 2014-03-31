package org.eclipse.smarthome.sample.hue;

import java.util.List;

import org.eclipse.smarthome.core.library.items.ColorItem;
import org.eclipse.smarthome.core.library.items.DimmerItem;
import org.eclipse.smarthome.core.library.items.SwitchItem;
import org.eclipse.smarthome.core.thing.Bridge;
import org.eclipse.smarthome.core.thing.Channel;
import org.eclipse.smarthome.core.thing.Thing;
import org.eclipse.smarthome.core.thing.binding.builder.BridgeBuilder;
import org.eclipse.smarthome.core.thing.binding.builder.ChannelBuilder;
import org.eclipse.smarthome.core.thing.binding.builder.ThingBuilder;
import org.eclipse.smarthome.sample.hue.handler.HueBridgeHandler.HueBridgeConfiguration;
import org.eclipse.smarthome.sample.hue.handler.HueLampHandler.HueLampConfiguration;

import com.google.common.collect.Lists;

public class HueStereotypeProvider {

    private static final String STEREO_TYPE_BRIDGE = "BRIDGE";
    private static final String STEREO_TYPE_LAMP = "LAMP";


    public Bridge createBridge(String stereoType) {
        return createHueBridge();
    }


    public Thing createThing(Bridge bridge, String stereoType) {
        switch (stereoType) {
        case STEREO_TYPE_LAMP:
            return createHueLamp();
        default:
            return null;
        }
    }


    public List<String> getSupportedBridgeStereoTypes() {
        return Lists.newArrayList(STEREO_TYPE_BRIDGE);
    }


    public List<String> getSupportedThingStereoTypes() {
        return Lists.newArrayList(STEREO_TYPE_LAMP);
    }

    private Bridge createHueBridge() {
        return BridgeBuilder.create("hue.bridge", "hue:bridge1").withConfiguration(createHueBridgeConfiguration())
                .build();
    }

    private HueBridgeConfiguration createHueBridgeConfiguration() {
        HueBridgeConfiguration hueBridgeConfiguration = new HueBridgeConfiguration();
        hueBridgeConfiguration.ipAddress = "localhost:8080";
        hueBridgeConfiguration.secret = "newdeveloper";
        hueBridgeConfiguration.id = "bridge1";
        return hueBridgeConfiguration;
    }

    private Thing createHueLamp() {

        Channel colorChannel = ChannelBuilder.create("color", ColorItem.class).build();
        Channel dimmerChannel = ChannelBuilder.create("dimmer", DimmerItem.class).build();
        Channel switchChannel = ChannelBuilder.create("switch", SwitchItem.class).build();

        return ThingBuilder.create("hue.lamp", "1").withConfiguration(createHueLampConfiguration())
                .withChannels(colorChannel, dimmerChannel, switchChannel).build();
    }

    private HueLampConfiguration createHueLampConfiguration() {
        HueLampConfiguration hueLampConfiguration = new HueLampConfiguration();
        hueLampConfiguration.bridgeId = "bridge1";
        hueLampConfiguration.lampId = 1;
        return hueLampConfiguration;
    }
}
