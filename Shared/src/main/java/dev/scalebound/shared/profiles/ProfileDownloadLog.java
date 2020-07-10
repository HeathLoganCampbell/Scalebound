package dev.scalebound.shared.profiles;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class ProfileDownloadLog
{
    private String fileName;
    private String folder = null;
    private String url;
}
