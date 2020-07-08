package dev.scalebound.master.console.command;

import dev.scalebound.master.utils.Console;
import lombok.Getter;
import lombok.Setter;

public abstract class SubCommand extends Command
{
    @Getter @Setter
    private String parentCommand;

    public SubCommand(String command, String[] alises, String description)
    {
        super(command, alises, description);
    }


    @Override
    public void log(String message) {
        Console.log(this.parentCommand, message);
    }

    @Override
    public void log(String format, String... data) {
        System.out.format(format, data);
    }


}
