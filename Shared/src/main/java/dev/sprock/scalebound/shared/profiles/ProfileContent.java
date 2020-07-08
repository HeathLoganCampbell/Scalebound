package dev.sprock.scalebound.shared.profiles;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
//@NoArgsConstructor
@AllArgsConstructor
public class ProfileContent
{
    private List<ProfileDownloadLog> downloads;
    private List<ProfileGenerateFile> generateFiles;

    public ProfileContent()
    {
        this.downloads = new ArrayList<>();
        this.generateFiles = new ArrayList<>();
    }
}
