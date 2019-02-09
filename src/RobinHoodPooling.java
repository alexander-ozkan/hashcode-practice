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

        for (Server server : usedServers) {
            if (randPools == null || randPools.size() == 0) {
                randPools = new ArrayList<>(datacenter.getNumPools());
                for (int i = 0; i < datacenter.getNumPools(); i++) randPools.add(i);
                Collections.shuffle(randPools);
            }

            server.setPool(randPools.remove(randPools.size() - 1));


            //server.setPool((counter++) % datacenter.getNumPools());
        }

//        int[] poolCapacity = new int[datacenter.getNumPools()];
//        for (int i = 0; i < datacenter.getRows(); i++) {
//            //Compute lowest capacity of all pools
//            for(int j = 0; j < datacenter.getUsedServers().size(); j++)
//            {
//                Server server = usedServers.get(i);
//                // ignore server if on row that is down
//                if (server.getRow() != i) {
//                    poolCapacity[server.getPool()] += server.getCapacity();
//                }
//            }
//        }

    }
}
