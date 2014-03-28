package org.eclipse.smarthome.config.setup.internal;

import java.net.URL;
import java.util.Enumeration;

import org.eclipse.smarthome.config.setup.flow.SetupFlows;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleEvent;
import org.osgi.util.tracker.BundleTracker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DeclarativeSetupFlowTracker extends BundleTracker {

    private Logger logger = LoggerFactory.getLogger(DeclarativeSetupFlowTracker.class);

    private static final String DIRECTORY_ESH_SETUP_FLOW = "ESH-INF/setup";

    private XMLTypeParser<SetupFlows> setupFlowParser;


    public DeclarativeSetupFlowTracker(
            BundleContext bundleContext, XMLTypeParser<SetupFlows> setupFlowParser) {

        super(bundleContext, Bundle.ACTIVE, null);

        if (bundleContext == null) {
            throw new IllegalArgumentException("The BundleContext must not be null!");
        }
        if (setupFlowParser == null) {
            throw new IllegalArgumentException("The setup flow parser must not be null!");
        }

        this.setupFlowParser = setupFlowParser;
   }

    @Override
    public synchronized Bundle addingBundle(Bundle bundle, BundleEvent event) {
        Enumeration<String> setupFlowPaths = bundle.getEntryPaths(DIRECTORY_ESH_SETUP_FLOW);

        if (setupFlowPaths != null) {
            while (setupFlowPaths.hasMoreElements()) {
                String setupFlowPath = setupFlowPaths.nextElement();
                URL setupFlowURL = bundle.getEntry(setupFlowPath);
System.out.println(setupFlowURL);

                try {
                    SetupFlows setupFlows = this.setupFlowParser.parse(setupFlowURL);
                } catch (Exception ex) {
                    this.logger.error("The 'QiviconManifest.xml' for bundle '" + bundle.getSymbolicName()
                      + "' could not be parsed successfully: " + ex.getMessage(), ex);

          return null;
                }
            }
        }

        return null;
//        URL qiviconManifestUrl = bundle.getEntryPaths(Resource(QIVICON_MANIFEST_XML);

//        if (qiviconManifestUrl != null) {
//            if (!this.manifestRegistry.containsManifest(bundle)) {
//                QiviconManifest manifest = null;
//                try {
//                    manifest = this.manifestParser.parse(qiviconManifestUrl);
//
//                    Logger.debug("The 'QiviconManifest.xml' for bundle '" + bundle.getSymbolicName()
//                            + "' has been parsed successfully.");
//                } catch (Exception ex) {
//                    Logger.error("The 'QiviconManifest.xml' for bundle '" + bundle.getSymbolicName()
//                            + "' could not be parsed successfully: " + ex.getMessage(), ex);
//
//                    return null;
//                }
//
//                this.manifestRegistry.addManifest(bundle, manifest);
//            }
//
//            return bundle;
//        }
    }

    @Override
    public synchronized void removedBundle(Bundle bundle, BundleEvent event, Object object) {
        if ((event == null) || (event.getType() == BundleEvent.UNRESOLVED)) {
            // unregister provider
        }
    }

}
