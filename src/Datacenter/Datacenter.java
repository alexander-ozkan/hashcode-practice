package Datacenter;

import Servers.Server;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Datacenter {
    private int rows, cols;
    private boolean[][] cluster;
    private int numPools;
    private List<Server> availableServers;
    private List<Server> usedServers;
    private List<ArrayList<Server>> rowsUsedServers;
    private int[][] unavailSlots;

    public Datacenter(int rows, int cols, int numPools, List<Server> availableServers, int[][] unavailSlots) {
        this.rows = rows;
        this.cols = cols;
        this.availableServers = availableServers;
        this.unavailSlots = unavailSlots;

        cluster = new boolean[rows][cols];

        usedServers = new ArrayList<>(availableServers.size());
        rowsUsedServers = new ArrayList<>(rows);

        for (int i = 0; i < rows; i++) {
            rowsUsedServers.add(new ArrayList<>());
        }

        this.numPools = numPools;

        resetSlots();
    }

    public boolean canPlaceServer(Server server, int row, int col) {
        for (int c = col; c < col + server.getRequiredSpace(); c++) {
            if (c >= cols || cluster[row][c]) return false;
        }

        return true;
    }

    public void placeServer(Server server, int row, int col) {
        for (int c = col; c < col + server.getRequiredSpace(); c++) {
            cluster[row][c] = true;
        }

        availableServers.remove(server);
        usedServers.add(server);

        server.setRow(row);
        server.setCol(col);

        rowsUsedServers.get(row).add(server);
    }

    public void resetSlots() {
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                cluster[r][c] = false;
            }
        }

        // apply unavailable slots
        for (int[] s : unavailSlots) {
            cluster[s[0]][s[1]] = true;
        }
    }

    public int getNumPools() {
        return numPools;
    }

    public void setNumPools(int numPools) {
        this.numPools = numPools;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        // print datacenter slot availability
        sb.append("Slots taken:\n");
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                sb.append(cluster[r][c] ? 'x' : '-');
            }

            sb.append("\n");
        }

        sb.append("Used servers:\n");
        for (Server s : usedServers) {
            sb.append(s.toString() + "\n");
        }

        sb.append("Available servers\n");
        for (Server s : availableServers) {
            sb.append(s.toString() + "\n");
        }

        sb.append("Score: " + getScore() + "\n");
        return sb.toString();
    }

    public boolean[][] getCluster() {
        return cluster;
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public List<Server> getAvailableServers() {
        return availableServers;
    }

    public List<Server> getUsedServers() {
        return usedServers;
    }

    public List<ArrayList<Server>> getRowsUsedServers() {
        return rowsUsedServers;
    }

    public int getScore() {
        int minGuaranteedPoolCap = Integer.MAX_VALUE;

        for (int i = 0; i < rows; i++) {
            int[] poolCapacity = new int[numPools];

            //Compute lowest capacity of all pools
            for(int j = 0; j < usedServers.size(); j++)
            {
                Server server = usedServers.get(j);
                // ignore server if on row that is down
                if (server.getRow() != i) {
                    poolCapacity[server.getPool()] += server.getCapacity();
                }
            }

            for (int j = 0; j < numPools; j++) {
                minGuaranteedPoolCap = Integer.min(minGuaranteedPoolCap, poolCapacity[j]);
            }
        }

        return minGuaranteedPoolCap;
    }

    public int[] getPoolCapacities() {
        int[] capacities = new int[numPools];

        for (Server server : usedServers) {
            capacities[server.getPool()] += server.getCapacity();
        }

        return capacities;
    }
}
