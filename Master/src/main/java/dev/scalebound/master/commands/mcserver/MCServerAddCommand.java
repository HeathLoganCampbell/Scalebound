package dev.scalebound.master.commands.mcserver;

import dev.scalebound.master.Scalebound;
import dev.scalebound.master.console.command.SubCommand;
import dev.sprock.scalebound.shared.commons.InputUtils;
import dev.sprock.scalebound.shared.profiles.ServerProfile;

import java.util.Map;

// /MCServer add <Name> <Profile> [override RamMB] [override MaxPlayerCount]
public class MCServerAddCommand extends SubCommand
{
    private Scalebound scalebound;

    public MCServerAddCommand(Scalebound scalebound)
    {
        super("add", new String[] { "register" }, "add Minecraft Servers");

        this.scalebound = scalebound;
    }

    @Override
    public void onExecution(String[] commandArgs)
    {
        if(commandArgs.length < 2)
        {
            this.log("Please do '/MCServer add <ServerName> <ProfileName> [override RamMB] [override MaxPlayerCount]'");
            return;
        }

        String serverName = commandArgs[0];
        String profileName = commandArgs[1];

        final ServerProfile profile = this.scalebound.getProfileManager().getProfileByName(profileName);
        if(profile == null)
        {
            log("Profile " + profileName + " does not exist.");
            StringBuilder builder = new StringBuilder();
            for (Map.Entry<String, ServerProfile> profileEntry : this.scalebound.getProfileManager().getProfiles())
                builder.append(profileEntry.getKey()).append(", ");
            builder.setLength(builder.length() - 2);
            log("Profiles: " + builder.toString());
            return;
        }

        int ramMB = profile.getMaxRamMB();
        int maxPlayerCount = profile.getMaxPlayerCount();

        if(commandArgs.length == 4)
        {
            String ramMBStr = commandArgs[0];
            String maxPlayerCountStr = commandArgs[1];

            if(!InputUtils.isNumeric(ramMBStr))
            {
                log("Please give a number for ram instaed of '" + ramMBStr + "'");
                return;
            }

            if(!InputUtils.isNumeric(maxPlayerCountStr))
            {
                log("Please give a number for max player count instaed of '" + maxPlayerCountStr + "'");
                return;
            }

            ramMB = Integer.parseInt(ramMBStr);
            maxPlayerCount = Integer.parseInt(maxPlayerCountStr);
        }

        this.scalebound.getMinecraftServerRepository().addMinecraftServer(serverName, ramMB, maxPlayerCount, System.currentTimeMillis(), profile);
        this.log("Created server of type '" + profileName + "' called " + serverName + ", Assigning IP and port soon");
    }
}
