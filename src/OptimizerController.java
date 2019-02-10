import Datacenter.Datacenter;
import Servers.Server;

import javax.imageio.ImageIO;
import javax.xml.crypto.Data;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Collections;

public class OptimizerController {
    private int currentBest;
    DatacenterOptimizer[] optimizers;

    public void go() {
        int currentBest = readBestFromFile();
        System.out.println("Current best before execution: " + currentBest);

        int numThreads = Runtime.getRuntime().availableProcessors() - 1;
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

        BufferedImage img = new BufferedImage(datacenter.getCols(), datacenter.getRows(), BufferedImage.TYPE_INT_RGB);
        Graphics g = img.getGraphics();
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, img.getWidth(), img.getHeight());

        for (Server server : datacenter.getUsedServers()) {
            g.setColor(new Color(Color.HSBtoRGB((float) server.getPool() / datacenter.getNumPools(), 1.0f, 1.0f)));
            g.fillRect(server.getCol(), server.getRow(), server.getRequiredSpace(), 1);
        }

        g.dispose();

        try {
            ImageIO.write(img, "PNG", new File("./datacenter.png"));
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
