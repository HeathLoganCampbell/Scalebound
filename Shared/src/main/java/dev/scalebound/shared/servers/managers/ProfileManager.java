package dev.scalebound.shared.servers.managers;

import dev.scalebound.shared.profiles.ServerProfile;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ProfileManager
{
    private HashMap<String, ServerProfile> profilesMap = new HashMap<>();
    private HashMap<Integer, ServerProfile> profileIdMap = new HashMap<>();

    public void registerProfile(ServerProfile serverProfile)
    {
        this.profilesMap.put(serverProfile.getName(), serverProfile);
        this.profileIdMap.put(serverProfile.getProfileId(), serverProfile);
    }

    public void unregisterProfile(ServerProfile serverProfile)
    {
        this.profilesMap.remove(serverProfile.getName());
        this.profileIdMap.remove(serverProfile.getProfileId());
    }

    public ServerProfile getProfileByName(String name)
    {
        return this.profilesMap.get(name);
    }

    public ServerProfile getProfileById(int profileId)
    {
        return this.profileIdMap.get(profileId);
    }

    public void clearProfiles()
    {
        this.profilesMap.clear();
    }

    public Set<Map.Entry<String, ServerProfile>> getProfiles()
    {
        return this.profilesMap.entrySet();
    }
}
