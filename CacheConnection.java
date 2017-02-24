/**
 * Created by TheSpine on 23/02/17.
 */
public class CacheConnection implements Comparable<CacheConnection> {
    public Server server;
    public int id;
    public int latency;

    public CacheConnection(int id, int latency, Server server) {
        this.id = id;
        this.latency = latency;
        this.server = server;
    }

    @Override
    public int compareTo(CacheConnection o) {
        return Integer.compare(latency, o.latency);
    }
}
