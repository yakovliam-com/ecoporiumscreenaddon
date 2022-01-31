package com.yakovliam.ecoporiumscreenaddon.command;

import co.aikar.commands.BaseCommand;
import com.yakovliam.ecoporiumscreenaddon.EcoporiumScreenAddonPlugin;

public abstract class AbstractEcoporiumCommand extends BaseCommand {

    /**
     * Plugin
     */
    protected final EcoporiumScreenAddonPlugin plugin;

    /**
     * Manager
     */
    protected final CommandManager manager;

    /**
     * Ecoporium command
     *
     * @param manager manager
     * @param plugin  plugin
     */
    public AbstractEcoporiumCommand(CommandManager manager, EcoporiumScreenAddonPlugin plugin) {
        this.plugin = plugin;
        this.manager = manager;
    }

    protected abstract void registerCompletions();

    protected abstract void registerContexts();
}
