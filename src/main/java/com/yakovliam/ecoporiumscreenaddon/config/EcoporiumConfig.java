package com.yakovliam.ecoporiumscreenaddon.config;

import com.yakovliam.ecoporiumscreenaddon.api.Plugin;
import com.yakovliam.ecoporiumscreenaddon.api.config.generic.KeyedConfiguration;
import com.yakovliam.ecoporiumscreenaddon.api.config.generic.adapter.ConfigurationAdapter;

public class EcoporiumConfig extends KeyedConfiguration {

    private final Plugin plugin;

    private final ConfigurationAdapter adapter;

    public EcoporiumConfig(Plugin plugin, ConfigurationAdapter adapter) {
        super(adapter, EcoporiumConfigKeys.getKeys());
        this.plugin = plugin;
        this.adapter = adapter;

        init();
    }

    @Override
    protected void load(boolean initial) {
        super.load(initial);
    }

    public Plugin getPlugin() {
        return this.plugin;
    }

    public ConfigurationAdapter getAdapter() {
        return adapter;
    }
}