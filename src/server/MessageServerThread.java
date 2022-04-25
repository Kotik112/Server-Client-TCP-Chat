package server;

import models.Event;
import models.Message;
import models.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;

public class MessageServerThread implements Runnable {

    Socket socket;
    ObjectOutputStream out;
    ObjectInputStream in;
    Thread listenerThread;
    MessageServer messageServer;

    public MessageServerThread (Socket s, MessageServer messageServer) {
        this.socket = s;
        this.messageServer = messageServer;
    }

    public void startListening() {
        System.out.println("Server: Start listening for client!");

        if (listenerThread == null) {
            listenerThread = new Thread(this);
            listenerThread.start();
        }

    }

    public void sendMessage(Message m) {
        try {
            out.writeObject(new Event(3, m));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendUserList(ArrayList<User> u) {
        try {
            System.out.println("Server: Sending list with users sized " + u.size());
            out.writeObject(new Event(4, u));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handleEvent(Event e) {
        if(e.getEventType() == 1) {
            System.out.println("Server: Got a new user");
            messageServer.addUser(e.getUser());
        }
        else if(e.getEventType() == 3) {
            System.out.println("Server: Got a new message.");
            messageServer.handleMessage(e.getMessage());
        }
    }

    @Override
    public void run() {
        try {
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());

            while (true) {
                Event newEvent = (Event) in.readObject();
                handleEvent(newEvent);
            }

        } catch (SocketException e) {
            if (!socket.isClosed()) {
                try {
                    socket.close();
                    listenerThread.interrupt();
                    messageServer.removeMessageServerThread(this);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
            // client disconnected
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
