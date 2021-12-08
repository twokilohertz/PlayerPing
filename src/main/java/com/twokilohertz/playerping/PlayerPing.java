package com.twokilohertz.playerping;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public final class PlayerPing extends JavaPlugin implements Listener
{
    private Logger log;
    @Override
    public void onEnable()
    {
        // Plugin enable logic
        log = getLogger();
        this.saveDefaultConfig();

        // Get interval between updates from config and calculate value in ticks
        long updateInterval = this.getConfig().getLong("update-interval") * 20;
        if (updateInterval <= 0) updateInterval = 20;

        // Register events & start repeat task
        this.getServer().getPluginManager().registerEvents(this, this);
        new UpdatePings(this).runTaskTimer(this, 20, updateInterval);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event)
    {
        // Update the pings when a new player joins rather than waiting for an update
        UpdatePings updatePings = new UpdatePings(this);
        updatePings.run();
    }

    @Override
    public void onDisable()
    {
        // Cancel repeated scoreboard update task
        log.info("Cancelling ping update task...");
        this.getServer().getScheduler().cancelTasks(this);

        /*
         NOTE:  As it stands, if the plugin is disabled/unloaded while the server is still running
                the player's ping times will still be shown on the player list with their last
                update values. The plugin is likely *not* reload-safe.
        */
    }
}