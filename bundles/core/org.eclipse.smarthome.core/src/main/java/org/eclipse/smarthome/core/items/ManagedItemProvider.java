package org.eclipse.smarthome.core.items;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * {@link ManagedItemProvider} is an OSGi service, that allows to add or remove
 * items at runtime by calling {@link ManagedItemProvider#addItem(Item)} or
 * {@link ManagedItemProvider#removeItem(Item)}. An added item is automatically
 * exposed to the {@link ItemRegistry}.
 * 
 * @author Dennis Nobel - Initial contribution
 * 
 */
public class ManagedItemProvider implements ItemProvider {

    private List<Item> items = new CopyOnWriteArrayList<>();
    private List<ItemsChangeListener> itemsChangeListeners = new CopyOnWriteArrayList<>();

    public void addItem(Item item) {
        this.items.add(item);
        notifyItemChangeListenersAboutAddedItem(item);
    }

    @Override
    public void addItemChangeListener(ItemsChangeListener listener) {
        itemsChangeListeners.add(listener);
    }

    @Override
    public Collection<Item> getItems() {
        return this.items;
    }

    public void removeItem(Item item) {
        this.items.remove(item);
        notifyItemChangeListenersAboutRemovedItem(item);
    }

    @Override
    public void removeItemChangeListener(ItemsChangeListener listener) {
        itemsChangeListeners.remove(listener);
    }

    private void notifyItemChangeListenersAboutAddedItem(Item item) {
        for (ItemsChangeListener itemsChangeListener : this.itemsChangeListeners) {
            itemsChangeListener.itemAdded(this, item);
        }
    }

    private void notifyItemChangeListenersAboutRemovedItem(Item item) {
        for (ItemsChangeListener itemsChangeListener : this.itemsChangeListeners) {
            itemsChangeListener.itemRemoved(this, item);
        }
    }

}
