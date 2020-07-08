package dev.scalebound.master.commands.profile.edit;

import dev.scalebound.master.Scalebound;
import dev.scalebound.master.console.command.SubCommand;
import dev.sprock.scalebound.shared.profiles.ProfileDownloadLog;
import dev.sprock.scalebound.shared.profiles.ServerProfile;

public class ProfileEditDownloadsCommand extends SubCommand
{
    private Scalebound scalebound;

    public ProfileEditDownloadsCommand(Scalebound scalebound)
    {
        super("download", new String[] { "d" }, "Edit a profile profiles");

        this.scalebound = scalebound;
    }

    @Override
    public void onExecution(String[] commandArgs)
    {
        if(commandArgs.length < 3)
        {
            this.log("Please use 'Profile edit generate <ProfileName> <FileName> <url> [folder]'");
            return;
        }

        String profileName = commandArgs[0];
        String fileName = commandArgs[1];
        String url = commandArgs[2];
        String folder = null;
        if(commandArgs.length == 3) folder = commandArgs[3];


        final ServerProfile profile = this.scalebound.getProfileManager().getProfileByName(profileName);
        if(profile == null)
        {
            log(profile.getName() + " does not exist.");
            return;
        }

        final ProfileDownloadLog profileDownloadLog = new ProfileDownloadLog(fileName, folder, url);
        profile.getContent().getDownloads().add(profileDownloadLog);
        this.scalebound.getProfileRepository().updateProfileContent(profile);

        log("Updated Profile " + profileName);
        this.scalebound.reloadProfiles();
        log("Reloaded Profile");
    }
}
