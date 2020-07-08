package dev.scalebound.master.commands.profile;

import dev.scalebound.master.Scalebound;
import dev.scalebound.master.console.command.SubCommand;
import dev.sprock.scalebound.shared.profiles.ServerProfile;

import java.util.Map;

public class ProfileListCommand extends SubCommand
{
    private Scalebound scalebound;

    public ProfileListCommand(Scalebound scalebound)
    {
        super("list", new String[] { "view", "l", "all" }, "View all profiles");

        this.scalebound = scalebound;
    }

    @Override
    public void onExecution(String[] commandArgs)
    {
        String format = "|%1$-12s|%2$-20s|%3$-30s|%4$-20s|%5$-20s|\n";
        this.log(format, "PROFILE NAME", "BUFFER SERVERS", "BUFFER MAX PLAYER COUNT", "MAX RAM MB", "MAX PLAYER COUNT");

        for (Map.Entry<String, ServerProfile> serverEntry : this.scalebound.getProfileManager().getProfiles())
        {
            final ServerProfile profile = serverEntry.getValue();

            this.log(format, profile.getName(), profile.getBufferServers() + "", profile.getBufferMaxPlayerCount() + "", profile.getMaxRamMB() + "", profile.getMaxPlayerCount() + "");
        }
    }
}
