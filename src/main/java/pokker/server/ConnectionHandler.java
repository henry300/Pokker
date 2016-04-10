package pokker.server;

import java.io.*;
import java.net.Socket;

public class ConnectionHandler implements Runnable {
    Socket socket;
    ConnectionHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try (
                DataInputStream dis = new DataInputStream(socket.getInputStream());
                DataOutputStream dos = new DataOutputStream(socket.getOutputStream())
        ) {
            // mängu algseis (pot, mängijate seisund, panused)
            // mängija liitus (raha)
            // kaardid jagatud
            // panuse panek
            // kes võitis
            // mängija lahkus (peab vahet tegema lahkumisel ja disconnectil?)

            // puudu: timer, kicker, bigblind preflo

        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
