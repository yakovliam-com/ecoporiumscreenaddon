package com.yakovliam.ecoporiumscreenaddon;

import com.yakovliam.ecoporium.api.EcoporiumPlugin;
import com.yakovliam.ecoporium.api.market.MarketCache;
import com.yakovliam.ecoporiumscreenaddon.api.Plugin;
import com.yakovliam.ecoporiumscreenaddon.api.message.Message;
import com.yakovliam.ecoporiumscreenaddon.command.CommandManager;
import com.yakovliam.ecoporiumscreenaddon.config.EcoporiumConfig;
import com.yakovliam.ecoporiumscreenaddon.map.MapPlacementHandler;
import com.yakovliam.ecoporiumscreenaddon.message.Messages;
import com.yakovliam.ecoporiumscreenaddon.screen.TrendScreenManager;
import com.yakovliam.ecoporiumscreenaddon.storage.Storage;
import com.yakovliam.ecoporiumscreenaddon.storage.implementation.json.JsonStorageImplementation;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;

public class EcoporiumScreenAddonPlugin extends Plugin {

    /**
     * Trend screen manager
     */
    private TrendScreenManager trendScreenManager;

    /**
     * Map placement handler
     */
    private MapPlacementHandler mapPlacementHandler;

    /**
     * Ecoporium configuration
     */
    private EcoporiumConfig ecoporiumConfig;

    /**
     * Language config
     */
    private EcoporiumConfig langConfig;

    /**
     * Storage
     */
    private Storage storage;

    /**
     * Messages
     */
    private Messages messages;

    /**
     * Market cache
     */
    private MarketCache marketCache;

    @Override
    public void onEnable() {

        // initialize audience provider
        Message.initAudience(this);

        this.ecoporiumConfig = new EcoporiumConfig(this, provideConfigAdapter("config.yml"));
        this.langConfig = new EcoporiumConfig(this, provideConfigAdapter("lang.yml"));

        loadMessages();

        this.storage = new Storage(new JsonStorageImplementation(this));
        this.mapPlacementHandler = new MapPlacementHandler(this);
        this.trendScreenManager = new TrendScreenManager(this);

        new CommandManager(this);

        // get ecoporium plugin
        RegisteredServiceProvider<EcoporiumPlugin> provider = Bukkit.getServicesManager().getRegistration(EcoporiumPlugin.class);
        if (provider != null) {
            this.marketCache = provider.getProvider().getMarketCache();
        } else {
            this.getLogger().severe("Can't hook into Ecoporium markets!");
        }
    }

    /**
     * Returns trend screen manager
     *
     * @return trend screen manager
     */
    public TrendScreenManager getTrendScreenManager() {
        return trendScreenManager;
    }

    /**
     * Returns the map placement handler
     *
     * @return map placement handler
     */
    public MapPlacementHandler getMapPlacementHandler() {
        return mapPlacementHandler;
    }

    /**
     * Returns storage
     *
     * @return storage
     */
    public Storage getStorage() {
        return storage;
    }

    /**
     * Returns messages
     *
     * @return messages
     */
    public Messages getMessages() {
        return messages;
    }

    /**
     * Loads messages
     */
    public void loadMessages() {
        this.messages = new Messages(this);
    }

    /**
     * Returns lang config
     *
     * @return lang config
     */
    public EcoporiumConfig getLangConfig() {
        return langConfig;
    }

    /**
     * Returns the Ecoporium config
     *
     * @return config
     */
    public EcoporiumConfig getEcoporiumConfig() {
        return ecoporiumConfig;
    }

    /**
     * Returns market cache
     *
     * @return market cache
     */
    public MarketCache getMarketCache() {
        return this.marketCache;
    }
}
