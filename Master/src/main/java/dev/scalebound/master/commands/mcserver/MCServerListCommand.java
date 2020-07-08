package dev.scalebound.master.commands.mcserver;

import dev.scalebound.master.Scalebound;
import dev.scalebound.master.console.command.SubCommand;
import dev.sprock.scalebound.shared.profiles.ServerProfile;
import dev.sprock.scalebound.shared.servers.types.MinecraftServer;

import java.util.Map;

// /MCServer view
public class MCServerListCommand extends SubCommand
{
    private Scalebound scalebound;

    public MCServerListCommand(Scalebound scalebound)
    {
        super("list", new String[] { "view", "all" }, "view all Servers");

        this.scalebound = scalebound;
    }

    @Override
    public void onExecution(String[] commandArgs)
    {
        boolean showAll = true;
        int profileId = 0;
        if(commandArgs.length == 1)
        {
            showAll = false;
            String profileName = commandArgs[0];
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

            profileId = profile.getProfileId();
        }

        String format = "|%1$-12s|%2$-20s|%3$-20s|%4$-20s|%5$-20s|%6$-20s|%7$-20s|\n";
        this.log(format, "SERVER NAME", "PROFILE NAME", "ADDRESS", "PORT", "TPS", "1 MIN TPS", "AVG PING");

        for (Map.Entry<String, MinecraftServer> serverEntry : this.scalebound.getMinecraftServerManager().getServers())
        {
            final MinecraftServer server = serverEntry.getValue();
            if(!showAll && profileId != server.getProfileId())
                continue;

            final ServerProfile profile = this.scalebound.getProfileManager().getProfileById(server.getProfileId());
            String profileName = "UNKNOWN";
            if(profile != null)
                profileName = profile.getName();

            this.log(format, server.getServerName(), profileName, server.getAddress(), server.getPort() + "", server.getTps() + "", server.getTps1min() + "", server.getAvgPing() + "");
        }
    }
}
