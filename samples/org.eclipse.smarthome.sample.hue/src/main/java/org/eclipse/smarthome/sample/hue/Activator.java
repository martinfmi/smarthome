package org.eclipse.smarthome.sample.hue;

import org.eclipse.smarthome.core.items.ItemProvider;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

public class Activator implements BundleActivator {

    private ServiceRegistration<?> serviceRegistration;

    @Override
    public void start(final BundleContext bundleContext) throws Exception {

        serviceRegistration = bundleContext.registerService(ItemProvider.class.getName(), new SimpleItemProvider(),
                null);

    }


    @Override
    public void stop(BundleContext bundleContext) throws Exception {
        serviceRegistration.unregister();
    }

}
