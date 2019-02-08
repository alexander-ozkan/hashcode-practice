package Servers;

public class Server {
    private int capacity;
    private int pool;
    private int requiredSpace;
    private int row;
    private int slot;

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
}
