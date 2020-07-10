package dev.scalebound.master.commands.profile.edit;

import dev.scalebound.master.Scalebound;
import dev.scalebound.master.console.command.SubCommand;
import dev.scalebound.shared.profiles.ProfileGenerateFile;
import dev.scalebound.shared.profiles.ServerProfile;

// /Profile edit generate <ProfileName> <Folder (. = null)> <FileName> <content...>
// /prof e gen HUB config/ database.json { "username": "cookie", "Password": "Hello" }
//prof e gen HUB . database.json { "username": "cookie", "Password": "Hello" }
public class ProfileEditGeneratesCommand extends SubCommand
{
    private Scalebound scalebound;

    public ProfileEditGeneratesCommand(Scalebound scalebound)
    {
        super("generate", new String[] { "gen", "generate" }, "Edit a profile profiles");

        this.scalebound = scalebound;
    }

    @Override
    public void onExecution(String[] commandArgs)
    {
        if(commandArgs.length < 3)
        {
            this.log("Please use 'Profile edit generate <ProfileName> <Folder (. = null)> <FileName> <content...>'");
            return;
        }

        String profileName = commandArgs[0];
        String folderName = commandArgs[1];
        String fileName = commandArgs[2];

        StringBuilder contentStr = new StringBuilder();
        for (int i = 3; i < commandArgs.length; i++) {
            contentStr.append(commandArgs[i]).append(" ");
        }
        contentStr.setLength(contentStr.length() - 2);

        String folder = folderName;
        if(fileName.equalsIgnoreCase(".")) folder = null;


        final ServerProfile profile = this.scalebound.getProfileManager().getProfileByName(profileName);
        if(profile == null)
        {
            log(profile.getName() + " does not exist.");
            return;
        }

        final ProfileGenerateFile profileGenerateFile = new ProfileGenerateFile(fileName, folder, contentStr.toString());
        profile.getContent().getGenerateFiles().add(profileGenerateFile);
        this.scalebound.getProfileRepository().updateProfileContent(profile);

        log("Updated Profile " + profileName);
        this.scalebound.reloadProfiles();
        log("Reloaded Profile");
    }
}
