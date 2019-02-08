package Datacenter;

import Servers.Server;

import java.util.List;

public class Datacenter {
    private boolean[][] cluster;
    private List<Server> availableServers;
    private List<Server> usedServers;
}
