package dev.scalebound.master.commands.dedi;

import dev.scalebound.master.Scalebound;
import dev.scalebound.master.console.command.SubCommand;
import dev.sprock.scalebound.shared.commons.InputUtils;
import dev.sprock.scalebound.shared.servers.types.DedicatedServer;

//dedi rm <ServerName>
public class DediRemoveCommand extends SubCommand
{
    private Scalebound scalebound;

    public DediRemoveCommand(Scalebound scalebound)
    {
        super("remove", new String[] { "rm" }, "dedi remove");

        this.scalebound = scalebound;
    }

    @Override
    public void onExecution(String[] commandArgs)
    {
        if(commandArgs.length != 1)
        {
            this.log("Please use '/dedi rm <ServerName/ServerId>'");
            return;
        }

        String serverTag = commandArgs[0];
        if(InputUtils.isNumeric(serverTag))
        {
            //Server id
            int serverId = Integer.parseInt(serverTag);
            this.scalebound.getDedicatedServerRepository().removeDedicatedServerById(serverId);
            this.log("Removed #" + serverId);
        }
        else
        {
            String serverName = serverTag;

            final DedicatedServer serverByName = this.scalebound.getDedicatedServerManager().getServerByName(serverName);
            if(serverByName == null)
            {
                this.log("Server " + serverName + " does not exist");
                return;
            }

            this.scalebound.getDedicatedServerRepository().removeDedicatedServerByName(serverName);
            log("Removed " + serverName);
        }

        this.scalebound.reloadDedicatedServers();
        this.log("reloaded dedicated servers from database");
    }
}
