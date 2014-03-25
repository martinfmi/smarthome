package org.eclipse.smarthome.core.thing.binding;

import java.util.Dictionary;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.Map.Entry;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.cm.ConfigurationException;
import org.osgi.service.cm.ManagedServiceFactory;
import org.osgi.service.component.ComponentContext;

public abstract class BaseThingHandlerFactory<H extends ThingHandler<C>, C extends ThingConfiguration> implements
        ManagedServiceFactory {

    @SuppressWarnings("rawtypes")
    private Map<String, ServiceRegistration> thingHandlers = new HashMap<>();
    private BundleContext bundleContext;

    protected void activate(ComponentContext componentContext) {
        this.bundleContext = componentContext.getBundleContext();
    }

    protected void deactivate(ComponentContext componentContext) {
        this.bundleContext = null;
    }

    @Override
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public void deleted(String pid) {
        ServiceRegistration serviceRegistration = thingHandlers.get(pid);
        H thingHandler = (H) bundleContext.getService(serviceRegistration.getReference());
        removeThingHandler(thingHandler);
        serviceRegistration.unregister();
    }

    @Override
    public String getName() {
        // TODO Auto-generated method stub
        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void updated(String pid, @SuppressWarnings("rawtypes") Dictionary dictionary) throws ConfigurationException {
        if (!thingHandlers.containsKey(pid)) {
            ThingHandler<C> thingHandler = createThingHandler(pid, dictionary);

            @SuppressWarnings("rawtypes")
            ServiceRegistration serviceRegistration = registerAsService(pid, thingHandler);
            thingHandlers.put(pid, serviceRegistration);
        } else {
            ThingHandler<C> thingHandler = getThingHandler(pid);
            thingHandler.updated(createConfiguration(dictionary));
        }
    }

    private ServiceRegistration registerAsService(String pid, ThingHandler<C> thingHandler) {
        @SuppressWarnings("rawtypes")
        Dictionary<String, Object> serviceProperties = new Hashtable<>();

        serviceProperties.put("service.pid", pid);

        Map<String, Object> additionalServiceProperties = getServiceProperties(thingHandler);
        if (additionalServiceProperties != null) {
            for (Entry<String, Object> additionalServiceProperty : additionalServiceProperties.entrySet()) {
                serviceProperties.put(additionalServiceProperty.getKey(), additionalServiceProperty.getValue());
            }
        }

        ServiceRegistration serviceRegistration = bundleContext.registerService(ThingHandler.class.getName(),
                thingHandler, serviceProperties);

        return serviceRegistration;
    }

    protected Map<String, Object> getServiceProperties(ThingHandler<C> thingHandler) {
        return null;
    }

    private ThingHandler<C> createThingHandler(String pid, Dictionary dictionary) {
        if (dictionary == null) {
            dictionary = new Hashtable<>();
        }
        ThingHandler<C> thingHandler = createThingHandler(createConfiguration(dictionary));
        return thingHandler;
    }

    protected abstract C createConfiguration(Dictionary<String, Object> configuration);

    protected abstract H createThingHandler(C configuration);

    protected abstract void removeThingHandler(H thingHandler);

    @SuppressWarnings("unchecked")
    private ThingHandler<C> getThingHandler(String pid) {
        return bundleContext.getService(thingHandlers.get(pid).getReference());
    }

}