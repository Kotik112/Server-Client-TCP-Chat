package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ListeningThread implements Runnable {

    ServerSocket serverSocket = null;
    Thread listenerThread = null;
    MessageServer messageServer;

    public ListeningThread(MessageServer m) {
        this.messageServer = m;
    }

    public void startListening() {
        System.out.println("Server: Start listening for sockets!"); //For debugging. Remove later.

        if (listenerThread == null) {
            listenerThread = new Thread(this);
            listenerThread.start();
        }
    }

    @Override
    public void run() {
        try {
            serverSocket = new ServerSocket(55555);
            while (true) {
                Socket socketToClient = serverSocket.accept();
                this.messageServer.addSocket(socketToClient);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
