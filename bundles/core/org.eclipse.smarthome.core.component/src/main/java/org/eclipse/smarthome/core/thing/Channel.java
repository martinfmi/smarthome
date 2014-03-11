package org.eclipse.smarthome.core.thing;

import org.eclipse.smarthome.core.items.Item;

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
