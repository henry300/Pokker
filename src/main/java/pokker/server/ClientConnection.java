package pokker.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientConnection implements Runnable {
    private final Socket socket;

    public ClientConnection(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try (
                DataInputStream dataIn = new DataInputStream(socket.getInputStream());
                DataOutputStream dataOut = new DataOutputStream(socket.getOutputStream());
        ) {

            socket.close();
        } catch (IOException e) {
            System.out.println("Something happened with a client: " + e.getMessage());
        }
    }

    public void write() {
        //TODO: implement
    }
}
