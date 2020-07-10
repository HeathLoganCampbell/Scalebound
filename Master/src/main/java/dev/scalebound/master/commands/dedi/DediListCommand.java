package dev.scalebound.master.commands.dedi;

import dev.scalebound.master.Scalebound;
import dev.scalebound.master.console.command.SubCommand;
import dev.scalebound.shared.servers.types.DedicatedServer;

import java.util.Map;

public class DediListCommand extends SubCommand
{
    private Scalebound scalebound;

    public DediListCommand(Scalebound scalebound)
    {
        super("list", new String[] { "l", "view"}, "view all dedis");

        this.scalebound = scalebound;
    }

    @Override
    public void onExecution(String[] commandArgs)
    {
        String format = "|%1$-12s|%2$-20s|%3$-20s|\n";
        this.log(format, "SERVER NAME", "SERVER ADDRESS", "RAM (MB)", "Last Updated (MS)");

        for (Map.Entry<String, DedicatedServer> serverEntry : this.scalebound.getDedicatedServerManager().getServers())
        {
            final DedicatedServer server = serverEntry.getValue();

            this.log(format, server.getServerName(),  server.getAddress(), server.getUsedRamMB() + "/" + server.getMaxRamMB(), "" + (System.currentTimeMillis() - server.getLastUpdatedTS()));
        }
    }
}
