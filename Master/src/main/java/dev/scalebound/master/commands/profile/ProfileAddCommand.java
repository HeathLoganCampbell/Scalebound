package dev.scalebound.master.commands.profile;

import dev.scalebound.master.Scalebound;
import dev.scalebound.master.console.command.SubCommand;
import dev.sprock.scalebound.shared.commons.InputUtils;

public class ProfileAddCommand extends SubCommand
{
    private Scalebound scalebound;

    public ProfileAddCommand(Scalebound scalebound)
    {
        super("add", new String[] { "register" }, "Add a profile profiles");

        this.scalebound = scalebound;
    }

    @Override
    public void onExecution(String[] commandArgs)
    {
        if(commandArgs.length != 5)
        {
            this.log("Please use '/profile add <Name> <BufferServerCount> <MaxPlayerCount> <MaxRamMB> <BufferMaxPlayerCount>'");
            return;
        }

        String profileName = commandArgs[0];
        String bufferServerCountStr = commandArgs[1];
        String maxPlayerCountStr = commandArgs[2];
        String maxRamMBStr = commandArgs[3];
        String bufferMaxPlayerCountStr = commandArgs[4];

        if(!InputUtils.isNumeric(bufferServerCountStr))
        {
            this.log("Please use a number to describe BufferServerCount instead of '" + bufferServerCountStr + "'");
            return;
        }

        if(!InputUtils.isNumeric(maxPlayerCountStr))
        {
            this.log("Please use a number to describe MaxPlayerCount instead of '" + maxPlayerCountStr + "'");
            return;
        }

        if(!InputUtils.isNumeric(maxRamMBStr))
        {
            this.log("Please use a number to describe MaxRamMB instead of '" + maxRamMBStr + "'");
            return;
        }

        if(!InputUtils.isNumeric(bufferMaxPlayerCountStr))
        {
            this.log("Please use a number to describe BufferMaxPlayerCount instead of '" + bufferMaxPlayerCountStr + "'");
            return;
        }

        this.scalebound.getProfileRepository().addProfile(profileName, Integer.parseInt(bufferServerCountStr), Integer.parseInt(maxPlayerCountStr), Integer.parseInt(maxRamMBStr), Integer.parseInt(bufferMaxPlayerCountStr));
        log("Added Profile " + profileName);
        this.scalebound.reloadProfiles();
        log("Reloaded Profile");
    }
}
