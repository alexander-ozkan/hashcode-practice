import Datacenter.Datacenter;

import javax.xml.crypto.Data;
import java.io.*;
import java.util.Collections;

public class OptimizerController {
    private int currentBest;
    DatacenterOptimizer[] optimizers;

    public void go() {
        int currentBest = readBestFromFile();
        System.out.println("Current best before execution: " + currentBest);

        int numThreads = Runtime.getRuntime().availableProcessors();
        System.out.printf("Running on %d threads.%n", numThreads);
        optimizers = new DatacenterOptimizer[numThreads];

        for (int i = 0; i < numThreads; i++) {
            optimizers[i] = new DatacenterOptimizer(this, currentBest);
            new Thread(optimizers[i]).start();
        }
    }

    public static void createOutputFiles(int score, Datacenter datacenter) {
        try {
            PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("best-score.txt")));
            out.println(score);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static int readBestFromFile() {
        String fileName = "best-score.txt";

        if (!new File(fileName).exists()) {
            return 0;
        }

        int score = 0;
        try {
            BufferedReader in = new BufferedReader(new FileReader(fileName));
            score = Integer.parseInt(in.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return score;
    }

    public synchronized void updateBest(Datacenter datacenter, int score) {
        System.out.println("New best: " + score);
        currentBest = score;

        for (DatacenterOptimizer datacenterOptimizer : optimizers) {
            datacenterOptimizer.updateBest(score);
        }

        createOutputFiles(score, datacenter);
    }

    public static void main(String[] args) {
        new OptimizerController().go();
    }
}
