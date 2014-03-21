package org.eclipse.smarthome.core.thing;

import java.io.IOException;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.eclipse.smarthome.core.thing.binding.ThingConfiguration;
import org.eclipse.smarthome.core.thing.internal.ThingImpl;
import org.eclipse.smarthome.core.thing.internal.ThingManager;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.service.component.ComponentContext;
import org.osgi.service.event.EventConstants;
import org.osgi.service.event.EventHandler;

public class ThingRegistry {

    private ConfigurationAdmin configurationAdmin;
    private List<Thing> things = new CopyOnWriteArrayList<>();
    private BundleContext bundleContext;
    private List<ThingTracker> thingTrackers = new CopyOnWriteArrayList<>();
    private ThingManager thingManager;
    private ServiceRegistration thingManagerServiceRegistration;

    protected void activate(ComponentContext componentContext) {
        this.bundleContext = componentContext.getBundleContext();
        thingManager = new ThingManager(this.bundleContext);
        Dictionary<String, Object> properties = new Hashtable<>();
        properties.put(EventConstants.EVENT_TOPIC, "smarthome/*");
        thingManagerServiceRegistration = this.bundleContext.registerService(EventHandler.class.getName(),
                this.thingManager, properties);
        addThingTracker(thingManager);
    }

    protected void setConfigurationAdmin(ConfigurationAdmin configurationAdmin) {
        this.configurationAdmin = configurationAdmin;
    }

    protected void unsetConfigurationAdmin(ConfigurationAdmin configurationAdmin) {
        this.configurationAdmin = null;
    }

    protected void deactivate(ComponentContext componentContext) {
        removeThingTracker(thingManager);
        thingManagerServiceRegistration.unregister();
    }

    public void addThing(Thing thing, ThingConfiguration configuration) {
        try {
            Configuration factoryConfiguration = configurationAdmin.createFactoryConfiguration(thing.getFactoryPid(),
                    null);
            if (configuration != null) {
                factoryConfiguration.update(configuration.toDictionary());
            }
            ((ThingImpl) thing).setConfigurationPid(factoryConfiguration.getPid());
            ((ThingImpl) thing).setFactoryPid(factoryConfiguration.getFactoryPid());
            notifyListenersAboutAddedThing(thing);
            things.add(thing);
        } catch (IOException ex) {
            // TODO Auto-generated catch block
            ex.printStackTrace();
        }
    }

    public void removeThing(Thing thing) {
        try {
            Configuration configuration = configurationAdmin.getConfiguration(thing.getConfigurationPid());
            configuration.delete();
            notifyListenersAboutRemovedThing(thing);
            things.remove(thing);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public List<Thing> getThings() {
        return things;
    }

    public void addThingTracker(ThingTracker thingTracker) {
        notifyListenerAboutAllThingsAdded(thingTracker);
        thingTrackers.add(thingTracker);
    }

    public void removeThingTracker(ThingTracker thingTracker) {
        notifyListenerAboutAllThingsRemoved(thingTracker);
        thingTrackers.remove(thingTracker);
    }

    private void notifyListenersAboutAddedThing(Thing thing) {
        for (ThingTracker thingTracker : thingTrackers) {
            thingTracker.thingAdded(thing);
        }
    }

    private void notifyListenersAboutRemovedThing(Thing thing) {
        for (ThingTracker thingTracker : thingTrackers) {
            thingTracker.thingRemoved(thing);
        }
    }

    private void notifyListenerAboutAllThingsAdded(ThingTracker thingTracker) {
        for (Thing thing : this.things) {
            thingTracker.thingAdded(thing);
        }
    }

    private void notifyListenerAboutAllThingsRemoved(ThingTracker thingTracker) {
        for (Thing thing : this.things) {
            thingTracker.thingRemoved(thing);
        }
    }
}
