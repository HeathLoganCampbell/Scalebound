package dev.scalebound.master.commands.mcserver;

import dev.scalebound.master.Scalebound;
import dev.scalebound.master.console.command.Command;

import java.util.Map;

public class McServerCommand extends Command
{
    private Scalebound scalebound;

    public McServerCommand(Scalebound scalebound)
    {
        super("MCServer", new String[] { "mcs", "minecraftserver"}, "Manage Minecraft Servers");

        this.scalebound = scalebound;
        this.registerSubCommand(new MCServerListCommand(this.scalebound));
        this.registerSubCommand(new MCServerReloadCommand(this.scalebound));
        this.registerSubCommand(new MCServerAddCommand(this.scalebound));
        this.registerSubCommand(new MCServerRemoveCommand(this.scalebound));
    }

    @Override
    public void onExecution(String[] commandArgs)
    {
        for (Map.Entry<String, Command> noOverlapCommand : this.getSubCommandMap().getNoOverlapCommands()) {
            System.out.println(this.getCommand() + " " + noOverlapCommand.getKey() + " | " + noOverlapCommand.getValue().getDescription());
        }
    }
}
