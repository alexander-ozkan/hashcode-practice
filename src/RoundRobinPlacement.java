import Datacenter.Datacenter;
import Servers.Server;

public class RoundRobinPlacement {
    public static void go(Datacenter datacenter) {
        for (int row = 0; row < datacenter.getRows(); row++) {
            for (int col = 0; col < datacenter.getCols(); col++) {
                for (Server server : datacenter.getAvailableServers()) {
                    if (datacenter.canPlaceServer(server, row, col)) {
                        datacenter.placeServer(server, row, col);
                        break;
                    }
                }
            }
        }
    }
}
