package dev.scalebound.master.commands.profile.edit;

import dev.scalebound.master.Scalebound;
import dev.scalebound.master.console.command.SubCommand;

public class ProfileEditCommand extends SubCommand
{
    private Scalebound scalebound;

    public ProfileEditCommand(Scalebound scalebound)
    {
        super("edit", new String[] { "e" }, "Edit a profile profiles");

        this.scalebound = scalebound;

        this.registerSubCommand(new ProfileEditDownloadsCommand(scalebound));
        this.registerSubCommand(new ProfileEditGeneratesCommand(scalebound));
    }

    @Override
    public void onExecution(String[] commandArgs)
    {

    }
}
