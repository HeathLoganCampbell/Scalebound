package dev.scalebound.shared.servers.repositories;

import dev.scalebound.shared.profiles.ProfileContent;
import dev.scalebound.shared.commons.GsonUtils;
import dev.scalebound.shared.database.MySQLDatabase;
import dev.scalebound.shared.database.ProfileDatabase;
import dev.scalebound.shared.profiles.ServerProfile;
import lombok.SneakyThrows;

import java.util.List;

public class ProfileRepository extends RepositoryBase
{
    public ProfileRepository(MySQLDatabase database)
    {
        super(database);
    }

    @SneakyThrows
    public List<ServerProfile> getAllProfiles() {
        return this.getTransformerResult(ProfileDatabase.PROFILE_FETCH_ALL, ServerProfile.class, (rs) ->  {
            final ServerProfile profile = new ServerProfile();
            profile.load(rs);
            return profile;
        });
    }


    public void addProfile(String profileName, int serverBuffer, int maxPlayerCount, int maxRamMB, int bufferMaxPlayerCount)
    {
        ProfileContent profileContent = new ProfileContent();
        final String jsonContent = GsonUtils.getGson().toJson(profileContent);
        this.execute(ProfileDatabase.PROFILE_INSERT, (prepared) -> {
            prepared.setString(1, profileName);
            prepared.setInt(2, serverBuffer);
            prepared.setString(3, jsonContent);
            prepared.setInt(4, maxRamMB);
            prepared.setInt(5, maxPlayerCount);
            prepared.setInt(6, bufferMaxPlayerCount);
        });
    }

    public void updateProfileContent(ServerProfile profile)
    {
        ProfileContent profileContent = profile.getContent();
        final String jsonContent = GsonUtils.getGson().toJson(profileContent);
        this.execute(ProfileDatabase.PROFILE_UPDATE_CONTENT_PROFILE_NAME, (prepared) -> {
            prepared.setString(1, jsonContent);
            prepared.setString(2, profile.getName());
        });
    }

    public void removeProfileByName(String profileName)
    {
        this.execute(ProfileDatabase.PROFILE_DELETE_PROFILE_NAME, (prepared) -> {
            prepared.setString(1, profileName);
        });
    }

    public void removeProfileById(int profileId)
    {
        this.execute(ProfileDatabase.PROFILE_DELETE_PROFILE_ID, (prepared) -> {
            prepared.setInt(1, profileId);
        });
    }
}
