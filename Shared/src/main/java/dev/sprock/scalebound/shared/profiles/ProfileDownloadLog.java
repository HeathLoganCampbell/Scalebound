package dev.sprock.scalebound.shared.profiles;

import lombok.AllArgsConstructor;
import lombok.ToString;

@ToString
@AllArgsConstructor
public class ProfileDownloadLog
{
    private String fileName;
    private String folder = null;
    private String url;
}
