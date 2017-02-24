import java.util.ArrayList;
import java.util.Random;

import static java.lang.Math.abs;

public class Request implements Comparable<Request> {
    public int videoID;
    public int cost;
    public Endpoint endpoint;
    public int numRequests;
    public int value;
    public int numOccurences;

    public Request(int videoID, Endpoint endpoint, int numRequests, int cost) {
        this.videoID = videoID;
        this.endpoint = endpoint;
        this.numRequests = numRequests;
        this.cost = cost;
        this.numOccurences = 1;
        updateValue();
    }

    @Override
    public int compareTo(Request r) {
        return Integer.compare(r.value, value);
    }

    public void updateValue() {
        /*
        int bestConnection = Integer.MIN_VALUE;
        for (CacheConnection c : endpoint.connections) {
            if (c.server.capacity + this.cost < c.server.maxCapacity) {
                bestConnection = c.latency;
                break;
            }
        }
        */
        Random r = new Random();

        this.value = numRequests * numOccurences / cost;
    }
}
