package com.yakovliam.ecoporiumscreenaddon.storage.implementation.json;

import com.yakovliam.ecoporiumscreenaddon.EcoporiumScreenAddonPlugin;
import com.yakovliam.ecoporiumscreenaddon.screen.TrendScreen;
import com.yakovliam.ecoporiumscreenaddon.storage.StorageImplementation;
import org.spongepowered.configurate.ConfigurateException;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.serialize.SerializationException;

import java.util.List;
import java.util.Objects;

public class JsonStorageImplementation implements StorageImplementation {

    /**
     * Ecoporium plugin
     */
    private final EcoporiumScreenAddonPlugin plugin;

    /**
     * Screens provider
     */
    private final JsonConfigurationProvider trendScreensProvider;

    /**
     * Json storage implementation
     *
     * @param plugin plugin
     */
    public JsonStorageImplementation(EcoporiumScreenAddonPlugin plugin) {
        this.plugin = plugin;
        this.trendScreensProvider = new JsonConfigurationProvider(plugin, "trend-screens.json");

        // init
        init();
    }

    /**
     * Initializes
     */
    @Override
    public void init() {
        // resolves the path which creates the files if they don't already exist
        trendScreensProvider.load();
    }

    /**
     * Shuts down
     */
    @Override
    public void shutdown() {
        // save
        save();
    }

    @Override
    public List<TrendScreen> loadTrendScreens() {
        ConfigurationNode node = trendScreensProvider.getRoot().node("screens");
        // get screens list
        try {
            return node.getList(TrendScreen.class);
        } catch (SerializationException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void saveTrendScreen(TrendScreen trendScreen) {
        ConfigurationNode node = trendScreensProvider.getRoot().node("screens");
        // get screens list
        try {
            List<TrendScreen> trendScreens = node.getList(TrendScreen.class);
            // remove trend screen that we're saving back (if it exists)
            Objects.requireNonNull(trendScreens).removeIf(t -> t.getUuid().equals(trendScreen.getUuid()));
            // add back to list
            trendScreens.add(trendScreen);
            // set list
            node.setList(TrendScreen.class, trendScreens);
            // save
            save();
        } catch (SerializationException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteTrendScreen(TrendScreen trendScreen) {
        ConfigurationNode node = trendScreensProvider.getRoot().node("screens");
        // get screens list
        try {
            List<TrendScreen> trendScreens = node.getList(TrendScreen.class);
            // remove trend screen
            Objects.requireNonNull(trendScreens).removeIf(t -> t.getUuid().equals(trendScreen.getUuid()));
            // set list
            node.setList(TrendScreen.class, trendScreens);
            // save
            save();
        } catch (SerializationException e) {
            e.printStackTrace();
        }
    }

    /**
     * Saves the file
     */
    private void save() {
        try {
            trendScreensProvider.getLoader().save(trendScreensProvider.getRoot());
        } catch (ConfigurateException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns the plugin
     *
     * @return plugin
     */
    @Override
    public EcoporiumScreenAddonPlugin getPlugin() {
        return plugin;
    }
}
