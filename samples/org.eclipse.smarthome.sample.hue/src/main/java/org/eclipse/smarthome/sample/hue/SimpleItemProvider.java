package org.eclipse.smarthome.sample.hue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.smarthome.core.items.Item;
import org.eclipse.smarthome.core.items.ItemProvider;
import org.eclipse.smarthome.core.items.ItemsChangeListener;
import org.eclipse.smarthome.core.library.items.ColorItem;

public class SimpleItemProvider implements ItemProvider {

    @Override
    public Collection<Item> getItems() {
        ColorItem colorItem = new ColorItem("HueLamp1");
        List<Item> items = new ArrayList<>();
        items.add(colorItem);
        return items;
    }

    @Override
    public void addItemChangeListener(ItemsChangeListener listener) {

    }

    @Override
    public void removeItemChangeListener(ItemsChangeListener listener) {

    }
    
}
