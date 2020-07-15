package dev.scalebound.master.settings;

import lombok.Getter;

@Getter
public class MasterConfig
{
    public String privateKeyFile = "~/.ssh/id_rsa";
    public String SSHUsername = "scalebound";
    public String SSHPassword = "scalebound";
}
