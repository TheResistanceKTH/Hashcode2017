import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class Main {
    static int V;
    static int E;
    static int R;
    static int C;
    static int X;
    static ArrayList<Integer> videos;
    static ArrayList<Endpoint> endpoints;
    static ArrayList<Request> requests;
    static ArrayList<Server> servers;
    public static void main(String[] args) {

        makeSolution("me_at_the_zoo");
        makeSolution("trending_today");
        makeSolution("videos_worth_spreading");
        makeSolution("kittens");

    }

    public static void makeSolution(String fileName) {
        readInput( new File("in/" + fileName + ".in") ); // <--
        System.out.println(fileName + ": \n" + videos.size() + " " + endpoints.size() + " " +  requests.size());

        Map<Integer, ArrayList<Request>> requestVideoMap = new HashMap<>();

        // make map of arraylists containing every request with that
        // videoID as key
        for (Request request : requests) {
            int id = request.videoID;
            ArrayList<Request> arr;
            if (requestVideoMap.get(id) == null) {
                arr = new ArrayList<>();
                arr.add(request);
                requestVideoMap.put(id, arr);
                continue;
            }
            arr = requestVideoMap.get(id);
            arr.add(request);
        }

        // set each numOccurences for every request
        for (int i = 0; i < V; i++) {
            ArrayList<Request> a = requestVideoMap.get(i);
            if (a == null) {
                continue;
            }
            int size = a.size();
            for (Request r : a) {
                r.numOccurences = size;
            }
        }

        updatedValues();

        // sort requests by their value and add them to the
        // cache server in that order, there by prioritizing those with high value
        Collections.sort(requests);
        for (Request r : requests) {
            for (CacheConnection c : r.endpoint.connections) {
                if (c.server.videoIDs.contains(r.videoID)) break;
                if (c.server.addVideo(r.videoID, r.cost)) break;
            }
            r.endpoint.addRequest(r);
        }

        // make output string
        int numUsedServers = 0;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < servers.size(); i++) {
            Server s = servers.get(i);
            if (s.videoIDs.size() != 0) {
                sb.append(i + " ");
                for (Integer j : s.videoIDs) {
                    sb.append(j.toString() + " ");
                }
                sb.deleteCharAt(sb.length()-1);
                numUsedServers++;
                sb.append("\n");
            }
        }

        // print output to file
        String outputString = numUsedServers + "\n" + sb.toString();
        try {
            Path p = Paths.get("out/" + fileName + ".out");
            Files.write(p, outputString.getBytes());
        } catch (IOException e) { e.printStackTrace(); }
    }

    // reads the input in the specified format
    public static void readInput(File file) {
        Scanner sc = null;
        try {
            sc = new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
        }

        V = sc.nextInt();
        E = sc.nextInt();
        R = sc.nextInt();
        C = sc.nextInt();
        X = sc.nextInt();

        servers = new ArrayList<>();
        for (int i = 0; i < C; i++) {
            servers.add( new Server(X) );
        }

        videos = new ArrayList<>(V);
        for (int i = 0; i < V; i++) videos.add(sc.nextInt());

        endpoints = new ArrayList<>();
        for (int i = 0; i < E; i++) {
            Endpoint e = new Endpoint(sc.nextInt());
            int numServers = sc.nextInt();
            for (int j = 0; j < numServers; j++) {
                int id = sc.nextInt();
                int latency = sc.nextInt();
                e.addConnection(id, latency, servers.get(id));
            }
            endpoints.add(e);
            e.sortConnections();
        }

        requests = new ArrayList<>(R);
        for (int i = 0; i < R; i++) {
            int id = sc.nextInt();
            requests.add( new Request(id, endpoints.get(sc.nextInt()), sc.nextInt(), videos.get(id)) );
        }
    }

    // updates the field 'value' for every request
    private static void updatedValues() {
        for (Request r : requests) {
            r.updateValue();
        }
    }
}
