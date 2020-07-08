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

    public static void main(String[] args) {

        String jenkinsUsername = "sprock";
        String jenkinsToken = "11966b20dd60cc5e083e0e6fea81e3aaba";
        StartUpScriptBuilder script = new StartUpScriptBuilder();

        script.addLine("echo 'Rarrr'");
        script.addLine("echo 'Downloading'");
        script.addLine("pwd");
        script.addLine("mkdir Plugins");
        script.addLine("curl -u " + jenkinsUsername + ":" + jenkinsToken + " -o Plugins/Library.jar \"http://51.222.35.161:8080/job/DreamTestNetwork/job/\\$1/job/master/lastSuccessfulBuild/execution/node/3/ws/Library.jar\"");
        script.addLine("pwd");
        script.addLine("cd Plugins");
        script.addLine("ls");

        new BooterManager().executeCommand("ubuntu", "ns517441.ip-158-69-27.net", script.toString());

    }
    //--port %SERVER_PORT% --host 0.0.0.0 --max-players %SERVER_MAX_PLAYER_COUNT%

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
        //Download spigot/paper/bukkit/velocity/bungee
        //Download Plugins
        //Download Configs
        final ServerProfile profile = profileManager.getProfileById(minecraftServer.getProfileId());

        final ProfileContent content = profile.getContent();
        StartUpScriptBuilder script = new StartUpScriptBuilder();
        for (ProfileRawCommand rawCommand : content.getRawCommands()) {
            script.addLine(injectPlaceholders(rawCommand.getCommand(), minecraftServer, profile));
        }

        executeCommand("ubuntu", minecraftServer.getAddress(), script.toString());
//        final Session session = this.ssh.getSession(SSH_USERNAME, minecraftServer.getAddress(), 22);
//        session.connect();
//
//
//
//
//
//
//
//
//
//
//        ProcessRunner processRunner = new ProcessRunner(
//                file,
//                "/bin/sh",
//                "remoteStart.sh",
//                minecraftServer.getAddress(),
//                minecraftServer.getPort() + "",
//                minecraftServer.getMaxRamMB() + "",
//                minecraftServer.getServerName(),
//                profile.getName()
//        );
//
//        processRunner.start((error) -> {
//            if(error)
//            {
//                System.out.println("Error creating server");
//            }
//            else
//            {
//                System.out.println("server created");
//            }
//        });
//
//        try {
//            processRunner.join(100L);
//        } catch (InterruptedException e1) {
//            e1.printStackTrace();
//        }
//
//        if (!processRunner.isDone())
//            System.out.println("Still going");;
    }

    public void stopServer(MinecraftServer minecraftServer)
    {
        ProcessRunner processRunner = new ProcessRunner(
                new File("./scripts"),
                "/bin/sh",
                "remoteKill.sh",
                minecraftServer.getAddress(),
                minecraftServer.getServerName()
        );
        processRunner.start((error) -> {
            if(error)
            {
                System.out.println("Error killing server");
            }
            else
            {
                System.out.println("server killed");
            }
        });

        try {
            processRunner.join(50L);
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }

        if (!processRunner.isDone())
            System.out.println("Still going");;
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
