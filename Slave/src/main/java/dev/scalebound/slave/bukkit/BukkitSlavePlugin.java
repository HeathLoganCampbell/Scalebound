package dev.scalebound.slave.bukkit;

import dev.scalebound.slave.common.Monitor;
import org.bukkit.plugin.java.JavaPlugin;

public class BukkitSlavePlugin extends JavaPlugin
{
    private Monitor monitor;

    @Override
    public void onEnable()
    {
        this.monitor = new Monitor(new BukkitDataCollector());


    }

    @Override
    public void onDisable()
    {
        this.monitor.disable();
    }
}
// Friend system
// Slime + fS
//