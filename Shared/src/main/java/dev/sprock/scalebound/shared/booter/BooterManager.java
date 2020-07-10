package dev.sprock.scalebound.shared.booter;

import com.jcraft.jsch.*;
import dev.sprock.scalebound.shared.profiles.*;
import dev.sprock.scalebound.shared.servers.managers.ProfileManager;
import dev.sprock.scalebound.shared.servers.types.MinecraftServer;
import sun.java2d.cmm.Profile;

import java.io.File;
import java.io.InputStream;

import com.jcraft.*;
/**
 * Remotely starts up servers
 * - Adds start up script
 * -
 */
public class BooterManager
{
    public static final String SSH_USERNAME = "scalebound";

    private JSch ssh;
    public BooterManager()
    {
        this.ssh = new JSch();
    }

    private String injectPlaceholders(String command, MinecraftServer minecraftServer, ServerProfile profile)
    {
        return command
                .replace("%SERVER_NAME%", minecraftServer.getServerName())
                .replace("%SERVER_ADDRESS%", minecraftServer.getAddress())
                .replace("%SERVER_PORT%", minecraftServer.getPort() + "")
                .replace("%SERVER_PROFILE_ID%", minecraftServer.getProfileId() + "")
                .replace("%SERVER_PROFILE_NAME%", profile.getName())
                .replace("%SERVER_MAX_RAM_MB%", minecraftServer.getMaxRamMB() + "")
                .replace("%SERVER_MAX_PLAYER_COUNT%", minecraftServer.getMaxPlayerCount() + "");
    }

    public void startServer(MinecraftServer minecraftServer, ProfileManager profileManager) {
        final ServerProfile profile = profileManager.getProfileById(minecraftServer.getProfileId());

        final ProfileContent content = profile.getContent();
        StartUpScriptBuilder script = new StartUpScriptBuilder();
        for (ProfileRawCommand rawCommand : content.getRawCommands()) {
            script.addLine(injectPlaceholders(rawCommand.getCommand(), minecraftServer, profile));
        }

        executeCommand("ubuntu", minecraftServer.getAddress(), script.toString());
    }

    public void stopServer(MinecraftServer minecraftServer)
    {

        executeCommand("ubuntu", minecraftServer.getAddress(), "for session in $(screen -ls | grep -o '[0-9]*\\." + minecraftServer.getServerName() + "'); do screen -S \"${session}\" -X quit; done");
    }

    private void executeCommand(String user, String host, String command)
    {
        try{
            System.out.println(command);
            java.util.Properties config = new java.util.Properties();
            config.put("StrictHostKeyChecking", "no");
            JSch jsch = new JSch();
            File privateKeyFile = new File("C:\\Users\\GGPC\\.ssh\\DreamTestNetwork\\rsa");

            jsch.addIdentity(privateKeyFile.getAbsolutePath());
            Session session=jsch.getSession(user, host, 22);
            session.setConfig(config);
            session.connect();

            Channel channel=session.openChannel("exec");
            ((ChannelExec)channel).setCommand(command);
            channel.setInputStream(null);
            ((ChannelExec)channel).setErrStream(System.err);

            InputStream in=channel.getInputStream();
            channel.connect();
            byte[] tmp= new byte[1024];
            while(true)
            {
                while(in.available()>0)
                {
                    int i=in.read(tmp, 0, 1024);
                    if(i<0)break;
                    System.out.print(new String(tmp, 0, i));
                }
                if(channel.isClosed()){
                    System.out.println("Finished");
                    break;
                }
                try
                {
                    Thread.sleep(1000);
                }
                catch(Exception ex)
                {

                }
            }
            channel.disconnect();
            session.disconnect();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    //for session in $(screen -ls | grep -o \'[0-9]*\\.%SERVER_NAME%\'); do screen -S \"${session}\" -X quit; done\n
}
