package org.eclipse.smarthome.core.thing.internal;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.smarthome.core.events.AbstractEventSubscriber;
import org.eclipse.smarthome.core.thing.Channel;
import org.eclipse.smarthome.core.thing.ItemChannelBindingRegistry;
import org.eclipse.smarthome.core.thing.Thing;
import org.eclipse.smarthome.core.thing.ThingTracker;
import org.eclipse.smarthome.core.thing.binding.ThingHandler;
import org.eclipse.smarthome.core.types.Command;
import org.eclipse.smarthome.core.types.State;
import org.osgi.framework.BundleContext;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;

public class ThingManager extends AbstractEventSubscriber implements ThingTracker {

    private BundleContext bundleContext;
    private Map<Thing, ThingHandlerServiceTracker> thingHandlerTrackers = new HashMap<>();

    public ThingManager(BundleContext bundleContext) {
        this.bundleContext = bundleContext;
    }

    @Override
    public void thingAdded(Thing thing, ThingTrackerEvent thingTrackerEvent) {
        try {
            ThingHandlerServiceTracker thingHandlerTracker = new ThingHandlerServiceTracker(bundleContext, thing);
            thingHandlerTracker.open();
            thingHandlerTrackers.put(thing, thingHandlerTracker);
        } catch (InvalidSyntaxException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public void thingRemoved(Thing thing, ThingTrackerEvent thingTrackerEvent) {
        thingHandlerTrackers.get(thing).close();
    }

    @Override
    public void receiveCommand(String itemName, Command command) {
        Set<Thing> things = thingHandlerTrackers.keySet();
        for (Thing thing : things) {
            List<Channel> ports = thing.getChannels();
            for (Channel thingPort : ports) {
                if (isItemBoundToChannel(itemName, thingPort)) {
                    thing.getHandler().handleCommand(thingPort, command);
                }
            }
        }
    }

    @Override
    public void receiveUpdate(String itemName, State newState) {
        Set<Thing> things = thingHandlerTrackers.keySet();
        for (Thing thing : things) {
            List<Channel> channels = thing.getChannels();
            for (Channel channel : channels) {
                if (isItemBoundToChannel(itemName, channel)) {
                    ThingHandler<?> handler = thing.getHandler();
                    if (handler != null) {
                        handler.handleUpdate(channel, newState);
                    }
                }
            }
        }
    }

    private ItemChannelBindingRegistry getItemChannelBindingRegistry() {
        ServiceReference<?> serviceReference = bundleContext.getServiceReference(ItemChannelBindingRegistry.class
                .getName());
        return (ItemChannelBindingRegistry) (serviceReference != null ? bundleContext.getService(serviceReference)
                : null);

    }

    private boolean isItemBoundToChannel(String itemName, Channel channel) {
        ItemChannelBindingRegistry itemChannelBindingRegistry = getItemChannelBindingRegistry();
        return itemChannelBindingRegistry != null ? itemChannelBindingRegistry.isBound(itemName, channel) : false;
    }

    @Override
    public void thingUpdated(Thing thing) {
        // TODO Auto-generated method stub
    }

}
