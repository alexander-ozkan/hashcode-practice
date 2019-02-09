package Servers;

public class Server implements Comparable {
    private int capacity;
    private int pool;
    private int requiredSpace;
    private int row, col;
    private int slot;

    public Server(int capacity, int requiredSpace) {
        this.capacity = capacity;
        this.requiredSpace = requiredSpace;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getPool() {
        return pool;
    }

    public void setPool(int pool) {
        this.pool = pool;
    }

    public int getRequiredSpace() {
        return requiredSpace;
    }

    public void setRequiredSpace(int requiredSpace) {
        this.requiredSpace = requiredSpace;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getSlot() {
        return slot;
    }

    public void setSlot(int slot) {
        this.slot = slot;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public double getCapacityPerSlot() {
        return (double) capacity / requiredSpace;
    }

    @Override
    public int compareTo(Object o) {
        Server other = (Server) o;

        double power = getCapacityPerSlot();
        double otherPower = other.getCapacityPerSlot();

        if (power > otherPower) {
            return 1;
        }
        else if (power < otherPower) {
            return -1;
        }
        else {
            return 0;
        }
    }

    @Override
    public String toString() {
        return "Server{" +
                "capacity=" + capacity +
                ", pool=" + pool +
                ", requiredSpace=" + requiredSpace +
                ", row=" + row +
                ", slot=" + slot +
                ", power=" + getCapacityPerSlot() +
                '}';
    }
}
