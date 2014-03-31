package org.eclipse.smarthome.config.setup.internal;

import java.net.URL;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.smarthome.config.setup.flow.SetupFlow;
import org.eclipse.smarthome.config.setup.flow.SetupFlows;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleEvent;
import org.osgi.util.tracker.BundleTracker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DeclarativeSetupFlowTracker extends BundleTracker {

    private Logger logger = LoggerFactory.getLogger(DeclarativeSetupFlowTracker.class);

    private static final String DIRECTORY_ESH_SETUP_FLOW = "/ESH-INF/setup/";

    private XMLTypeParser setupFlowParser;

    private SetupFlowManagerImpl setupFlowManager;
    private Map<Bundle, SetupFlowProvider> setupFlowProviderMap;


    public DeclarativeSetupFlowTracker(BundleContext bundleContext,
            XMLTypeParser setupFlowParser, SetupFlowManagerImpl setupFlowManager) {

        super(bundleContext, Bundle.ACTIVE, null);

        if (bundleContext == null) {
            throw new IllegalArgumentException("The BundleContext must not be null!");
        }
        if (setupFlowParser == null) {
            throw new IllegalArgumentException("The setup flow parser must not be null!");
        }
        if (setupFlowManager == null) {
            throw new IllegalArgumentException("The SetupFlowManager must not be null!");
        }

        this.setupFlowParser = setupFlowParser;
        this.setupFlowManager = setupFlowManager;

        this.setupFlowProviderMap = new HashMap<>();
    }

    @Override
    public final synchronized void open() {
        super.open();
    }

    @Override
    public final synchronized void close() {
        super.close();

        this.setupFlowProviderMap.clear();
    }

    private SetupFlowProvider acquireSetupFlowProvider(Bundle bundle) {
        if (bundle != null) {
            SetupFlowProvider setupFlowProvider = this.setupFlowProviderMap.get(bundle);

            if (setupFlowProvider == null) {
                setupFlowProvider = new SetupFlowProvider(bundle.getSymbolicName());
                this.setupFlowProviderMap.put(bundle, setupFlowProvider);

                this.setupFlowManager.addSetupFlowProvider(setupFlowProvider);
            }

            return setupFlowProvider;
        }

        return null;
    }

    private void releaseSetupFlowProvider(Bundle bundle) {
        if (bundle != null) {
            SetupFlowProvider setupFlowProvider = this.setupFlowProviderMap.get(bundle);

            if (setupFlowProvider != null) {
                this.setupFlowManager.removeFlowProvider(setupFlowProvider);

                this.setupFlowProviderMap.remove(bundle);
            }
        }
System.out.println("releaseSetupFlowProvider... " + this.setupFlowProviderMap.toString());
    }

    private void addSetupFlow(Bundle bundle, SetupFlow setupFlow) {
        SetupFlowProvider setupFlowProvider = acquireSetupFlowProvider(bundle);

        if (setupFlowProvider != null) {
            setupFlowProvider.addSetupFlow(setupFlow);
        }
System.out.println("addSetupFlow... " + this.setupFlowProviderMap.toString());
    }

    private void addSetupFlow(Bundle bundle, SetupFlows setupFlows) {
        SetupFlowProvider setupFlowProvider = acquireSetupFlowProvider(bundle);

        if (setupFlowProvider != null) {
            setupFlowProvider.addSetupFlows(setupFlows);
        }
System.out.println("addSetupFlow... " + this.setupFlowProviderMap.toString());
    }

    @Override
    public final synchronized Bundle addingBundle(Bundle bundle, BundleEvent event) {
        Enumeration<String> setupFlowPaths = bundle.getEntryPaths(DIRECTORY_ESH_SETUP_FLOW);

        if (setupFlowPaths != null) {
            int numberOfParsedSetupFlows = 0;

            while (setupFlowPaths.hasMoreElements()) {
                String setupFlowPath = setupFlowPaths.nextElement();
                URL setupFlowURL = bundle.getEntry(setupFlowPath);
                String setupFlowFile = setupFlowURL.getFile();
System.out.println(setupFlowFile);

                try {
                    this.logger.debug("Parsing the setup flow definition file '{}' in bundle '{}'...",
                            setupFlowFile, bundle.getSymbolicName());

//                    SetupFlows setupFlows = this.setupFlowParser.parse(setupFlowURL);
                    Object setupFlowObj = this.setupFlowParser.parse(setupFlowURL);
System.out.println(setupFlowObj);
                    if (setupFlowObj instanceof SetupFlows) {
                        addSetupFlow(bundle, (SetupFlows) setupFlowObj);
                    } else {
                        addSetupFlow(bundle, (SetupFlow) setupFlowObj);
                    }

                    numberOfParsedSetupFlows++;
                } catch (Exception ex) {
                    this.logger.error("The file '" + setupFlowFile + "' in bundle '"
                            + bundle.getSymbolicName() + "' does not contain a valid setup flow!",
                            ex);
                }
            }

            if (numberOfParsedSetupFlows > 0) {
                return bundle;
            }
        }

        return null;
    }

    @Override
    public final synchronized void removedBundle(Bundle bundle, BundleEvent event, Object object) {
        this.logger.debug("Unregistering the setup flow definition(s) for bundle '{}'...",
                bundle.getSymbolicName());

        releaseSetupFlowProvider(bundle);
    }

}
