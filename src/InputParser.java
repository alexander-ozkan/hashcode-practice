import Datacenter.Datacenter;
import Servers.Server;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

public class InputParser {
    public static Datacenter parseFile(String filePath) throws IOException {
        List<Server> servers = new ArrayList<Server>();

        BufferedReader in = new BufferedReader(new FileReader(filePath));

        String[] specs = in.readLine().split(" ");
        int[] specInts = Arrays.stream(specs).mapToInt(n -> (Integer.parseInt(n))).toArray();
        int rows, cols, numUnavail, numPools, numServers;
        rows = specInts[0];
        cols = specInts[1];
        numUnavail = specInts[2];
        numPools = specInts[3];
        numServers = specInts[4];

        int[][] unavailSlots = new int[numUnavail][];

        for (int i = 0; i < numUnavail; i++) {
            StringTokenizer tokenizer = new StringTokenizer(in.readLine());
            unavailSlots[i] = new int[2];
            for (int j = 0; j < 2; j++) unavailSlots[i][j] = Integer.parseInt(tokenizer.nextToken());
        }

        for (int i = 0; i < numServers; i++) {
            int[] serverSpecs = Arrays.stream(in.readLine().split(" ")).mapToInt(n -> (Integer.parseInt(n))).toArray();
            servers.add(new Server(serverSpecs[1], serverSpecs[0]));
        }

        return new Datacenter(rows, cols, numPools, servers, unavailSlots);
    }
}
