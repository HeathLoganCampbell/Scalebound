package dev.scalebound.master.commands;

import dev.scalebound.master.console.command.Command;
import dev.scalebound.master.console.command.CommandMap;

import java.util.Map;

public class HelpCommand extends Command
{
    private CommandMap commandMap;

    public HelpCommand(CommandMap commandMap)
    {
        super("help", new String[] { "?" }, "Gives a list of all the commands and how to use them");

        this.commandMap = commandMap;
    }

    @Override
    public void onExecution(String[] commandArgs)
    {
        for (Map.Entry<String, Command> noOverlapCommand : this.commandMap.getNoOverlapCommands()) {
            System.out.println(noOverlapCommand.getKey() + " | " + noOverlapCommand.getValue().getDescription());
        }
    }
}
