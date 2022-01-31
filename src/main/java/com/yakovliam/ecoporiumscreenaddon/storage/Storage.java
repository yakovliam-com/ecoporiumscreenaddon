package com.yakovliam.ecoporiumscreenaddon.storage;

import com.yakovliam.ecoporiumscreenaddon.screen.TrendScreen;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class Storage {

    /**
     * Storage implementation
     */
    private final StorageImplementation storageImplementation;

    /**
     * Storage
     *
     * @param storageImplementation storage implementation
     */
    public Storage(StorageImplementation storageImplementation) {
        this.storageImplementation = storageImplementation;
    }

    /**
     * Returns the storage implementation
     *
     * @return implementation
     */
    public StorageImplementation getStorageImplementation() {
        return storageImplementation;
    }

    /**
     * Loads trend screens
     */
    public List<TrendScreen> loadTrendScreens() {
        return this.storageImplementation.loadTrendScreens();
    }

    /**
     * Saves a trend screen
     *
     * @param trendScreen trend screen
     */
    public void saveTrendScreen(TrendScreen trendScreen) {
        CompletableFuture.runAsync(() -> this.storageImplementation.saveTrendScreen(trendScreen));
    }

    /**
     * Deletes a trend screen
     *
     * @param trendScreen trend screen
     */
    public void deleteTrendScreen(TrendScreen trendScreen) {
        CompletableFuture.runAsync(() -> this.storageImplementation.deleteTrendScreen(trendScreen));
    }
}
