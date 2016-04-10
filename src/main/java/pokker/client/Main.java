package pokker.client;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class Main {
    public static void main(String[] args) throws IOException {
        try (
                Socket socket = new Socket(InetAddress.getLocalHost(), 1337);
                DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
                DataInputStream dis = new DataInputStream(socket.getInputStream());
        ) {
            // TODO
        }
    }
}