package dev.scalebound.master.console.command;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class CommandMap
{
    private HashMap<String, Command> commands = new HashMap<>();
    private HashMap<String, Command> noOverLapCommands = new HashMap<>();

    public void registerCommand(Command command)
    {
        if(this.noOverLapCommands.containsKey(command.getCommand()))
        {
            System.out.println(command.getCommand() + " is being overwritten by " + command.getClass().getSimpleName());
        }
        this.noOverLapCommands.put(command.getCommand().toLowerCase(), command);
        this.commands.put(command.getCommand().toLowerCase(), command);
        for (String alise : command.getAlises()) {
            this.commands.put(alise.toLowerCase(), command);
        }
    }

    public Iterable<Map.Entry<String, Command>> getAllCommands()
    {
        return this.noOverLapCommands.entrySet();
    }

    public Command getCommand(String commandLabel)
    {
        return this.commands.get(commandLabel.toLowerCase());
    }

    public void handleCommand(String commandMsg)
    {
        if(commandMsg.length() == 0) return;
        final String[] rawArgs = commandMsg.split(" ");
        final String commandLabel = rawArgs[0];
        final Command command = this.commands.get(commandLabel.toLowerCase());

        if(command == null)
        {
            System.out.println("Unknown command '" + commandLabel + "'");
            return;
        }

        this.executeCommand(command, rawArgs);
    }



    public void executeCommand(Command command, String[] rawArgs)
    {
        int size = rawArgs.length - 1;
        String[] args = new String[size];
        System.arraycopy(rawArgs, 1, args, 0, size);
        command.execute(args);
    }

    public Iterable<Map.Entry<String, Command>> getNoOverlapCommands()
    {
        return this.noOverLapCommands.entrySet();
    }
}
