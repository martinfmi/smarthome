package org.eclipse.smarthome.core.thing.internal;

import org.eclipse.smarthome.core.thing.Thing;
import org.eclipse.smarthome.core.thing.ThingStatus;
import org.eclipse.smarthome.core.thing.binding.ThingHandler;
import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;

@SuppressWarnings("rawtypes")
public class ThingHandlerServiceTracker extends ServiceTracker {

    private final Thing thing;
    private BundleContext bundleContext;

    @SuppressWarnings("unchecked")
    public ThingHandlerServiceTracker(BundleContext context, Thing thing) throws InvalidSyntaxException {
        super(context, context.createFilter(getFilter(thing)), null);
        this.bundleContext = context;
        this.thing = thing;
    }

    private static String getFilter(Thing thing) {
        return "(&(" + Constants.OBJECTCLASS + "=" + ThingHandler.class.getName() + ")(service.pid="
                + thing.getConfigurationPid() + "))";
    }

    @SuppressWarnings({ "unchecked" })
    @Override
    public Object addingService(ServiceReference serviceReference) {

        ThingHandler<?> thingHandler = bundleContext.getService(serviceReference);
        thingHandler.setThing(thing);
        thing.setHandler(thingHandler);
        thing.setStatus(ThingStatus.ONLINE);
        return thingHandler;
    }

    @Override
    public void removedService(ServiceReference reference, Object service) {
        ThingHandler<?> thingHandler = (ThingHandler<?>) service;
        // TODO: think about setting thing to null if useful
        // thingHandler.setThing(null);
        thing.setHandler(null);
        thing.setStatus(ThingStatus.OFFLINE);
    }

}
