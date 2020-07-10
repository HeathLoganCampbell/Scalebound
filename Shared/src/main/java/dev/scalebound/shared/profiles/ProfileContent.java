package dev.scalebound.shared.profiles;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
//@NoArgsConstructor
@AllArgsConstructor
public class ProfileContent
{
    private List<ProfileDownloadLog> downloads;
    private List<ProfileGenerateFile> generateFiles;
    private List<ProfileRawCommand> rawCommands;

    public ProfileContent()
    {
        this.downloads = new ArrayList<>();
        this.generateFiles = new ArrayList<>();
        this.rawCommands = new ArrayList<>();
    }
}
