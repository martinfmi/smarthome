package org.eclipse.smarthome.core.thing.binding.builder;

import org.eclipse.smarthome.core.items.Item;
import org.eclipse.smarthome.core.thing.Channel;

public class ChannelBuilder {

    private Channel channel;

    private ChannelBuilder(Channel channel) {
        this.channel = channel;
    }

    public static ChannelBuilder create(String id, Class<? extends Item> acceptedItemType) {
        return new ChannelBuilder(new Channel(id, acceptedItemType));
    }

    public Channel build() {
        return channel;
    }
}
