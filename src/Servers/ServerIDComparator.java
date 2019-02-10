package Servers;

import java.util.Comparator;

public class ServerIDComparator implements Comparator<Server> {
    @Override
    public int compare(Server o1, Server o2) {
        if (o1.getId() < o2.getId()) {
            return -1;
        }
        else if (o1.getId() > o2.getId()) {
            return 1;
        }
        else {
            return 0;
        }
    }
}
