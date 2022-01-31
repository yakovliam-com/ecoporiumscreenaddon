package com.yakovliam.ecoporiumscreenaddon.message;

import com.yakovliam.ecoporiumscreenaddon.EcoporiumScreenAddonPlugin;
import com.yakovliam.ecoporiumscreenaddon.api.config.generic.adapter.ConfigurationAdapter;
import com.yakovliam.ecoporiumscreenaddon.api.message.Message;

public class Messages {

    // something went wrong
    public final Message somethingWentWrong;

    /**
     * Ecoporium command
     */

    // help
    public final Message ecoporiumHelp;

    // reloaded
    public final Message ecoporiumReloaded;

    // screen create cancel not in placement session
    public Message ecoporiumScreenCreateNotInPlacementSession;

    // screen create already in session
    public Message ecoporiumScreenCreateAlreadyInPlacementSession;

    // screen create canceled
    public Message ecoporiumScreenCreateCanceled;

    // screen cant find
    public Message ecoporiumScreenDeleteCantFind;

    // screen deleted
    public final Message ecoporiumScreenDeleteDeleted;

    // market nonexistent
    public final Message ecoporiumMarketNonexistent;

    // retrieving market
    public final Message ecoporiumMarketGettingData;

    // market symbol doesn't exist
    public final Message ecoporiumMarketSymbolDoesntExist;

    /**
     * Placement
     */

    public final Message placementStartPlacing;

    /**
     * Messages
     *
     * @param plugin plugin
     */
    public Messages(EcoporiumScreenAddonPlugin plugin) {
        ConfigurationAdapter adapter = plugin.getLangConfig().getAdapter();

        ecoporiumHelp = Message.fromConfigurationSection("ecoporium.help", adapter);
        ecoporiumReloaded = Message.fromConfigurationSection("ecoporium.reloaded", adapter);
        somethingWentWrong = Message.fromConfigurationSection("something-went-wrong", adapter);

        ecoporiumMarketNonexistent = Message.fromConfigurationSection("ecoporium.market.nonexistent", adapter);
        ecoporiumMarketGettingData = Message.fromConfigurationSection("ecoporium.market.getting-data", adapter);
        ecoporiumMarketSymbolDoesntExist = Message.fromConfigurationSection("ecoporium.market.symbol-doesnt-exist", adapter);

        ecoporiumScreenCreateNotInPlacementSession = Message.fromConfigurationSection("ecoporium.screen.create.not-in-placement-session", adapter);
        ecoporiumScreenCreateAlreadyInPlacementSession = Message.fromConfigurationSection("ecoporium.screen.create.already-in-placement-session", adapter);
        ecoporiumScreenCreateCanceled = Message.fromConfigurationSection("ecoporium.screen.create.canceled", adapter);
        ecoporiumScreenDeleteCantFind = Message.fromConfigurationSection("ecoporium.screen.delete.cant-find", adapter);
        ecoporiumScreenDeleteDeleted = Message.fromConfigurationSection("ecoporium.screen.delete.deleted", adapter);
        placementStartPlacing = Message.fromConfigurationSection("placement.start-placing", adapter);
    }
}
