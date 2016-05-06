package pokker.server;

/**
 * This class runs the server
 */
public class Main {
    public static void main(String[] args) {
        Server server = new Server(1337);
        server.run();

        // messages:
        // mängu algseis (pot, mängijate seisund, panused)
        // mängija liitus (raha)
        // kaardid jagatud
        // panuse panek
        // kes võitis
        // mängija lahkus (peab vahet tegema lahkumisel ja disconnectil?)
    }
}

