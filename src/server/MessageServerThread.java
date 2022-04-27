package server;

import models.Event;
import models.Message;
import models.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.sql.SQLOutput;
import java.util.ArrayList;

public class MessageServerThread implements Runnable {

    Socket socket;
    ObjectOutputStream out;
    ObjectInputStream in;
    Thread listenerThread = null;
    MessageServer messageServer;

    public MessageServerThread (Socket s, MessageServer messageServer) {
        this.socket = s;
        this.messageServer = messageServer;
    }

    public void startListening() {
        System.out.println("Server: Start listening for client!");   //For debugging. Remove later.

        if (listenerThread == null) {
            listenerThread = new Thread(this);
            listenerThread.start();
        }

    }

    /**
     * Used with Event type 3. Send Message.
     * Refer to model.Event for Event documentation.
     * @param m Message object m. (See model.Event documentation.
     */
    public void sendMessage(Message m) {
        try {
            out.writeObject(new Event(3, m));
            out.reset();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Used by the server to update the "connected users" list on client side.
     * @param u List of all connected users.
     */
    public void sendUserList(ArrayList<User> u) {
        try {
            System.out.println("Server: Sending list with users sized " + u.size()); //For debugging. Remove later.
            out.writeObject(new Event(4, u));
            out.reset();    //Clears buffer
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handleEvent(Event e) {
        if(e.getEventType() == 1) {
            System.out.println("Server: Got a new user"); //For debugging. Remove later.
            messageServer.addUser(e.getUser());
        }
        else if(e.getEventType() == 3) {
            System.out.println("Server: Got a new message."); //For debugging. Remove later.
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
            System.out.println("ClassNotFoundException occurred.");
            e.printStackTrace();
        }
    }
}
