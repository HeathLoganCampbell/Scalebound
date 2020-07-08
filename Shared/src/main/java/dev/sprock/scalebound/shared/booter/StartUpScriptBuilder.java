package dev.sprock.scalebound.shared.booter;

public class StartUpScriptBuilder
{
    public StringBuilder scriptContent = new StringBuilder();

    public StartUpScriptBuilder()
    {
//        this.scriptContent.append("#!/bin/sh");
    }

    public void addLine(String line)
    {
        this.scriptContent.append(line).append("\n");
    }

    public String toString()
    {
        return scriptContent.toString();
    }
}
