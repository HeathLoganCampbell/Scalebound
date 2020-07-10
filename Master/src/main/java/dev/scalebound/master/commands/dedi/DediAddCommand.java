package dev.scalebound.master.commands.dedi;

import dev.scalebound.master.Scalebound;
import dev.scalebound.master.console.command.SubCommand;
import dev.scalebound.shared.commons.InputUtils;

//  add <Name> <IpAddress> <MaxRamMB>
public class DediAddCommand extends SubCommand
{
    private Scalebound scalebound;

    public DediAddCommand(Scalebound scalebound)
    {
        super("add", new String[] { "register", "a"}, "add a new dedicated server");

        this.scalebound = scalebound;
    }

    @Override
    public void onExecution(String[] commandArgs) {
        if (commandArgs.length < 3) {
            this.log("Please use '/dedi add <DediName> <IpAddress> <MaxRamMB>'");
            return;
        }

        final String dediName = commandArgs[0];
        final String ipAddress = commandArgs[1];
        final String maxRamMBStr = commandArgs[2];

        if (!InputUtils.isNumeric(maxRamMBStr))
        {
            this.log("Please enter a number for RamMB");
            return;
        }

        final int maxRamMB = Integer.parseInt(maxRamMBStr);

        this.scalebound.getDedicatedServerRepository().addDedicatedServer(dediName, ipAddress, maxRamMB, System.currentTimeMillis());
        this.log("Added server " + dediName);
        this.scalebound.reloadDedicatedServers();
        this.log("Reloaded dedicated server list");
    }
}
