package dev.scalebound.master.console.command;

import dev.scalebound.master.utils.Console;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Map;

@Getter
public abstract class Command
{
    private String command;
    private String[] alises;
    private String description;
    private CommandMap subCommandMap;

    public Command(String command, String[] alises, String description)
    {
        this.command = command;
        this.alises = alises;
        this.description = description;
    }

    public void registerSubCommand(SubCommand subCommand)
    {
        if(this.subCommandMap == null)
            this.subCommandMap = new CommandMap();
        this.subCommandMap.registerCommand(subCommand);
        subCommand.setParentCommand(this.getCommand());
        if (subCommand.getSubCommandMap() != null) {
            for (Map.Entry<String, Command> commandEntry : subCommand.getSubCommandMap().getAllCommands()) {
                final Command command = commandEntry.getValue();
                if(command instanceof SubCommand)
                {
                    SubCommand childCommand = (SubCommand) command;
                    childCommand.setParentCommand(this.getCommand());
                }
            }
        }
    }

    public void log(String message)
    {
        Console.log(this.command, message);
    }

    public void log(String format, String... data)
    {
        System.out.format(format, data);
    }

    public void execute(String[] commandArgs)
    {

        if(subCommandMap != null)
        {
            if (commandArgs.length >= 1) {

                final String subCommand = commandArgs[0];
                final Command command = this.subCommandMap.getCommand(subCommand.toLowerCase());
                if (command != null) {
                    this.subCommandMap.executeCommand(command, commandArgs);
                    return;
                }
                else
                {
                    this.log("Unknown sub command '" + subCommand + "' for '" + this.getCommand() + "'!");
                }
            }
        }

        this.onExecution(commandArgs);
    }

    public abstract void onExecution(String[] commandArgs);
}
