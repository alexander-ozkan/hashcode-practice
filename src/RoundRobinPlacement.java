import Datacenter.Datacenter;
import Servers.Server;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class RoundRobinPlacement {
    public static void go(Datacenter datacenter) {
        List<Server> fits;
        Random random = new Random();
        int numChooseFrom = 4;
        for (int col = 0; col < datacenter.getCols(); col++) {
            for (int row = 0; row < datacenter.getRows(); row++) {
                if (datacenter.getCluster()[row][col]) {
                    continue;
                }

                fits = new ArrayList<>();

                for (Server server : datacenter.getAvailableServers()) {
                    if (datacenter.canPlaceServer(server, row, col)) {
                        fits.add(server);
                        if (fits.size() > numChooseFrom) break;
                    }
                }

                if (fits.size() != 0) {
                    if (fits.size() < numChooseFrom) {
                        datacenter.placeServer(fits.get(0), row, col);
                    }
                    else {
                        datacenter.placeServer(fits.get(random.nextInt(numChooseFrom)), row, col);
                    }
                }
            }
        }
    }
}
