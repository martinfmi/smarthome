/**
 * Copyright (c) 2010-2013, openHAB.org and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.eclipse.smarthome.binding.hue.internal;

import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.smarthome.binding.hue.handler.HueBridgeHandler;
import org.eclipse.smarthome.binding.hue.handler.HueLampHandler;
import org.eclipse.smarthome.core.items.Item;
import org.eclipse.smarthome.core.items.ItemProvider;
import org.eclipse.smarthome.core.items.ItemsChangeListener;
import org.eclipse.smarthome.core.library.items.ColorItem;
import org.eclipse.smarthome.core.library.items.DimmerItem;
import org.eclipse.smarthome.core.thing.Bridge;
import org.eclipse.smarthome.core.thing.Channel;
import org.eclipse.smarthome.core.thing.ItemChannelBindingRegistry;
import org.eclipse.smarthome.core.thing.Thing;
import org.eclipse.smarthome.core.thing.ThingRegistry;
import org.eclipse.smarthome.core.thing.binding.builder.BridgeBuilder;
import org.eclipse.smarthome.core.thing.binding.builder.ChannelBuilder;
import org.eclipse.smarthome.core.thing.binding.builder.ThingBuilder;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;

import com.google.common.collect.Lists;

/**
 * This activator sets up the needed configuration for a HUE bridge with one
 * lamp.
 * 
 * @author Dennis Nobel - Initial contribution of HUE binding
 */
public final class HueActivator implements BundleActivator {

    private static final String THING_ID_BRIDGE1 = "hue:bridge1";
    private static final String THING_ID_LAMP1 = "hue:lamp1";

    private class HueItemProvider implements ItemProvider {

        @Override
        public Collection<Item> getItems() {
            ColorItem colorItem = new ColorItem("hueLampColor1");
            DimmerItem dimmerItem = new DimmerItem("hueLampColorTemperature1");
            return new ArrayList<Item>(Lists.newArrayList(colorItem, dimmerItem));
        }

        @Override
        public void addItemChangeListener(ItemsChangeListener listener) {
            // TODO Auto-generated method stub
        }

        @Override
        public void removeItemChangeListener(ItemsChangeListener listener) {
            // TODO Auto-generated method stub
        }

    }

    private ServiceRegistration<?> serviceRegistration;

    public void start(BundleContext bc) throws Exception {
        ConfigurationAdmin configurationAdmin = (ConfigurationAdmin) bc.getService(bc
                .getServiceReference(ConfigurationAdmin.class.getName()));
        Configuration[] bridgeConfigurations = configurationAdmin
                .listConfigurations("(service.factoryPid=org.eclipse.smarthome.binding.hue.bridge)");
        if (bridgeConfigurations != null) {
            for (Configuration configuration : bridgeConfigurations) {
                configuration.delete();
            }
        }

        Configuration[] lampConfigurations = configurationAdmin
                .listConfigurations("(service.factoryPid=org.eclipse.smarthome.binding.hue.lamp)");
        if (lampConfigurations != null) {
            for (Configuration configuration : lampConfigurations) {
                configuration.delete();
            }
        }

        HueBridgeHandler.HueBridgeConfiguration hueBridgeConfiguration = new HueBridgeHandler.HueBridgeConfiguration();
        hueBridgeConfiguration.ipAddress = "192.168.3.113";
        hueBridgeConfiguration.secret = "newdeveloper";

        Bridge hueBridge = BridgeBuilder.create("org.eclipse.smarthome.binding.hue.bridge", THING_ID_BRIDGE1).build();

        Channel colorChannel = ChannelBuilder.create(HueLampHandler.CHANNEL_ID_COLOR, ColorItem.class).build();
        Channel colorTemperatureChannel = ChannelBuilder.create(HueLampHandler.CHANNEL_ID_COLOR_TEMPERATURE, DimmerItem.class).build();

        HueLampHandler.HueLampConfiguration hueLampConfiguration = new HueLampHandler.HueLampConfiguration();
        hueLampConfiguration.lampId = 1;

        Thing hueLamp = ThingBuilder.create("org.eclipse.smarthome.binding.hue.lamp", THING_ID_LAMP1).withBridge(hueBridge)
                .withChannels(colorChannel, colorTemperatureChannel).build();

        ThingRegistry thingRegistry = getThingRegistry(bc);
        thingRegistry.addThing(hueBridge, hueBridgeConfiguration);
        thingRegistry.addThing(hueLamp, hueLampConfiguration);

        ItemChannelBindingRegistry itemChannelBindingRegistry = getItemChannelBindingRegistry(bc);
        itemChannelBindingRegistry.bind("hueLampSwitch1", colorTemperatureChannel);
        itemChannelBindingRegistry.bind("hueLampColor1", colorChannel);

        serviceRegistration = bc.registerService(ItemProvider.class.getName(), new HueItemProvider(), null);
    }

    private ItemChannelBindingRegistry getItemChannelBindingRegistry(BundleContext bc) {
        ServiceReference<?> serviceReference = bc.getServiceReference(ItemChannelBindingRegistry.class.getName());
        return (ItemChannelBindingRegistry) bc.getService(serviceReference);
    }

    private ThingRegistry getThingRegistry(BundleContext bc) {
        ServiceReference<?> serviceReference = bc.getServiceReference(ThingRegistry.class.getName());
        return (ThingRegistry) bc.getService(serviceReference);
    }

    public void stop(BundleContext bc) throws Exception {
        ThingRegistry thingRegistry = getThingRegistry(bc);
        thingRegistry.removeThing(THING_ID_LAMP1);
        thingRegistry.removeThing(THING_ID_BRIDGE1);
        serviceRegistration.unregister();
    }

}
