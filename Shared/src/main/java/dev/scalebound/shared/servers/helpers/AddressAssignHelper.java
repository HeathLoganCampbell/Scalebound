package dev.scalebound.shared.servers.helpers;

import dev.scalebound.shared.commons.MathUtils;
import dev.scalebound.shared.servers.types.DedicatedServer;
import dev.scalebound.shared.servers.types.MinecraftServer;
import dev.scalebound.shared.servers.managers.DedicatedServerManager;
import dev.scalebound.shared.servers.managers.MinecraftServerManager;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class AddressAssignHelper
{
    public static final float RAM_USAGE_PRECENTAGE = 0.7f;
    public static final int BASE_PORT = 30000;
    public static final int RANGE_PORT = 1000;

    public boolean assignAddress(MinecraftServer minecraftServer, DedicatedServerManager dedicatedServerManager, MinecraftServerManager minecraftServerManager)
    {
        if (minecraftServer.isProxy())
        {
            int assignedPort = 25565;
            String assignedAddress = null;

            for (Map.Entry<String, DedicatedServer> serverEntry : dedicatedServerManager.getServers())
            {
                final DedicatedServer dediServer = serverEntry.getValue();
                if(!minecraftServerManager.isUsedPort(dediServer.getAddress(), assignedPort))
                {
                    assignedAddress = dediServer.getAddress();
                }
            }

            if(assignedAddress == null)
                return false;

            minecraftServer.setAddress(assignedAddress);
            minecraftServer.setPort(assignedPort);
            minecraftServerManager.propergateAddressData(minecraftServer);
            return true;
        }

        int minServerCount = 1000000;
        DedicatedServer bestDedicatedServer = null;

        final Iterator<Map.Entry<String, DedicatedServer>> iterator = dedicatedServerManager.getServers().iterator();
        while (iterator.hasNext())
        {
            final Map.Entry<String, DedicatedServer> entry = iterator.next();
            final DedicatedServer dedicatedServer = entry.getValue();
            final String address = dedicatedServer.getAddress();

            List<MinecraftServer> allServers = minecraftServerManager.getAllServerByAddress(address);
            if(allServers == null) allServers = new ArrayList<>();
            final int mcServersOnDedi = allServers.size();

            int usedRamMB = dedicatedServer.getUsedRamMB();
            if(usedRamMB == 0) usedRamMB = 1;

            if(minServerCount > mcServersOnDedi
                    && (usedRamMB / (float) dedicatedServer.getMaxRamMB()) < RAM_USAGE_PRECENTAGE)

            {
                minServerCount = mcServersOnDedi;
                bestDedicatedServer = dedicatedServer;
            }
        }

        if(bestDedicatedServer == null)
            return false;


        final String assignedAddress = bestDedicatedServer.getAddress();
        int port = BASE_PORT + MathUtils.getRandom().nextInt(RANGE_PORT);
        while(minecraftServerManager.isUsedPort(assignedAddress, port))
            port = BASE_PORT + MathUtils.getRandom().nextInt(RANGE_PORT);

        int assignedPort = port;

        minecraftServer.setAddress(assignedAddress);
        minecraftServer.setPort(assignedPort);
        minecraftServerManager.propergateAddressData(minecraftServer);
        return true;
    }
}
