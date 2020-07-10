package dev.scalebound.shared.profiles;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProfileGenerateFile
{
    private String fileName;
    private String folder;
    private String content;
}
