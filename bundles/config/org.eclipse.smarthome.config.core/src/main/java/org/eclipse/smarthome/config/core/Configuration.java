package org.eclipse.smarthome.config.core;

import java.lang.reflect.Field;
import java.util.Dictionary;
import java.util.Hashtable;

public class Configuration {

    public static <T extends Configuration> T create(Class<T> configurationClass,
            Dictionary<String, Object> dictionary) {
        try {
            T configuration = configurationClass.newInstance();
            Field[] declaredFields = configurationClass.getDeclaredFields();
            for (Field field : declaredFields) {
                Object value = dictionary.get(field.getName());
                if (value != null) {
                    field.set(configuration, value);
                }
            }
            return configuration;
        } catch (InstantiationException | IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }

    public Dictionary<String, Object> toDictionary() {
        Dictionary<String, Object> dictionary = new Hashtable<>();
        Field[] fields = this.getClass().getFields();
        for (Field field : fields) {
            try {
                String name = field.getName();
                Object value = field.get(this);
                if (value != null) {
                    dictionary.put(name, value);
                }
            } catch (IllegalArgumentException | IllegalAccessException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return dictionary;
    }

    public boolean isProperlyConfigured() {
        return true;
    }
}
