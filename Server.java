import java.util.ArrayList;

public class Server {
    int maxCapacity;
    int capacity;
    ArrayList<Integer> videoIDs;

    public Server(int maxCapacity) {
        capacity = 0;
        videoIDs = new ArrayList<>();
        this.maxCapacity = maxCapacity;
    }

    public boolean addVideo(int id, int cost) {
        if (capacity + cost < maxCapacity) {
            this.capacity += cost;
            videoIDs.add(id);
            return true;
        }
        return false;
    }
}
