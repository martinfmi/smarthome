package org.eclipse.smarthome.core.thing;

import org.eclipse.smarthome.core.items.Item;

/**
 * {@link Channel} is a part of a {@link Thing} that represents a functionality
 * of it. Therefore {@link Item}s can be bound a to a channel. The channel only
 * accepts a specific item type which is specified by
 * {@link Channel#getAcceptedItemType()} methods.
 * 
 * @author Dennis Nobel - Initial contribution and API
 */
public class Channel implements Identifiable {

    private Class<? extends Item> acceptedItemType;

    private String id;

    public Channel(String id, Class<? extends Item> acceptedItemType) {
        this.id = id;
        this.acceptedItemType = acceptedItemType;
    }

    public Class<? extends Item> getAcceptedItemType() {
        return this.acceptedItemType;
    }

    public String getId() {
        return this.id;
    }
}
