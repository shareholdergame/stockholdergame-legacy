package com.stockholdergame.server.localization;

import com.stockholdergame.server.i18n.ErrorsResourceBundleKeys;
import com.stockholdergame.server.i18n.InternalResourceBundleKeys;
import com.stockholdergame.server.i18n.ServiceResourceBundleKeys;
import com.stockholdergame.server.model.i18n.ModelResourceBundleKeys;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author Alexander Savin
 *         Date: 14.9.2010 21.50.02
 */
public class ResourceBundleRegistry {

    private static ResourceBundleRegistry instance = new ResourceBundleRegistry();

    public static ResourceBundleRegistry getInstance() {
        return instance;
    }

    private ResourceBundleRegistry() {
        register(ModelResourceBundleKeys.BUNDLE_BASE_NAME, ModelResourceBundleKeys.class);
        register(ServiceResourceBundleKeys.BUNDLE_BASE_NAME, ServiceResourceBundleKeys.class);
        register(InternalResourceBundleKeys.BUNDLE_BASE_NAME, InternalResourceBundleKeys.class);
        register(ErrorsResourceBundleKeys.BUNDLE_BASE_NAME, ErrorsResourceBundleKeys.class);
    }

    private void register(String bundleBaseName, Class<?> resourceBundleKeysClass) {
        bundleMap.put(bundleBaseName, resourceBundleKeysClass);
    }

    private Map<String, Class> bundleMap = new HashMap<String, Class>();

    public static Set<Map.Entry<String, Class>> getBundles() {
        return Collections.unmodifiableSet(getInstance().bundleMap.entrySet());
    }
}
