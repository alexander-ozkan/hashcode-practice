import Datacenter.Datacenter;
import Servers.Server;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class RobinHoodPooling {
    public static void go(Datacenter datacenter) {

        int lowestCapacity, highestCapacity;

        Random rnd = new Random();
        List<Server> usedServers = datacenter.getUsedServers();

        int counter = 0;

        List<Integer> randPools = null;

        // apply random server pools

        List<ArrayList<Server>> rowsUsedServers = datacenter.getRowsUsedServers();

        for (ArrayList<Server> listRow : rowsUsedServers) {
            for (Server server : listRow) {
                if (randPools == null || randPools.size() == 0) {
                    randPools = new ArrayList<>(datacenter.getNumPools());
                    for (int i = 0; i < datacenter.getNumPools(); i++) randPools.add(i);
                    Collections.shuffle(randPools);
                }

                server.setPool(randPools.remove(randPools.size() - 1));
            }
        }

        int[] poolCaps = datacenter.getPoolCapacities();
        // redistribute pool allocations
        for (int n = 0; n < 150; n++) {
            int highest = Integer.MIN_VALUE;
            int lowest = Integer.MAX_VALUE;
            int highPool = 0;
            int lowPool = 0;

            for (int i = 0; i < poolCaps.length; i++) {
                if (poolCaps[i] > highest) {
                    highest = poolCaps[i];
                    highPool = i;
                }

                if (poolCaps[i] < lowest) {
                    lowest = poolCaps[i];
                    lowPool = i;
                }
            }

            int highSwapIndex = -1;
            int lowSwapIndex = -1;
            int smallestCap = Integer.MAX_VALUE;
            int biggestCap = Integer.MIN_VALUE;

            for (int i = 0; i < usedServers.size(); i++) {
                Server server = usedServers.get(i);
                if (server.getPool() == lowPool && server.getCapacity() < smallestCap) {
                    smallestCap = server.getCapacity();
                    lowSwapIndex = i;
                }
            }

            for (int i = 0; i < usedServers.size(); i++) {
                Server server = usedServers.get(i);
                if (server.getPool() == highPool && server.getCapacity() > biggestCap) {
                    biggestCap = server.getCapacity();
                    highSwapIndex = i;
                }
            }

            // swap allocation
            if (highSwapIndex != -1 && lowSwapIndex != -1) {
                usedServers.get(highSwapIndex).setPool(lowPool);
                usedServers.get(lowSwapIndex).setPool(highPool);

                poolCaps[highPool] -= usedServers.get(highSwapIndex).getCapacity();
                poolCaps[highPool] += usedServers.get(lowSwapIndex).getCapacity();

                poolCaps[lowPool] -= usedServers.get(lowSwapIndex).getCapacity();
                poolCaps[lowPool] += usedServers.get(highSwapIndex).getCapacity();
            }
        }
    }
}
