import Datacenter.Datacenter;

import java.io.IOException;
import java.util.Collections;

public class DatacenterOptimizer implements Runnable {
    private OptimizerController controller;
    private int currentBest;

    public DatacenterOptimizer(OptimizerController controller, int currentBest) {
        this.controller = controller;
        this.currentBest = currentBest;
    }

    @Override
    public void run() {
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

        while (true) {
            for (int i = 0; i < 10000; i++) {
                RobinHoodPooling.go(datacenter);
                int score = datacenter.getScore();

                if (score > getCurrentBest()) {
                    updateBest(score);
                    controller.updateBest(datacenter, getCurrentBest());
                }
            }

            datacenter.resetSlots();
            RoundRobinPlacement.go(datacenter);
            // System.out.println("Moving servers...");
        }
    }

    private synchronized int getCurrentBest() {
        return currentBest;
    }

    public synchronized void updateBest(int newBest) {
        currentBest = newBest;
    }
}
