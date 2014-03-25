package org.eclipse.smarthome.core.thing;

import java.util.Collection;
import java.util.List;
import java.util.Map.Entry;

import org.osgi.service.component.ComponentContext;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;

public class ItemChannelBindingRegistry implements ThingTracker {

    private Multimap<String, Channel> itemChannelBindings = Multimaps.synchronizedMultimap(ArrayListMultimap
            .<String, Channel> create());

    private ThingRegistry thingRegistry;

    public void bind(String itemName, Channel channel) {
        String boundItemName = getBoundItem(channel);
        // if (boundItemName != null && !boundItemName.equals(itemName)) {
        // throw new
        // IllegalArgumentException("Channel is already bound to an item.");
        // }
        itemChannelBindings.put(itemName, channel);
    }

    public List<Channel> getBoundChannels(String itemName) {
        return Lists.newArrayList(itemChannelBindings.get(itemName));
    }

    public String getBoundItem(Channel channel) {
        Collection<Entry<String, Channel>> entries = itemChannelBindings.entries();
        for (Entry<String, Channel> entry : entries) {
            if (channel.equals(channel)) {
                return entry.getKey();
            }
        }
        return null;
    }

    public boolean isBound(String itemName, Channel channel) {
        return itemChannelBindings.containsEntry(itemName, channel);
    }

    @Override
    public void thingAdded(Thing thing, ThingTrackerEvent thingTrackerEvent) {
        // nothing to do
    }

    @Override
    public void thingRemoved(Thing thing, ThingTrackerEvent thingTrackerEvent) {
        if (thingTrackerEvent == ThingTrackerEvent.THING_REMOVED) {
            List<Channel> channels = thing.getChannels();
            for (Channel channel : channels) {
                unbind(channel);
            }
        }
    }

    @Override
    public void thingUpdated(Thing thing) {
        // nothing to do
    }

    public void unbind(Channel channel) {
        String boundItemName = getBoundItem(channel);
        if (boundItemName != null) {
            itemChannelBindings.remove(boundItemName, channel);
        }
    }

    protected void activate(ComponentContext componentContext) {
        thingRegistry.addThingTracker(this);
    }

    protected void deactivate(ComponentContext componentContext) {
        thingRegistry.removeThingTracker(this);
    }

    protected void setThingRegistry(ThingRegistry thingRegistry) {
        this.thingRegistry = thingRegistry;
    }

    protected void unsetThingRegistry(ThingRegistry thingRegistry) {
        this.thingRegistry = null;
    }

}
