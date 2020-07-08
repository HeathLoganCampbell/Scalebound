package dev.scalebound.master.commands.profile;

import dev.scalebound.master.Scalebound;
import dev.scalebound.master.console.command.SubCommand;

public class ProfileReloadCommand extends SubCommand
{
    private Scalebound scalebound;

    public ProfileReloadCommand(Scalebound scalebound)
    {
        super("Reload", new String[] { "rl" }, "Reload a profile profiles");

        this.scalebound = scalebound;
    }

    @Override
    public void onExecution(String[] commandArgs)
    {
        this.scalebound.reloadProfiles();
        this.log("Reloaded Profiles from database");
    }
}
