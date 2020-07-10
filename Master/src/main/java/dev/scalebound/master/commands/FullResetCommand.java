package dev.scalebound.master.commands;

import dev.scalebound.master.Scalebound;
import dev.scalebound.master.console.command.Command;
import dev.scalebound.shared.servers.types.MinecraftServer;

import java.util.Map;

public class FullResetCommand extends Command
{
    private Scalebound scalebound;

    public FullResetCommand(Scalebound scalebound)
    {
        super("FullReset", new String[] {  }, "Fully reset all the minecraft servers");

        this.scalebound = scalebound;
    }

    @Override
    public void onExecution(String[] commandArgs)
    {
        for (Map.Entry<String, MinecraftServer> server : scalebound.getMinecraftServerManager().getServers()) {
            scalebound.getMinecraftServerRepository().removeMinecraftServer(server.getValue().getServerName());
        }
        scalebound.getMinecraftServerManager().clearServers();
        this.log("Fully reset network");
    }
}
