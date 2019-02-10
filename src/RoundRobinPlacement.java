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
        for (int col = 0; col < datacenter.getCols(); col++) {
            for (int row = 0; row < datacenter.getRows(); row++) {
                fits = new ArrayList<>();

                for (Server server : datacenter.getAvailableServers()) {
                    if (datacenter.canPlaceServer(server, row, col)) {
                        fits.add(server);
                    }
                }

                if (fits.size() != 0) {
                    if (fits.size() < 3) {
                        datacenter.placeServer(fits.get(0), row, col);
                    }
                    else {
                        datacenter.placeServer(fits.get(random.nextInt(3)), row, col);
                    }
                }
            }
        }
    }
}
