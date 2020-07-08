package dev.scalebound.master.commands.profile;

import dev.scalebound.master.Scalebound;
import dev.scalebound.master.console.command.Command;
import dev.scalebound.master.commands.profile.edit.ProfileEditCommand;

import java.util.Map;

public class ProfileCommand extends Command
{
    private Scalebound scalebound;

    public ProfileCommand(Scalebound scalebound)
    {
        super("Profile", new String[] { "prof", "pf"}, "Manage Profiles");

        this.scalebound = scalebound;
        this.registerSubCommand(new ProfileListCommand(this.scalebound));
        this.registerSubCommand(new ProfileAddCommand(this.scalebound));
        this.registerSubCommand(new ProfileRemoveCommand(this.scalebound));
        this.registerSubCommand(new ProfileReloadCommand(this.scalebound));
        this.registerSubCommand(new ProfileEditCommand(this.scalebound));
    }

    @Override
    public void onExecution(String[] commandArgs)
    {
        for (Map.Entry<String, Command> noOverlapCommand : this.getSubCommandMap().getNoOverlapCommands()) {
            System.out.println(noOverlapCommand.getKey() + " | " + noOverlapCommand.getValue().getDescription());
        }
    }
}
