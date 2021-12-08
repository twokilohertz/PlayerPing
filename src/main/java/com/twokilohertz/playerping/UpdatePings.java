package com.twokilohertz.playerping;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class UpdatePings extends BukkitRunnable
{
    private final PlayerPing plugin;

    public UpdatePings(PlayerPing plugin)
    {
        this.plugin = plugin;
    }

    public void run()
    {
        // Iterate through players, get ping times & update scoreboard
        for (Player player : plugin.getServer().getOnlinePlayers())
        {
            // Try catch block because I don't know if this can fail unexpectedly yet
            try
            {
                // Set player name on player list to "[PINGTIME ms] PLAYERNAME"
                player.setPlayerListName("[" + player.getPing() + " ms] " + player.getDisplayName());
            }
            catch (Exception ex)
            {
                ex.printStackTrace(); // Should probably use a logger error for this instead
            }
        }
    }
}
