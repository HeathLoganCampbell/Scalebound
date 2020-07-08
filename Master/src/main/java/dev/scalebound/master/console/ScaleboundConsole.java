package dev.scalebound.master.console;

import dev.scalebound.master.Scalebound;
import dev.scalebound.master.commands.FullResetCommand;
import dev.scalebound.master.console.command.CommandMap;
import dev.scalebound.master.commands.HelpCommand;
import dev.scalebound.master.commands.dedi.DediCommand;
import dev.scalebound.master.commands.mcserver.McServerCommand;
import dev.scalebound.master.commands.profile.ProfileCommand;
import net.minecrell.terminalconsole.SimpleTerminalConsole;
import org.jline.reader.Candidate;
import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ScaleboundConsole extends SimpleTerminalConsole
{
    private static final Logger log = LoggerFactory.getLogger(ScaleboundConsole.class);

    private Scalebound scalebound;
    private CommandMap commandMap;

    public ScaleboundConsole(Scalebound scalebound)
    {
        this.scalebound = scalebound;

        this.commandMap = new CommandMap();
        this.commandMap.registerCommand(new HelpCommand(this.commandMap));
        this.commandMap.registerCommand(new DediCommand(scalebound));
        this.commandMap.registerCommand(new ProfileCommand(scalebound));
        this.commandMap.registerCommand(new McServerCommand(scalebound));
        this.commandMap.registerCommand(new FullResetCommand(scalebound));
    }


    @Override
    protected boolean isRunning() {
        return true;
    }

    @Override
    protected void runCommand(String command)
    {
        this.commandMap.handleCommand(command);
    }

    @Override
    protected LineReader buildReader(LineReaderBuilder builder) {
        return super.buildReader(builder
                .appName("Scalebound")
                .completer((reader, parsedLine, list) -> {
                    try {
                        list.add(new Candidate("Example"));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                })
        );
    }

    @Override
    protected void shutdown()
    {

    }

}
