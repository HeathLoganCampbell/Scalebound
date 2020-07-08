package dev.scalebound.master.commands.dedi;

import dev.scalebound.master.Scalebound;
import dev.scalebound.master.console.command.Command;

import java.util.Map;

//Dedi
//  list
//  reload
//  add <Name> <IpAddress> <MaxRamMB>
//  remove <name>

public class DediCommand extends Command
{
    public DediCommand(Scalebound scalebound)
    {
        super("Dedicated", new String[] { "dedi" } , "Manage dedicated servers");

        this.registerSubCommand(new DediListCommand(scalebound));
        this.registerSubCommand(new DediReloadCommand(scalebound));
        this.registerSubCommand(new DediAddCommand(scalebound));
        this.registerSubCommand(new DediRemoveCommand(scalebound));
    }

    @Override
    public void onExecution(String[] commandArgs)
    {
        for (Map.Entry<String, Command> noOverlapCommand : this.getSubCommandMap().getNoOverlapCommands()) {
            System.out.println(noOverlapCommand.getKey() + " | " + noOverlapCommand.getValue().getDescription());
        }
    }
}
