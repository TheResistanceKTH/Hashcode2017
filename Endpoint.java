import java.util.ArrayList;
import java.util.Collections;

public class Endpoint {
    public int latencyToCenter;
    public ArrayList<CacheConnection> connections;
    public ArrayList<Request> requests;

    public Endpoint(int latencyToCenter) {
        this.latencyToCenter = latencyToCenter;
        connections = new ArrayList<>();
        requests = new ArrayList<>();
    }

    public void addConnection(int id, int latency, Server s) {
        connections.add( new CacheConnection(id, latency, s));
    }

    public void addRequest(Request r) {
        requests.add(r);
    }

    public void sortConnections() {
        Collections.sort(connections);
    }
}
