package dev.scalebound.master.commands.mcserver;

import dev.scalebound.master.Scalebound;
import dev.scalebound.master.console.command.SubCommand;

// /MCServer view
public class MCServerReloadCommand extends SubCommand
{
    private Scalebound scalebound;

    public MCServerReloadCommand(Scalebound scalebound)
    {
        super("reload", new String[] { "rl" }, "view all Servers");

        this.scalebound = scalebound;
    }

    @Override
    public void onExecution(String[] commandArgs)
    {
        this.scalebound.reloadMinecraftServers();
        this.log("Reloaded Minecraft Servers.");
    }
}
