package com.yakovliam.ecoporiumscreenaddon.api.config.generic.key;

import com.google.common.collect.ImmutableMap;
import com.yakovliam.ecoporiumscreenaddon.api.config.generic.adapter.ConfigurationAdapter;
import com.yakovliam.ecoporiumscreenaddon.api.config.generic.key.SimpleConfigKey;

import java.util.Map;
import java.util.function.Function;

public interface ConfigKeyFactory<T> {

    ConfigKeyFactory<Boolean> BOOLEAN = ConfigurationAdapter::getBoolean;
    ConfigKeyFactory<String> STRING = ConfigurationAdapter::getString;
    ConfigKeyFactory<String> LOWERCASE_STRING = (adapter, path, def) -> adapter.getString(path, def).toLowerCase();
    ConfigKeyFactory<Map<String, String>> STRING_MAP = (config, path, def) -> ImmutableMap.copyOf(config.getStringMap(path, ImmutableMap.of()));

    static <T> com.yakovliam.ecoporiumscreenaddon.api.config.generic.key.SimpleConfigKey<T> key(Function<ConfigurationAdapter, T> function) {
        return new com.yakovliam.ecoporiumscreenaddon.api.config.generic.key.SimpleConfigKey<>(function);
    }

    static <T> com.yakovliam.ecoporiumscreenaddon.api.config.generic.key.SimpleConfigKey<T> notReloadable(com.yakovliam.ecoporiumscreenaddon.api.config.generic.key.SimpleConfigKey<T> key) {
        key.setReloadable(false);
        return key;
    }

    static com.yakovliam.ecoporiumscreenaddon.api.config.generic.key.SimpleConfigKey<Boolean> booleanKey(String path, boolean def) {
        return key(new Bound<>(BOOLEAN, path, def));
    }

    static com.yakovliam.ecoporiumscreenaddon.api.config.generic.key.SimpleConfigKey<String> stringKey(String path, String def) {
        return key(new Bound<>(STRING, path, def));
    }

    static com.yakovliam.ecoporiumscreenaddon.api.config.generic.key.SimpleConfigKey<String> lowercaseStringKey(String path, String def) {
        return key(new Bound<>(LOWERCASE_STRING, path, def));
    }

    static SimpleConfigKey<Map<String, String>> mapKey(String path) {
        return key(new Bound<>(STRING_MAP, path, null));
    }

    /**
     * Extracts the value from the config.
     *
     * @param config the config
     * @param path the path where the value is
     * @param def the default value
     * @return the value
     */
    T getValue(ConfigurationAdapter config, String path, T def);

    /**
     * A {@link ConfigKeyFactory} bound to a given {@code path}.
     *
     * @param <T> the value type
     */
    class Bound<T> implements Function<ConfigurationAdapter, T> {
        private final ConfigKeyFactory<T> factory;
        private final String path;
        private final T def;

        Bound(ConfigKeyFactory<T> factory, String path, T def) {
            this.factory = factory;
            this.path = path;
            this.def = def;
        }

        @Override
        public T apply(ConfigurationAdapter adapter) {
            return this.factory.getValue(adapter, this.path, this.def);
        }
    }

}
