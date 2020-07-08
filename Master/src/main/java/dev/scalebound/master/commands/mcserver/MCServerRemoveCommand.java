package dev.scalebound.master.commands.mcserver;

import dev.scalebound.master.Scalebound;
import dev.scalebound.master.console.command.SubCommand;
import dev.sprock.scalebound.shared.servers.types.MinecraftServer;

// /MCServer view
public class MCServerRemoveCommand extends SubCommand
{
    private Scalebound scalebound;

    public MCServerRemoveCommand(Scalebound scalebound)
    {
        super("remove", new String[] { "rm"}, "remove Servers");

        this.scalebound = scalebound;
    }

    @Override
    public void onExecution(String[] commandArgs)
    {
        if(commandArgs.length != 1)
        {
            this.log("Please use '/MCServer rm <ServerName>'");
            return;
        }

        final MinecraftServer server = this.scalebound.getMinecraftServerManager().getServerByName(commandArgs[0]);
        if(server == null)
        {
            this.log("Server " + commandArgs[0] + " does not exist");
            return;
        }

        this.scalebound.getMinecraftServerRepository().removeMinecraftServer(server.getServerName());
        this.log("Removed server " + server.getServerName());
    }
}
