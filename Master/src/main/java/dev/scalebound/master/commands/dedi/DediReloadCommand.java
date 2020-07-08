package dev.scalebound.master.commands.dedi;

import dev.scalebound.master.Scalebound;
import dev.scalebound.master.console.command.SubCommand;

public class DediReloadCommand extends SubCommand
{
    private Scalebound scalebound;

    public DediReloadCommand(Scalebound scalebound)
    {
        super("reload", new String[] { "rl"}, "reload the dedi data locally from the database");

        this.scalebound = scalebound;
    }

    @Override
    public void onExecution(String[] commandArgs)
    {
        this.scalebound.reloadDedicatedServers();
        this.log("Reloaded Dedicated servers from database");
    }
}
