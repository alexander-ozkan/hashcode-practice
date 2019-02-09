import Datacenter.Datacenter;

import java.io.IOException;
import java.util.Collections;

public class Main {
    public static void main(String[] args) {
        Datacenter datacenter = null;
        try {
            datacenter = InputParser.parseFile("./dc.in");
        }
        catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }

        Collections.sort(datacenter.getAvailableServers(), Collections.reverseOrder());

        // cull the bad servers
//        for (int i = 0; i < 80; i++) {
//            datacenter.getAvailableServers().remove(datacenter.getAvailableServers().size() - 1);
//        }

        RoundRobinPlacement.go(datacenter);

        int best = -1;

        while (true) {
            for (int i = 0; i < 10000; i++) {
                RobinHoodPooling.go(datacenter);
                int score = datacenter.getScore();

                if (score > best) {
                    best = score;
                    System.out.println("New best: " + score);
                }
            }

            datacenter.resetSlots();
            RoundRobinPlacement.go(datacenter);
            //System.out.println("Moving servers...");
        }
    }
}
