package dev.scalebound.master.commands.profile;

import dev.scalebound.master.Scalebound;
import dev.scalebound.master.console.command.SubCommand;
import dev.scalebound.shared.profiles.ServerProfile;

public class ProfileRemoveCommand extends SubCommand
{
    private Scalebound scalebound;

    public ProfileRemoveCommand(Scalebound scalebound)
    {
        super("Remove", new String[] { "rm" }, "Add a profile profiles");

        this.scalebound = scalebound;
    }

    @Override
    public void onExecution(String[] commandArgs)
    {
        if(commandArgs.length != 1)
        {
            this.log("Please use '/profile rm <ProfileName>'");
            return;
        }

        String profileName = commandArgs[0];

        final ServerProfile profile = this.scalebound.getProfileManager().getProfileByName(profileName);
        if(profile == null)
        {
            this.log("Server " + profileName + " does not exist");
            return;
        }

        this.scalebound.getProfileRepository().removeProfileByName(profileName);
        log("Removed " + profileName);

        this.scalebound.reloadProfiles();
        this.log("reloaded Profiles from database");
    }
}
