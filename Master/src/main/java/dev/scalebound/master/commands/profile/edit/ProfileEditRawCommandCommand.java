package dev.scalebound.master.commands.profile.edit;

import dev.scalebound.master.Scalebound;
import dev.scalebound.master.console.command.SubCommand;
import dev.scalebound.shared.profiles.ProfileRawCommand;
import dev.scalebound.shared.profiles.ServerProfile;

// /Profile edit generate <ProfileName> <Folder (. = null)> <FileName> <content...>
// /prof e gen HUB config/ database.json { "username": "cookie", "Password": "Hello" }
//prof e gen HUB . database.json { "username": "cookie", "Password": "Hello" }
public class ProfileEditRawCommandCommand extends SubCommand
{
    private Scalebound scalebound;

    public ProfileEditRawCommandCommand(Scalebound scalebound)
    {
        super("command", new String[] { "con", "rawcommand" }, "Edit a profile profiles");

        this.scalebound = scalebound;
    }

    @Override
    public void onExecution(String[] commandArgs)
    {
        if(commandArgs.length < 2)
        {
            this.log("Please use 'Profile edit generate <ProfileName> <Commad...>'");
            return;
        }

        String profileName = commandArgs[0];

        StringBuilder contentStr = new StringBuilder();
        for (int i = 1; i < commandArgs.length; i++) {
            contentStr.append(commandArgs[i]).append(" ");
        }
        contentStr.setLength(contentStr.length() - 2);

        final ServerProfile profile = this.scalebound.getProfileManager().getProfileByName(profileName);
        if(profile == null)
        {
            log(profile.getName() + " does not exist.");
            return;
        }

        final ProfileRawCommand rawCommand = new ProfileRawCommand(contentStr.toString());
        profile.getContent().getRawCommands().add(rawCommand);
        this.scalebound.getProfileRepository().updateProfileContent(profile);

        log("Updated Profile " + profileName);
        this.scalebound.reloadProfiles();
        log("Reloaded Profile");
    }
}
