package pokker.server;

import java.io.*;
        import java.net.ServerSocket;
        import java.net.Socket;

public class Main {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(1337);
        while (true) {
            Socket socket = serverSocket.accept();
            Runnable r = new ConnectionHandler(socket);
            Thread t = new Thread(r);
            t.start();
        }
    }
}

